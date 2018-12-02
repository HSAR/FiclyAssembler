package io.hsar.Assembler;

import io.hsar.Assembler.model.Story;
import io.hsar.Assembler.model.StorySkeleton;

import java.util.Set;
import java.util.Stack;

public class DepthFirstAssembler extends AbstractAssembler {

    private final Stack<StorySkeleton> stack;

    private final boolean showSeries = false;
    private boolean showTitles = true;

    
    public DepthFirstAssembler(String seriesTitle, Story firstStory, Set<StorySkeleton> stories) {
        super(seriesTitle, firstStory, stories);
        stack = new Stack<>();
        stack.push(firstStory.getSkeleton());
    }


    public DepthFirstAssembler(Story firstStory, Set<StorySkeleton> stories) {
        super(null, firstStory, stories);
        stack = new Stack<>();
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
                boolean showAuthors = true;
                sb.append(curr.getText(false, showAuthors, showTitles, showSeries));
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
                // useTex = TRUE, authors cannot be shown in Tex (per story, anyway)
                sb.append(curr.getText(true, false, showTitles, showSeries));
                for (StorySkeleton sequel : curr.getSequels()) {
                    stack.push(sequel);
                }
            }
        }
        return sb.toString();
    }

}
