import java.util.Set;

public abstract class AbstractAssembler {
    
    protected Set<StorySkeleton> stories;
    protected Story firstStory;

    public AbstractAssembler(Story firstStory, Set<StorySkeleton> stories) {
        super();
        this.stories = stories;
        this.firstStory = firstStory;
    }
    
    public abstract String toString();
    
    public abstract String toTex();

}
