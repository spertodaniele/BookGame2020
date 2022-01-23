package it.sperto;

import it.sperto.game.BookGame;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

import static java.lang.System.out;

public class App {
    private static final String TXT_SUFFIX = ".txt";
    private static String SCOREPY_PATH = null;

    public static void main(String[] args) throws Exception {
        out.println();
        if (args.length == 0) {
            out.println("The only parameter \" BASE_PATH  \" is missing! pass it as first command line argument!");
        }

        Instant startInstant = Instant.now();
        String BASE_PATH = args[0];
        if (args.length > 1) {
            SCOREPY_PATH = args[1];
        }
        out.println("BASE_PATH is "+BASE_PATH);
        out.println("SCOREPY_PATH is "+SCOREPY_PATH);
        File baseDir = new File(BASE_PATH);
        File[] baseDirContent = baseDir.listFiles();

        for (File f : baseDirContent) {
            if (f.getName().endsWith(TXT_SUFFIX)) {
                launchGame(f);
            }
        }

        out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
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
        }catch (Exception e){
            out.println("Error processing file "+ inputFile.getAbsolutePath());
            e.printStackTrace();
        }
        out.println("Heap size " + (Runtime.getRuntime().totalMemory() / 1_000_000) + "MB, elapsed: " + Duration.between(start, Instant.now()).toSeconds() + "s");
        if (SCOREPY_PATH != null){
            String controlScoreCmd = "python "+SCOREPY_PATH+"\\score.py " + inputFile.getAbsolutePath() + " " + outPath;
            String cmdOut = execCmd(controlScoreCmd);
            out.println(cmdOut);
        }
    }

    private static String execCmd(String cmd) throws java.io.IOException {
        out.println(cmd);
        java.util.Scanner s = new java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}
