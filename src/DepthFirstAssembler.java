import java.util.Set;
import java.util.Stack;

public class DepthFirstAssembler extends AbstractAssembler {

    private Stack<StorySkeleton> stack;
    
    private boolean showTitles = true;
    private boolean showAuthors = true;
    
    public DepthFirstAssembler(String seriesTitle, Story firstStory, Set<StorySkeleton> stories) {
        super(seriesTitle, firstStory, stories);
        stack = new Stack<StorySkeleton>();
        stack.push(firstStory.getSkeleton());
    }

    
    public DepthFirstAssembler(Story firstStory, Set<StorySkeleton> stories) {
        super(null, firstStory, stories);
        stack = new Stack<StorySkeleton>();
        stack.push(firstStory.getSkeleton());
    }

    public boolean isShowTitles() {
        return showTitles;
    }

    public void setShowTitles(boolean showTitles) {
        this.showTitles = showTitles;
    }

    // non-TeX
    public String toString() {
        StringBuilder sb = new StringBuilder(1024);
        while (!stack.isEmpty()) {
            Story curr = stack.pop().getStory();
            if (curr != null) {
                sb.append(curr.getText(false, showAuthors, showTitles));
                for (StorySkeleton sequel : curr.getSequels()) {
                    stack.push(sequel);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String toTex() {
        // auto-add header boilerplate
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            Story curr = stack.pop().getStory();
            if (curr != null) {
                // useTex = TRUE, authors cannot be shown in Tex (per story, anyway), showTitles = configurable
                sb.append(curr.getText(true, false, showTitles));
                for (StorySkeleton sequel : curr.getSequels()) {
                    stack.push(sequel);
                }
            }
        }
        return sb.toString();
    }

}
