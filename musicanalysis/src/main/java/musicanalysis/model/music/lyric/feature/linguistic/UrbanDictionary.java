package musicanalysis.model.music.lyric.feature.linguistic;

import musicanalysis.model.net.HttpRequester;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * See API: http://api.urbandictionary.com
 */
public class UrbanDictionary {

   private static final String URL = "http://api.urbandictionary.com/v0/define?term=%s";
   private Map<String, Boolean> tokens;
   private MongoTemplate mongoTemplate;

   private UrbanDictionary() {

      tokens = new ConcurrentHashMap<>();
   }

   public static UrbanDictionary getInstance() {

      return InstanceHolder.INSTANCE;
   }

   public synchronized void initialize(final MongoTemplate mongoTemplate) {

      this.mongoTemplate = mongoTemplate;
      final List<musicanalysis.model.data.UrbanDictionary> list = mongoTemplate.findAll(musicanalysis.model.data.UrbanDictionary.class);
      tokens.clear();
      list.forEach(w -> tokens.put(w.getToken(), w.getIsPresent()));
   }

   public synchronized boolean request(String token) {

      token = token.toLowerCase();
      boolean found = false;

      if (!tokens.containsKey(token)) {
         try {
            final String message = HttpRequester.get(String.format(URL, musicanalysis.model.common.Net.encodeURL(token)));
            final JSONObject json = new JSONObject(message);
            found = json.getString("result_type").equalsIgnoreCase("exact");
            tokens.put(token, found);
            save(token, found);
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      } else {
         found = tokens.get(token);
      }

      return found;
   }

   private void save(final String token, final Boolean isPresent) {

      if (mongoTemplate != null) {
         musicanalysis.model.data.UrbanDictionary dictionary = new musicanalysis.model.data.UrbanDictionary();
         dictionary.setToken(token);
         dictionary.setIsPresent(isPresent);
         mongoTemplate.insert(dictionary);
      }
   }

   private static class InstanceHolder {

      static final UrbanDictionary INSTANCE = new UrbanDictionary();
   }
}
