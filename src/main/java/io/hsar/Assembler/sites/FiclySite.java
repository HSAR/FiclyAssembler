package io.hsar.Assembler.sites;

public class FiclySite extends AbstractSite {

    private static final String BASE_STORY_URL = "http://ficly.com/stories/";

    private static final String SITE_NAME = "ficly";

    private static final String THANKS_LINE = "Thanks to Kevin Lawver and all the folks at Ficly.";

    private static final String SITE_PREQUEL_TAG = "#prequels li.hentry [rel=bookmark previous]";
    private static final String SITE_SEQUEL_TAG = "#sequels li.hentry [rel=bookmark previous]";
    private static final String SITE_TITLE_TAG = "#title a";
    private static final String SITE_AUTHOR_TAG = "#story .fn";
    private static final String SITE_STORY_TEXT_TAG = ".entry-content";

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
}
