import java.util.Set;

public abstract class AbstractAssembler {
    
    protected Set<StorySkeleton> stories;

    private String seriesTitle;
    protected Story firstStory;

    public AbstractAssembler(String seriesTitle, Story firstStory, Set<StorySkeleton> stories) {
        super();
        this.seriesTitle = seriesTitle;
        this.stories = stories;
        this.firstStory = firstStory;
    }
    
    public abstract String toString();
    
    public abstract String toTex();

}
