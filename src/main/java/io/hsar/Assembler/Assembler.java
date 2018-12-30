package io.hsar.Assembler;

import io.hsar.Assembler.model.Series;
import io.hsar.Assembler.model.Story;
import io.hsar.Assembler.sites.SiteUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

class Assembler {

    // "Bender's Key"
    // http://ficly.com/stories/37571

    // "Ascension: System Check"
    // http://ficly.com/stories/36673

    // "The Armory"
    // http://ficly.com/stories/32329

    // "Airships: A Blood-Red Cloak"
    // http://ficly.com/stories/31023

    // "Gunfire: The Target"
    // https://ficlatte.com/stories/40067/

    // "Tube Alloys"
    // https://ficlatte.com/stories/41634/

    private static final String THANKS_LINE_REPLACER = "REPLACE_ME_THANKS_LINE";
    public static String texHeader;

    public Assembler() {
        try {
            texHeader = new String(Files.readAllBytes(Paths.get("bookheader.txt")));
        } catch (IOException e) {
            System.err.println("HEADER FILE READ OPERATION FAILED");
            texHeader = "%% HEADER FILE READ OPERATION FAILED";
        }
    }

    public static void main(String[] args) {
        Assembler fa = new Assembler();
        if (args.length < 1) {
            throw new IllegalArgumentException("Expected a URL in the program argument.");
        }
        fa.run(args[0]);
    }

    private void run(String url) {
        // get the current date in short string format to use as filename
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateStamp = dateformat.format((new Date()).getTime());
        // create and initialise PrintStream object
        PrintStream printstream;

        System.out.println("Program initialised.");

        // detect site, add site-default thanks
        SiteUtils siteUtils = SiteUtils.getFromURL(url);
        texHeader = texHeader.replace(THANKS_LINE_REPLACER, siteUtils.getSiteThanks());

        // auto-detects series name
        Story start = siteUtils.getStory(url);
        String seriesName = start.getSkeleton().getSeries();
        // if the series name was successfully found, then remove the series title from all following stories
        boolean showSeries = seriesName.equals(start.getTitle()); // #TODO: Pass this to DepthFirstAssembler

        System.out.println("Initial fetch complete.");
        System.out.println("Assembling series \"" + seriesName + "\" by \"" + start.getAuthor() + "\"");

        // init Series object
        Series series = new Series(seriesName, start);

        // fetch all HTML necessary to complete series
        series.fetch();

        System.out.println("Data fetch complete.");

        // run typesetting
        String result = series.assembleTex(texHeader);

        System.out.println("Typesetting complete.");

        // print to file
        String saveFileName;
        try {
            saveFileName = siteUtils.getSiteName() + "_" + seriesName + "_" + dateStamp + ".txt";
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
            System.out.println("Output saved to \"" + saveFile.getAbsolutePath() + "\"");
        } catch (FileNotFoundException e) {
            // this is a non-critical method and so does not stack-trace on fail
            System.err.println(e.toString());
        }

        System.out.println("Program run complete.");
    }

}
