package musicanalysis.model.music.lyric.crawler;

import com.gargoylesoftware.htmlunit.AjaxController;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import musicanalysis.model.data.Lyric;
import musicanalysis.model.data.Track;
import musicanalysis.model.music.lyric.language.LanguageDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class Sing365 extends ConcurrentLyricCrawler {

    public static final String NAME = "sing365";
    private static final String URL = "http://www.sing365.com/search.html?q=%s+%s+lyrics";

    @Autowired
    public Sing365(MongoTemplate mongoTemplate) {

        super(4);
        this.initialize(mongoTemplate);
    }

    @Override
    public String getName() {

        return NAME;
    }

    @Override
    protected Lyric download(final Track track)
            throws IOException {

        Lyric lyric = null;

        final String name = track.getName();
        final String artist = track.getArtists().get(0).getName();
        if (name != null && artist != null && !name.isEmpty() && !artist.isEmpty()) {
            final String song = String.format(URL, encodeURL(artist), encodeURL(name));

            try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45)) {
                webClient.getOptions().setTimeout(120000);
                webClient.setJavaScriptTimeout(120000);
                webClient.getOptions().setCssEnabled(false);
                webClient.getOptions().setPopupBlockerEnabled(false);
                webClient.getOptions().setUseInsecureSSL(true);
                webClient.getOptions().setJavaScriptEnabled(true);
                webClient.getOptions().setThrowExceptionOnScriptError(false);
                webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
                webClient.setAjaxController(new SynchronAjaxController());
                final HtmlPage page = webClient.getPage(song);
                if (page != null) {
                    final org.jsoup.nodes.Document d = Jsoup.parse(page.asXml());
                    final Element link = d.select("div.gsc-results.gsc-webResult").select("a").first();
                    if (link != null) {
                        final String url = link.attr("href");
                        // check if result exists
                        if (!url.isEmpty()) {
                            final String lyricURL = findInLyrics(url, name);
                            final String text = extractLyric(lyricURL).trim();
                            if (!text.isEmpty()) {
                                lyric = new Lyric();
                                lyric.setUrl(lyricURL);
                                lyric.setTrackId(track.getId());
                                lyric.setText(text);
                                lyric.setLanguage(LanguageDetector.detect(text));
                            }
                        }
                    }
                }
            }
        }

        return lyric;
    }

    private String extractLyric(final String url)
            throws IOException {

        String result = "";

        final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url, USER_AGENT));
        final Pattern pattern = Pattern.compile("(?is)</script>\\s+<br>(((?!<hr>).)*)<hr>");
        final Matcher matcher = pattern.matcher(d.select("div#main").html());
        if (matcher.find()) {
            result = preserveNewlines(matcher.group(1).replaceAll("<br>", "")).trim();
            result = result.replaceAll("(?im)^[^\\S\\n]*Thanks[^\\S\\n]*to.*for[^\\S\\n]*submitting.*Lyrics.$", "").trim();
        }

        return result;
    }

    private String findInLyrics(final String url, final String trackName)
            throws IOException {

        String newURL = url;

        final org.jsoup.nodes.Document d = Jsoup.parse(executeGetRequest(url, USER_AGENT));
        boolean isLyricsOverview = d.select("div#main").select("a[href^=http://submit.sing365.com]").first() != null;
        if (isLyricsOverview) {
            for (final Element element : d.select("div#main").select("a")) {
                if (element.attr("title").toUpperCase().contains(trackName.toUpperCase())) {
                    newURL = "http://www.sing365.com" + element.attr("href");
                    break;
                }
            }
        }

        return newURL;
    }

    private class SynchronAjaxController extends AjaxController {
        @Override
        public boolean processSynchron(HtmlPage page, WebRequest request, boolean async) {
            return true;
        }
    }
}