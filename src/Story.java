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

    public String getTitle(boolean stripSeries) {
        if (stripSeries) {
            return skel.getTitle().split(": ")[1];
        } else {
            return skel.getTitle();
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

    public String getText(boolean useTex, boolean addAuthor, boolean addTitle) {
        StringBuilder sb = new StringBuilder(text.length() * 2);
        if (addTitle) {
            if (useTex) {
                sb.append("%% Enumerated chapter\n %%-------------------------------------------------------------------------------\n");
                sb.append("\\chapter{" + skel.getTitle() + "} \n \n");                
            } else {
                sb.append(skel.getTitle() + "\n");
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
            processedText = text.replaceAll("“", "``").replaceAll("”", "''");
            // Ficly handles apostrophes and dashes oddly
            processedText = processedText.replaceAll("’","'").replaceAll("–","-");
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
