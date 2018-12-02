package io.hsar.Assembler.sites;

import io.hsar.Assembler.model.Story;
import org.jsoup.nodes.Document;

public interface Site {
    String getBaseStoryURL();

    String getName();

    String getThanksLine();

    String getSitePrequelTag();

    String getSiteSequelTag();

    String getSiteTitleTag();

    String getSiteAuthorTag();

    String getSiteStoryTextTag();

    Story parse(Document doc, String url);
}
