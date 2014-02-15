import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FiclyAssembler {

    // "Bender's Key"
    // http://ficly.com/stories/37571
    
    // "System Check"
    // http://ficly.com/stories/36673
    
    // "The Armory"
    // http://ficly.com/stories/32329
    
    public static String texheader = "";

    public FiclyAssembler() {
        try {
            texheader = new String(Files.readAllBytes(Paths.get("bookheader.txt")));
        } catch (IOException e) {
            System.err.println("HEADER FILE READ OPERATION FAILED");
            texheader = "%% HEADER FILE READ OPERATION FAILED";
        }
    }

    public static void main(String[] args) {
        FiclyAssembler fa = new FiclyAssembler();
        //Series test = new Series("Test Series Please Ignore", FiclyUtils.getStoryWithURL("http://ficly.com/stories/36673"));
        Series test = new Series("Test Series Please Ignore", FiclyUtils.getStoryWithURL("http://ficly.com/stories/32329"));
        test.fetch();
        System.out.println(test.assembleTex(Series.AssemblerType.depth_first));
    }

}
