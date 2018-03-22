package musicanalysis.model.common;

import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

public class Net {

   private Net() {

   }

   public static String encodeURL(final String url)
         throws UnsupportedEncodingException {

      return UriUtils.encode(url, "UTF-8");
   }

   public static String encodePath(final String url)
         throws UnsupportedEncodingException {

      return UriUtils.encodePath(url, "UTF-8");
   }
}