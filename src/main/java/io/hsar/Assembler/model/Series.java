package io.hsar.Assembler.model;

import io.hsar.Assembler.Assembler;
import io.hsar.Assembler.DepthFirstAssembler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Series {

    private String title;
    private Story start;

    private Queue<StorySkeleton> unvisited;
    private Set<StorySkeleton> stories;

    private Set<String> authors;

    public Series(String title, Story start) {
        super();
        this.title = title;
        this.start = start;
        unvisited = new PriorityQueue<>();
        stories = new HashSet<>();
        stories.add(start.getSkeleton());
        authors = new HashSet<>();
        authors.add(start.getSkeleton().getAuthor());
    }

    public void fetch() {
        // add all start skeletons
        unvisited.addAll(start.getSequels());
        while (!unvisited.isEmpty()) {
            Story curr = unvisited.poll().fetch();
            stories.add(curr.getSkeleton());
            authors.add(curr.getSkeleton().getAuthor());
            unvisited.addAll(curr.getSequels());
        }
    }

    public String assembleTex() {
        // auto-prepend headers
        // assembler only handles the individual stories
        String header = Assembler.texheader;
        if (title != null) {
            // set title field, if present
            header = header.replace("===TITLE===", title);
        }
        if (!authors.isEmpty()) {
            // set authors field, if applicable
            StringBuilder authorSectionSB = new StringBuilder("\\author{");
            for (String author : authors) {
                authorSectionSB.append(author);
                authorSectionSB.append("\\\\ \n");
            }
            authorSectionSB.append("}");
            header = header.replace("===AUTHORS===", authorSectionSB.toString());

        }
        String sb = header + new DepthFirstAssembler(start, stories).toTex() +
                "\\end{document}";
        return sb;
    }
}
