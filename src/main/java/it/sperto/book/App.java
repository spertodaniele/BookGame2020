package it.sperto.book;

import it.sperto.book.game.BookGame;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;

import static java.lang.System.out;

public class App {
    private static final String TXT_SUFFIX = ".txt";
    private static final Locale systemLocale = new Locale(System.getProperty("user.language"), System.getProperty("user.country"));
    private static final DecimalFormat DECIMAL_FORMAT = (DecimalFormat) NumberFormat.getInstance(systemLocale);
    private static String BASE_PATH = "undefined";

    public static void main(String[] args) throws Exception {
        out.println();
        if (args.length == 0) {
            out.println("The only parameter \" BASE_PATH  \" is missing! pass it as first command line argument!");
        }

        Instant startInstant = Instant.now();
        BASE_PATH = args[0];
        out.println("BASE_PATH is " + BASE_PATH);
        File baseDir = new File(BASE_PATH);
        File[] baseDirContent = baseDir.listFiles();
        for (File f : baseDirContent) {
            if (f.getName().endsWith(TXT_SUFFIX)) {
                launchGame(f);
            }
        }

        int totalScore = Judge.computeTotalScore(BASE_PATH);
        out.printf("%nTotal score is: %s%n", DECIMAL_FORMAT.format(totalScore));
        out.println("Heap size " + (Runtime.getRuntime().totalMemory() / 1_000_000) + "MB, elapsed: " + Duration.between(startInstant, Instant.now()).toSeconds() + "s");
        out.println();
    }

    private static void launchGame(File inputFile) throws Exception {
        Instant start = Instant.now();
        out.println("*****************************" + inputFile.getName() + "*****************************");
        BookGame game = new BookGame();
        String outPath = "undefined";
        try {
            outPath = game.play(inputFile);
            int score = Judge.computeScore(inputFile, new File(outPath));
            out.printf("Solution for scenario %s scored %s%n", inputFile.getAbsolutePath(), DECIMAL_FORMAT.format(score));
        } catch (Exception e) {
            out.println("Error processing file " + inputFile.getAbsolutePath());
            e.printStackTrace();
        }
        out.println("Heap size " + (Runtime.getRuntime().totalMemory() / 1_000_000) + "MB, elapsed: " + Duration.between(start, Instant.now()).toSeconds() + "s");
    }

    private static String execCmd(String cmd) throws java.io.IOException {
        out.println(cmd);
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
