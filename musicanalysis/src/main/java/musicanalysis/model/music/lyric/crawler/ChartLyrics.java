package musicanalysis.model.music.lyric.crawler;

import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.music.lyric.language.LanguageDetector;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * API description can be found at http://www.chartlyrics.com/api.aspx.
 */
@Component
public final class ChartLyrics extends SerialLyricCrawler {

   public static final String NAME = "chartlyrics";
   private static final String URL = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=%s&song=%s";

   @Autowired
   public ChartLyrics(MongoTemplate mongoTemplate) {

      this.initialize(mongoTemplate);
   }

   @Override
   public String getName() {

      return NAME;
   }

   @Override
   protected Lyric download(final Track track)
         throws IOException, SAXException, ParserConfigurationException {

      Lyric lyric = null;

      final String name = track.getName();
      final String artist = track.getArtists().get(0).getName();
      if (name != null && artist != null && !name.isEmpty() && !artist.isEmpty()) {
         final String song = String.format(URL, encodeURL(artist), encodeURL(name));
         final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(song));
         if (d != null) {
            final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            final DocumentBuilder db = dbf.newDocumentBuilder();
            final org.w3c.dom.Document dom = db.parse(new InputSource(new ByteArrayInputStream(d.body().text().getBytes("UTF-8"))));
            final NodeList nodes = dom.getElementsByTagName("Lyric");
            if (nodes.getLength() == 1) {
               final Node node = nodes.item(0);
               final String text = node.getTextContent().trim();
               if (!text.isEmpty()) {
                  lyric = new Lyric();
                  lyric.setUrl(song);
                  lyric.setTrackId(track.getId());
                  lyric.setText(text);
                  lyric.setLanguage(LanguageDetector.detect(text));
               }
            }
         }
      }

      return lyric;
   }
}
