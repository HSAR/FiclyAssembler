import java.util.Set;
import java.util.Stack;

public class DepthFirstAssembler extends AbstractAssembler {

    private Stack<StorySkeleton> stack;

    private boolean showTitles = true;
    private boolean showAuthors = true;

    /**
     * @param firstStory
     * 
     *            Runs text assembly
     */
    public DepthFirstAssembler(Story firstStory, Set<StorySkeleton> stories) {
        super(firstStory, stories);
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
        // TODO Auto-generated method stub
        return null;
    }

}
