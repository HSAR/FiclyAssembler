import java.util.Set;

abstract class AbstractAssembler {
    
    private final Set<StorySkeleton> stories;

    private final Story firstStory;

    AbstractAssembler(String seriesTitle, Story firstStory, Set<StorySkeleton> stories) {
        super();
        this.stories = stories;
        this.firstStory = firstStory;
    }
    
    public abstract String toString();
    
    public abstract String toTex();

}
