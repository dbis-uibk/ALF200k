package musicanalysis.model.settings;

public class Settings {

   public static final String BASE_PACKAGES = "musicanalysis.controller;musicanalysis.model.eval;musicanalysis.model.music.lyric.crawler;musicanalysis.model.music.lyric.feature;musicanalysis.model.music.track;musicanalysis.model.music.playlist;musicanalysis.model.music.audio;musicanalysis.model.ml;musicanalysis.model.ml.parser";
   public static final String BASE_DIRECTORY = System.getProperty("user.home") + "/musicanalysis/";
   public static final String EXPORT_PATH = BASE_DIRECTORY + "export/";

   public static final String AUDIO_PATH = "spotifydataset/echonestDataset.csv";
   public static final String PLAYLIST_PATH = "spotifydataset/playlistDataset.csv";
   public static final String AFINN_PATH = "AFINN/AFINN-111.txt";
   public static final String CHUNKER_TRAINING_SET_PATH = "chunker/en-chunker.bin";
   public static final String RID_CATEGORY_PATH = "RID/RID.cat";
   public static final String RID_EXCLUDED_PATH = "RID/RID.exc";
   public static final String SENTI_STRENGTH_DATA_PATH = "sentistrength-2015/";
   public static final String STOPWORDS_LIST_PATH = "stopwords/stopwords.txt";
   public static final String VADER_PYTHON_SCRIPT_PATH = "VADER/vaderSentiment.py";
   public static final String VADER_LEXICON_PATH = "VADER/vader_lexicon.txt";
   public static final String BEST_SD_AROUND_MEAN_FEATURES_PATH = "best-features/sd_around_mean.csv";
   public static final String BEST_MEAN_FEATURES_PATH = "best-features/mean.csv";
   private static final String OPINION_LEXICON_PATH = "OpinionLexicon/";
   public static final String OPINION_LEXICON_NEGATIVE_WORDS_PATH = OPINION_LEXICON_PATH + "negative-words.txt";
   public static final String OPINION_LEXICON_POSITIVE_WORDS_PATH = OPINION_LEXICON_PATH + "positive-words.txt";

   private Settings() {

   }
}
