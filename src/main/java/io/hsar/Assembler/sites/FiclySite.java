package io.hsar.Assembler.sites;

import io.hsar.Assembler.model.Story;
import io.hsar.Assembler.model.StorySkeleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class FiclySite implements Site {

    private static final String BASE_STORY_URL = "http://ficly.com/stories/";

    private static final String SITE_NAME = "ficly";

    @Override
    public String getBaseStoryURL() {
        return BASE_STORY_URL;
    }

    @Override
    public String getSiteName() {
        return SITE_NAME;
    }

    public Story parse(Document doc, String url) {
        return new Story(getPrequels(doc), getSequels(doc), getTitle(doc), getAuthor(doc), url, getText(doc));
    }

    private ArrayList<StorySkeleton> getPrequels(Document doc) {
        Elements prequels = doc.select("#prequels li.hentry [rel=bookmark previous]");
        ArrayList<StorySkeleton> preqs = new ArrayList<StorySkeleton>();
        for (Element prequel : prequels) {
            preqs.add(new StorySkeleton(prequel.ownText(), getAuthor(doc), prequel.attr("href")));
        }
        return preqs;
    }

    private ArrayList<StorySkeleton> getSequels(Document doc) {
        Elements sequels = doc.select("#sequels li.hentry [rel=bookmark previous]");
        ArrayList<StorySkeleton> seqs = new ArrayList<StorySkeleton>();
        for (Element sequel : sequels) {
            seqs.add(new StorySkeleton(sequel.ownText(), getAuthor(doc), sequel.attr("href")));
        }
        return seqs;
    }

    private String getTitle(Document doc) {
        return doc.select("#title a").first().ownText();
    }

    private String getAuthor(Document doc) {
        return doc.select("#story .fn").first().ownText();
    }

    private String getPlainText(Document doc) {
        return Jsoup.parse(doc.select(".entry-content").first().html()).text();
    }

    private String getText(Document doc) {
        return doc.select(".entry-content").first().html();
    }

}
