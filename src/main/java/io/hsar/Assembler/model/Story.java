package io.hsar.Assembler.model;

import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class Story {

    private final List<StorySkeleton> prequels;
    private final List<StorySkeleton> sequels;

    private final String text;

    private final StorySkeleton skel;

    public Story(List<StorySkeleton> prequels, List<StorySkeleton> sequels, String title, String author, String url, String text) {
        this.skel = new StorySkeleton(title, author, url);
        skel.setStory(this);
        this.prequels = prequels;
        this.sequels = sequels;
        this.text = text;
    }

    public Story(List<StorySkeleton> prequels, List<StorySkeleton> sequels, StorySkeleton skel, String text) {
        this.skel = skel;
        skel.setStory(this);
        this.prequels = prequels;
        this.sequels = sequels;
        this.text = text;
    }

    public String getAuthor() {
        return skel.getAuthor();
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return skel.getTitle();
    }

    private String getTitle(boolean showSeries) {
        if (showSeries) {
            return skel.getTitle();
        } else {
            String[] splitTitle = skel.getTitle().split(": ");
            return splitTitle[splitTitle.length - 1];
        }
    }

    public List<StorySkeleton> getPrequels() {
        return prequels;
    }

    public int getID() {
        return skel.getID();
    }

    public List<StorySkeleton> getSequels() {
        return sequels;
    }

    public String getText(boolean useTex, boolean addAuthor, boolean addTitle, boolean addSeries) {
        StringBuilder sb = new StringBuilder(text.length() * 2);
        if (addTitle) {
            String title = getTitle(addSeries).replace("&", "\\&");
            if (useTex) {
                sb.append("%% Enumerated chapter\n %%-------------------------------------------------------------------------------\n");
                sb.append("\\chapter{").append(title).append("} \n \n");
            } else {
                sb.append(title).append("\n");
            }
        }
        if (addAuthor) {
            if (!useTex) {
                sb.append(skel.getAuthor()).append("\n");
            }
        }
        String processedText;
        // decode all that random &quote; HTML stuff
        processedText = StringEscapeUtils.unescapeHtml4(text); // #TODO Switch to commons-text
        if (useTex) {
            // escape {} characters
            processedText = processedText.replace("{", "\\{");
            processedText = processedText.replace("}", "\\}");
            // replace <em></em> tags with tex \textit{}
            processedText = processedText.replace("<em>", "\\textit{");
            processedText = processedText.replace("</em>", "}");
            // replace <strong></strong> tags with tex \textbf{}
            processedText = processedText.replace("<strong>", "\\textbf{");
            processedText = processedText.replace("</strong>", "}");
            // add paragraphing at appropriate positions <br.*/>
            //processedText = processedText.replace("<p>", "");
            //processedText = processedText.replace("</p>", "\n\n");
            processedText = processedText.replace("<p>", "\\par ");
            processedText = processedText.replace("</p>", " "); // This space is vital to the operation of detect end-speech-marks
            // add line breaks at appropriate positions (regex used for security), and newline chars to readability.
            // quad backslashes needed to 1) escape java to "\\\\" 2) escape regex to "\\"
            processedText = processedText.replaceAll("<br[\\s]*[/]*>", "\\\\\\\\ \n");
            // escape # characters
            processedText = processedText.replace("#", "\\#");
            // escape ampersand characters
            processedText = processedText.replace("&", "\\&");
            // escape umlaut characters
            processedText = processedText
                    .replace("ä", "\\\"a")
                    .replace("ö", "\\\"o")
                    .replace("ü", "\\\"u")
                    .replace("ß", "\\ss");

            // Ficly handles quote marks, apostrophes and dashes oddly
            processedText = processedText.replace("‘", "`").replace("’", "'").replace("–", "-").replace("—", "-");
            // convert speech marks to Tex formatting
            processedText = replaceSpeechMarksForTex(processedText);
        } else {
            // Ficly handles quote marks, apostrophes and dashes oddly
            processedText = processedText.replace("‘", "`").replace("’", "'").replace("–", "-").replace("—", "-");
        }
        // ellipses are handled oddly too…
        processedText = processedText.replace("…", "... ");
        sb.append(processedText);
        sb.append("\n\n");
        return sb.toString();
    }

    public StorySkeleton getSkeleton() {
        return skel;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((skel == null) ? 0 : skel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Story other = (Story) obj;
        if (skel == null) {
            if (other.skel != null)
                return false;
        } else if (!skel.equals(other.skel))
            return false;
        return true;
    }

    private String replaceSpeechMarksForTex(String text) {
        String result = text;
        result = result.replace("“", " ``").replace("”", "'' ");
        result = result.replaceAll("\\s\"(?!\\s)", " ``").replaceAll("(?<!\\s)\"\\s", "'' ");
        return result;
    }

}
