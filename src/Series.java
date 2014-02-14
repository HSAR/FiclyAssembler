import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class Series {

    enum AssemblerType {
        depth_first, breadth_first
    };

    private String title;
    private Story start;

    private Queue<StorySkeleton> unvisited;
    private Set<StorySkeleton> stories;

    public Series(String title, Story start) {
        super();
        this.title = title;
        this.start = start;
        unvisited = new PriorityQueue<StorySkeleton>();
        stories = new HashSet<StorySkeleton>();
        stories.add(start.getSkeleton());
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
            for (StorySkeleton sequel : curr.getSequels()) {
                unvisited.add(sequel);
            }
        }
    }

    public String assembleString(AssemblerType at) {
        switch (at) {
        case depth_first:
            return new DepthFirstAssembler(start, stories).toString();
        default:
            break;
        }
        return "";
    }
}
