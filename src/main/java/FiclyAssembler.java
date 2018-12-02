import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FiclyAssembler {

    // "Bender's Key"
    // http://ficly.com/stories/37571

    // "Ascension: System Check"
    // http://ficly.com/stories/36673

    // "The Armory"
    // http://ficly.com/stories/32329

    // "Airships: A Blood-Red Cloak"
    // http://ficly.com/stories/31023

    public static String texheader = "";

    private String seriesName;
    private boolean showSeries;

    public FiclyAssembler() {
        try {
            texheader = new String(Files.readAllBytes(Paths.get("bookheader.txt")));
        } catch (IOException e) {
            System.err.println("HEADER FILE READ OPERATION FAILED");
            texheader = "%% HEADER FILE READ OPERATION FAILED";
        }
    }

    public void run(String url) {
        // get the current date in short string format to use as filename
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateStamp = dateformat.format((new Date()).getTime());
        // create and initialise PrintStream object
        PrintStream printstream;

        System.out.println("Program initialised.");

        // auto-detects series name
        Story start = FiclyUtils.getStoryWithURL(url);
        seriesName = start.getSkeleton().getSeries();
        // if the series name was successfully found, then remove the series title from all following stories
        showSeries = seriesName.equals(start.getTitle());

        System.out.println("Initial fetch complete.");
        System.out.println("Assembling series \"" + seriesName + "\" by \"" + start.getAuthor() + "\"");

        // init Series object
        Series series = new Series(seriesName, start);

        // fetch all HTML necessary to complete series
        series.fetch();

        System.out.println("Data fetch complete.");

        // run typesetting
        String result = series.assembleTex(Series.AssemblerType.depth_first);

        System.out.println("Typesetting complete.");

        // print to file
        String saveFileName = "CRITICAL ERROR";
        try {
            saveFileName = "ficly" + "_" + seriesName + "_" + dateStamp + ".txt";
            // declare savefile object
            File saveFile = new File(saveFileName);
            if (!saveFile.exists()) {
                try {
                    saveFile.createNewFile();
                } catch (IOException e) {
                    System.err.println(e.toString());
                }
            }
            printstream = new PrintStream(new FileOutputStream(saveFileName, true));
            printstream.println(result);
            // close file
            printstream.close();
        } catch (FileNotFoundException e) {
            // this is a non-critical method and so does not stack-trace on fail
            System.err.println(e.toString());
        }

        System.out.println("Program run complete.");
        System.out.println("Output saved to \"" + saveFileName + "\"");
    }

    public static void main(String[] args) {
        FiclyAssembler fa = new FiclyAssembler();
        fa.run(args[0]);
    }

}
