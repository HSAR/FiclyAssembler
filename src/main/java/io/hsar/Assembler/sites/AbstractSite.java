package io.hsar.Assembler.sites;

import io.hsar.Assembler.model.Story;
import io.hsar.Assembler.model.StorySkeleton;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public abstract class AbstractSite implements Site {

    @Override
    public Story parse(Document doc, String url) {
        return new Story(getPrequels(doc), getSequels(doc), getTitle(doc), getAuthor(doc), url, getText(doc));
    }

    public ArrayList<StorySkeleton> getPrequels(Document doc) {
        Elements prequels = doc.select(getSitePrequelTag());
        ArrayList<StorySkeleton> preqs = new ArrayList<>();
        for (Element prequel : prequels) {
            preqs.add(new StorySkeleton(prequel.ownText(), getAuthor(doc), prequel.attr("href")));
        }
        return preqs;
    }

    public ArrayList<StorySkeleton> getSequels(Document doc) {
        Elements sequels = doc.select(getSiteSequelTag());
        ArrayList<StorySkeleton> seqs = new ArrayList<>();
        for (Element sequel : sequels) {
            seqs.add(new StorySkeleton(sequel.ownText(), getAuthor(doc), sequel.attr("href")));
        }
        return seqs;
    }

    public String getTitle(Document doc) {
        return doc.select(getSiteTitleTag()).first().ownText();
    }

    private String getAuthor(Document doc) {
        return doc.select(getSiteAuthorTag()).first().ownText();
    }

    public String getPlainText(Document doc) {
        return Jsoup.parse(doc.select(getSiteStoryTextTag()).first().html()).text();
    }

    public String getText(Document doc) {
        return doc.select(getSiteStoryTextTag()).first().html();
    }
}
