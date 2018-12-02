package io.hsar.Assembler.sites;

import io.hsar.Assembler.model.StorySkeleton;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class FiclatteSite extends AbstractSite {

    private static final String BASE_URL = "https://ficlatte.com";
    private static final String BASE_STORY_URL = "https://ficlatte.com/stories/";

    private static final String SITE_NAME = "ficlatte";

    private static final String THANKS_LINE = "Thanks to Paul Robertson and all the folks at Ficlatte.";

    private static final String SITE_PREQUEL_TAG = "#prequel-group a.story-link";
    private static final String SITE_SEQUEL_TAG = "#sequel-group a.story-link";
    private static final String SITE_TITLE_TAG = "#main-bit h1";
    private static final String SITE_AUTHOR_TAG = "#main-bit span.byline a";
    private static final String SITE_STORY_TEXT_TAG = ".content-body";

    @Override
    public String getBaseStoryURL() {
        return BASE_STORY_URL;
    }

    @Override
    public String getName() {
        return SITE_NAME;
    }

    @Override
    public String getThanksLine() {
        return THANKS_LINE;
    }

    @Override
    public String getSitePrequelTag() {
        return SITE_PREQUEL_TAG;
    }

    @Override
    public String getSiteSequelTag() {
        return SITE_SEQUEL_TAG;
    }

    @Override
    public String getSiteTitleTag() {
        return SITE_TITLE_TAG;
    }

    @Override
    public String getSiteAuthorTag() {
        return SITE_AUTHOR_TAG;
    }

    @Override
    public String getSiteStoryTextTag() {
        return SITE_STORY_TEXT_TAG;
    }

    @Override
    public List<StorySkeleton> getPrequels(Document doc) {
        List<StorySkeleton> rawPrequels = super.getPrequels(doc);
        List<StorySkeleton> result = new ArrayList<>(rawPrequels.size());
        for (StorySkeleton currStorySkeleton : rawPrequels) {
            result.add(new StorySkeleton(
                    currStorySkeleton.getTitle(),
                    currStorySkeleton.getAuthor(),
                    BASE_URL + currStorySkeleton.getURLString()));
        }
        return result;
    }

    @Override
    public List<StorySkeleton> getSequels(Document doc) {
        List<StorySkeleton> rawSequels = super.getSequels(doc);
        List<StorySkeleton> result = new ArrayList<>(rawSequels.size());
        for (StorySkeleton currStorySkeleton : rawSequels) {
            result.add(new StorySkeleton(
                    currStorySkeleton.getTitle(),
                    currStorySkeleton.getAuthor(),
                    BASE_URL + currStorySkeleton.getURLString()));
        }
        return result;
    }
}
