package io.hsar.Assembler.sites;

import io.hsar.Assembler.model.Story;
import org.jsoup.nodes.Document;

public interface Site {
    String getBaseStoryURL();

    Story parse(Document doc, String url);
}
