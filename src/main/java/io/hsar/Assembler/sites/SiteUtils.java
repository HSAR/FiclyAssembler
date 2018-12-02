package io.hsar.Assembler.sites;

import io.hsar.Assembler.model.Story;
import io.hsar.Assembler.model.StorySkeleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SiteUtils {

    private Site site;

    private SiteUtils(Site site) {
        this.site = site;
    }

    public static SiteUtils getFromURL(String url) {
        if (url.startsWith(new FiclySite().getBaseStoryURL())) {
            return new SiteUtils(new FiclySite());
        }
        if (url.startsWith(new FiclatteSite().getBaseStoryURL())) {
            return new SiteUtils(new FiclatteSite());
        }

        throw new IllegalArgumentException("Unsupported URL: '" + url + "'");
    }

    public Story getStory(int ID) {
        return getStory(site.getBaseStoryURL() + ID);
    }

    public Story getStory(StorySkeleton skel) {
        return getStory(skel.getURLString());
    }

    public Story getStory(String url) {
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
            return site.parse(doc, url);
        } catch (IOException e) {
            System.err.println("Could not fetch page.");
            return null;
        }
    }

    public String getSiteName() {
        return site.getName();
    }

    public String getSiteThanks() {
        return site.getThanksLine();
    }
}
