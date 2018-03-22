package musicanalysis.model;

import musicanalysis.model.log.ILogger;
import musicanalysis.model.log.LogType;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Task implements Runnable {

   private final Object lock;
   private final List<ILogger> logger;

   private ExecutionState executionState;
   private MongoTemplate mongoTemplate;

   public Task() {

      logger = Collections.synchronizedList(new ArrayList<>());
      executionState = ExecutionState.Stopped;
      lock = new Object();
   }

   public void initialize(final MongoTemplate mongoTemplate) {

      this.mongoTemplate = mongoTemplate;
   }

   protected MongoTemplate getDatabaseConnection() {

      return mongoTemplate;
   }

   @Override
   public synchronized void run() {

      boolean execute = false;
      synchronized (lock) {
         if (executionState == ExecutionState.Stopped) {
            executionState = ExecutionState.Running;
            execute = true;
         }
      }

      if (execute) {
         try {
            logInfo(String.format("Task '%s' started", getTaskName()));
            execute();
            logInfo(String.format("Task '%s' successfully finished", getTaskName()));
         } catch (Exception ex) {
            logInfo(String.format("Task '%s' exited with errors", getTaskName()));
            logError(ex.toString());
         } finally {
            synchronized (lock) {
               executionState = ExecutionState.Stopped;
            }
         }
      } else {
         logError(String.format("Task '%s' is already running", getTaskName()));
      }
   }

   public void subscribe(ILogger logger) {

      if (!this.logger.contains(logger)) {
         this.logger.add(logger);
      }
   }

   public void unsubscribe(ILogger logger) {

      this.logger.remove(logger);
   }

   protected boolean isRunning() {

      synchronized (lock) {
         return executionState == ExecutionState.Running;
      }
   }

   protected boolean isCancelled() {

      synchronized (lock) {
         return executionState == ExecutionState.Cancelling;
      }
   }

   public void cancel() {

      synchronized (lock) {
         if (executionState == ExecutionState.Running) {
            executionState = ExecutionState.Cancelling;
         }
      }

      new Thread(this::onCancellation).start();
   }

   public void onCancellation() {

   }

   public ExecutionState getExecutionState() {

      synchronized (lock) {
         return executionState;
      }
   }

   public float getProgress() {

      float progress = 0.0f;

      if (executionState != ExecutionState.Stopped) {
         progress = calculateProgress();
      }

      return progress;
   }

   protected void logInfo(final String message) {

      final String text = String.format("%-15s - %s", getTaskName().toUpperCase(), message);
      log(text, LogType.INFO);
   }

   protected void logWarning(final String message) {

      final String text = String.format("%-15s - %s", getTaskName().toUpperCase(), message);
      log(text, LogType.WARNING);
   }

   protected void logError(final String message) {

      final String text = String.format("%-15s - %s", getTaskName().toUpperCase(), message);
      log(text, LogType.ERROR);
   }

   private void log(final String message, LogType type) {

      logger.forEach(l -> l.log(message, type));
   }

   protected abstract void execute();

   protected abstract String getTaskName();

   protected abstract float calculateProgress();

   public enum ExecutionState {

      Running,
      Cancelling,
      Stopped
   }
}
