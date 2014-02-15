import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FiclyUtils {

    public static final String storyurl = "http://ficly.com/stories/";

    public static Story getStoryWithID(int ID) {
        return FiclyUtils.getStoryWithURL(storyurl + ID);
    }

    public static Story getStory(StorySkeleton skel) {
        return FiclyUtils.getStoryWithURL(skel.geturl());
    }

    public static Story getStoryWithURL(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            System.err.println("Could not fetch page.");
            return null;
        }
        return parseDoc(doc, url);
    }

    private static Story parseDoc(Document doc, String url) {
        return new Story(FiclyUtils.getPrequels(doc), FiclyUtils.getSequels(doc), FiclyUtils.getTitle(doc), getAuthor(doc), url, FiclyUtils.getText(doc));
    }

    private static ArrayList<StorySkeleton> getPrequels(Document doc) {
        Elements prequels = doc.select("#prequels li.hentry [rel=bookmark previous]");
        ArrayList<StorySkeleton> preqs = new ArrayList<StorySkeleton>();
        for (Element prequel : prequels) {
            preqs.add(new StorySkeleton(prequel.ownText(), getAuthor(doc), prequel.attr("href")));
        }
        return preqs;
    }

    private static ArrayList<StorySkeleton> getSequels(Document doc) {
        Elements sequels = doc.select("#sequels li.hentry [rel=bookmark previous]");
        ArrayList<StorySkeleton> seqs = new ArrayList<StorySkeleton>();
        for (Element sequel : sequels) {
            seqs.add(new StorySkeleton(sequel.ownText(), getAuthor(doc), sequel.attr("href")));
        }
        return seqs;
    }

    private static String getTitle(Document doc) {
        return doc.select("#title a").first().ownText();
    }

    private static String getAuthor(Document doc) {
        return doc.select("#story .fn").first().ownText();
    }

    private static String getPlainText(Document doc) {
        return Jsoup.parse(doc.select(".entry-content").first().html()).text();
    }

    private static String getText(Document doc) {
        return doc.select(".entry-content").first().html();
    }

    /**
     * @param url
     *            - Valid story url
     * @return Story ID as int, -1 if parse failed.
     */
    public static int getIDfromURL(String url) {
        try {
            return Integer.parseInt(url.replace(storyurl, ""));
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

}
