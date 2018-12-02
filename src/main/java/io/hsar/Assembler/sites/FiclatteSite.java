package io.hsar.Assembler.sites;

public class FiclatteSite extends AbstractSite {

    private static final String BASE_STORY_URL = "https://ficlatte.com/stories/";

    private static final String SITE_NAME = "ficlatte";

    private static final String SITE_PREQUEL_TAG = "#prequel-group h4.list-group-item-heading";
    private static final String SITE_SEQUEL_TAG = "#sequel-group h4.list-group-item-heading";
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
