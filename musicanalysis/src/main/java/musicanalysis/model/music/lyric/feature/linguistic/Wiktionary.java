package musicanalysis.model.music.lyric.feature.linguistic;

import musicanalysis.model.net.HttpRequester;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * See API: http://en.wiktionary.org/w/api.php
 */
public class Wiktionary {

   private static final String URL = "https://en.wiktionary.org/w/api.php?action=query&titles=%s&format=xml";
   private final Map<String, Boolean> tokens;
   private MongoTemplate mongoTemplate;

   private Wiktionary() {

      tokens = new ConcurrentHashMap<>();
   }

   public static Wiktionary getInstance() {

      return InstanceHolder.INSTANCE;
   }

   public synchronized void initialize(final MongoTemplate mongoTemplate) {

      this.mongoTemplate = mongoTemplate;
      final List<musicanalysis.model.data.Wiktionary> list = mongoTemplate.findAll(musicanalysis.model.data.Wiktionary.class);
      tokens.clear();
      list.forEach(w -> tokens.put(w.getToken(), w.getIsPresent()));
   }

   public synchronized boolean request(String token) {

      token = token.toLowerCase();
      boolean found = false;

      if (!tokens.containsKey(token)) {
         try {
            // search by different token capitalizations -> Wiktionary does a case sensitive search
            final String message = HttpRequester.get(buildRequest(token));
            final DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final org.w3c.dom.Document dom = db
                  .parse(new InputSource(new ByteArrayInputStream(message.getBytes("UTF-8"))));
            final NodeList nodes = dom.getElementsByTagName("page");
            for (int i = 0; i < nodes.getLength() && !found; i++) {
               final Node node = nodes.item(i);
               final Node pageId = node.getAttributes().getNamedItem("pageid");
               if (pageId != null) {
                  found = Integer.parseInt(pageId.getTextContent()) > -1;
               }
            }

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

   private String buildRequest(final String token)
         throws UnsupportedEncodingException {

      final String lowercased = token.toLowerCase();
      final String uppercased = token.toUpperCase();
      final String capitalized = token.substring(0, 1).toUpperCase() + token.substring(1);
      final String parameter = lowercased + "|" + uppercased + "|" + capitalized;
      return String.format(URL, musicanalysis.model.common.Net.encodeURL(parameter));
   }

   private void save(final String token, final Boolean isPresent) {

      if (mongoTemplate != null) {
         musicanalysis.model.data.Wiktionary wiktionary = new musicanalysis.model.data.Wiktionary();
         wiktionary.setToken(token);
         wiktionary.setIsPresent(isPresent);
         mongoTemplate.insert(wiktionary);
      }
   }

   private static class InstanceHolder {

      static final Wiktionary INSTANCE = new Wiktionary();
   }
}
