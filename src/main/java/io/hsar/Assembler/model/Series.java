package io.hsar.Assembler.model;

import io.hsar.Assembler.DepthFirstAssembler;
import io.hsar.Assembler.FiclyAssembler;
import io.hsar.Assembler.sites.FiclyUtils;

import java.util.*;

public class Series {

    public String assembleString() {
        return new DepthFirstAssembler(start, stories).toString();
    }

    ;

    private String title;
    private Story start;

    private Queue<StorySkeleton> unvisited;
    private Set<StorySkeleton> stories;

    private Set<String> authors;

    public Series(String title, Story start) {
        super();
        this.title = title;
        this.start = start;
        unvisited = new PriorityQueue<StorySkeleton>();
        stories = new HashSet<StorySkeleton>();
        stories.add(start.getSkeleton());
        authors = new HashSet<String>();
        authors.add(start.getSkeleton().getAuthor());
    }

    public Series(int ID) {
        super();
        this.start = FiclyUtils.getStoryWithID(ID);
        this.title = start.getTitle().split(": ")[0];
    }

    public void fetch() {
        // add all start skeletons
        for (StorySkeleton sequel : start.getSequels()) {
            unvisited.add(sequel);
        }
        while (!unvisited.isEmpty()) {
            Story curr = unvisited.poll().fetch();
            stories.add(curr.getSkeleton());
            authors.add(curr.getSkeleton().getAuthor());
            for (StorySkeleton sequel : curr.getSequels()) {
                unvisited.add(sequel);
            }
        }
    }

    public String assembleTex() {
        // auto-prepend headers
        // assembler only handles the individual stories
        String header = FiclyAssembler.texheader;
        if (title != null) {
            // set title field, if present
            header = header.replace("===TITLE===", title);
        }
        if (!authors.isEmpty()) {
            // set authors field, if applicable
            StringBuilder authorSectionSB = new StringBuilder("\\author{");
            Iterator<String> itr = authors.iterator();
            while (itr.hasNext()) {
                authorSectionSB.append(itr.next());
                authorSectionSB.append("\\\\ \n");
            }
            authorSectionSB.append("}");
            header = header.replace("===AUTHORS===", authorSectionSB.toString());

        }
        StringBuilder sb = new StringBuilder(header);
        sb.append(new DepthFirstAssembler(start, stories).toTex());
        sb.append("\\end{document}");
        return sb.toString();
    }

    enum AssemblerType {
        depth_first, breadth_first
    }
}
