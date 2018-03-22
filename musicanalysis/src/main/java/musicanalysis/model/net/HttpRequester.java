package musicanalysis.model.net;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class HttpRequester {

   private static final int TIMEOUT = 30000;

   private HttpRequester() {

   }

   public static String get(final String url)
         throws IOException {

      return get(url, null);
   }

   public static String get(final String url, final String agent)
         throws IOException {

      try (final CloseableHttpClient client = HttpClientBuilder.create().build()) {
         final HttpGet request = new HttpGet(url);
         try {
            final RequestConfig requestConfig = RequestConfig.custom()
                  .setSocketTimeout(TIMEOUT)
                  .setConnectTimeout(TIMEOUT)
                  .setConnectionRequestTimeout(TIMEOUT)
                  .build();
            request.setConfig(requestConfig);
            request.setHeader("Connection", "close");
            if (agent != null) {
               request.setHeader("User-Agent", agent);
            }
            return client.execute(request, getResponseHandler());
         } finally {
            request.releaseConnection();
            client.close();
         }
      }
   }

   public static String post(final String url, final List<NameValuePair> data)
         throws IOException {

      try (final CloseableHttpClient client = HttpClients.createDefault()) {
         final HttpPost request = new HttpPost(url);
         try {
            final RequestConfig requestConfig = RequestConfig.custom()
                  .setSocketTimeout(TIMEOUT)
                  .setConnectTimeout(TIMEOUT)
                  .setConnectionRequestTimeout(TIMEOUT)
                  .build();
            request.setConfig(requestConfig);
            request.setHeader("Connection", "close");
            // request.setHeader("User-Agent", USER_AGENT);
            request.setEntity(new UrlEncodedFormEntity(data));
            return client.execute(request, getResponseHandler());
         } finally {
            request.releaseConnection();
            client.close();
         }
      }
   }

   private static ResponseHandler<String> getResponseHandler() {

      final ResponseHandler<String> responseHandler = response -> {
         int status = response.getStatusLine().getStatusCode();
         if (status >= 200 && status < 300) {
            final HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
         } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
         }
      };

      return responseHandler;
   }
}
