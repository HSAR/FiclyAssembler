import java.util.List;

public class Story {

    private List<StorySkeleton> prequels;
    private List<StorySkeleton> sequels;

    private String text;
    
    private StorySkeleton skel;

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

    public String getText() {
        return text;
    }

    public String getTitle() {
        return skel.getTitle();
    }

    public String getTitle(boolean showSeries) {
        if (showSeries) {
            return skel.getTitle();
        } else {
            return skel.getTitle().split(": ")[1];
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
            String title = getTitle(addSeries);
            if (useTex) {
                sb.append("%% Enumerated chapter\n %%-------------------------------------------------------------------------------\n");
                sb.append("\\chapter{" + title + "} \n \n");                
            } else {
                sb.append(title + "\n");
            }
        }
        if (addAuthor) {
            if (!useTex ) {
                sb.append(skel.getAuthor() + "\n");
            }
        }
        String processedText = "";
        if (useTex) {
            // convert speech marks to Tex formatting
            processedText = text.replace("“", "``").replace("”", "''");
            processedText = processedText.replace("&quot;", "''");
            // Ficly handles quote marks, apostrophes and dashes oddly
            processedText = processedText.replace("‘","`").replace("’","'").replace("–","-");
            // replace <em></em> tags with tex \textit{}
            processedText = processedText.replace("<em>", "\\textit{");
            processedText = processedText.replace("</em>", "}");
            // replace <strong></strong> tags with tex \textbf{}
            processedText = processedText.replace("<strong>", "\\textbf{");
            processedText = processedText.replace("</strong>", "}");
            // add paragraphing at appropriate positions <br.*/>
            processedText = processedText.replace("<p>", "");
            processedText = processedText.replace("</p>", "\n\n");
            // add line breaks at appropriate positions (regex used for security)
            processedText = processedText.replaceAll("<br.*(/)*>", "\\\\");
            
        } else {
            // convert apostrophes only
            processedText = text.replaceAll("’","'");      
            // remove paragraph tags and line break tags
            processedText = processedText.replaceAll("<.*>", "");    
        }
        // ellipses are handled oddly too…
        processedText = processedText.replace("…","... ");
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
    
    
}
