package it.sperto.book.game;

import it.sperto.book.game.calculators.RatingCalculator;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;

public class BookGame {
    private int DAYS = 0;

    private List<Book> allBooks = new ArrayList<>();
    private List<Library> libraries = new ArrayList<>();
    private ResponseModel solution = new ResponseModel();


    static void outOpt(String s) {
        System.out.println(s);
    }

    public String play(File resourceFilepath) throws Exception {
        this.init(resourceFilepath);
        RatingCalculator ratingCalculator = new RatingCalculator.BestRatingCalculator();
        Collections.sort(libraries, Collections.reverseOrder());
        for (int daysRemainig = DAYS; daysRemainig > 0; ) {
            if (libraries.size() == 0) break;
            Library choice = ratingCalculator.computeBestLibrary(libraries, daysRemainig);
            libraries.remove(0);
            daysRemainig -= choice.getSignupDay();
            solution.addLibraryLines(choice);
            cleanBookScore(choice);
            cleanLibraries(choice, daysRemainig);

        }
        String solutionPath = resourceFilepath.getAbsolutePath().replace(".txt", ".solution");

        try (PrintWriter out = new PrintWriter(solutionPath, "UTF-8")) {
            out.write(solution.toString());
        }
        return solutionPath;
    }


    private void cleanBookScore(Library library) {
        for (Book libraryBook : library.getBooks()) {
            for (Book book : allBooks) {
                if (book.equals(libraryBook)) {
                    book.setScore(0);
                }
            }
        }
    }

    private void cleanLibraries(Library library, int dayRemining) {
        libraries.remove(library);
        List<Library> libToRemove = new ArrayList<>();
        for (Library library1 : libraries) {
            if (dayRemining < library1.getSignupDay() + 1) {
                libToRemove.add(library);
            }
        }
        libraries.removeAll(libToRemove);
    }

    private void init(File inputFile) throws Exception {
        try (Stream<String> stream = Files.lines(Paths.get(inputFile.getAbsolutePath()))) {
            List<String> lines = stream.collect(Collectors.toList());

            //PRIMA RIGA DEL FILE DI INPUT
            StringTokenizer tokenizer = new StringTokenizer(lines.get(0), " ");
            out.println("They are " + Integer.parseInt((String) tokenizer.nextElement()) + " books");
            out.println("They are " + Integer.parseInt((String) tokenizer.nextElement()) + " libraries");
            DAYS = Integer.parseInt((String) tokenizer.nextElement());
            out.println("They are " + DAYS + " days to do the work..");

            //SECONDA RIGA..
            tokenizer = new StringTokenizer(lines.get(1), " ");
            int idxBooks = 0;
            //check if the book have same score
            int bookscore = Integer.parseInt((String) tokenizer.nextElement());
            Book book = new Book(idxBooks, bookscore);
            allBooks.add(book);
            idxBooks++;
            while (tokenizer.hasMoreElements()) {
                int score = Integer.parseInt((String) tokenizer.nextElement());
                book = new Book(idxBooks, score);
                allBooks.add(book);
                idxBooks++;
            }

            //LE LIBRERIE...
            Library library = null;
            int libIdx = 0;
            for (int line_idx = 2; line_idx < lines.size(); line_idx++) {
                String line = lines.get(line_idx);
                if (line.length() < 1) {
                    break;
                }
                tokenizer = new StringTokenizer(line, " ");
                if (line_idx % 2 == 0) {
                    int nBooks = 0;
                    nBooks = Integer.parseInt((String) tokenizer.nextElement());
                    int signupDay = Integer.parseInt((String) tokenizer.nextElement());
                    int bookPerDay = Integer.parseInt((String) tokenizer.nextElement());
                    library = new Library(libIdx++);
                    library.setBookPerDay(bookPerDay);
                    library.setSignupDay(signupDay);
                    library.setBooks(new ArrayList<>(nBooks));
                } else {
                    while (tokenizer.hasMoreElements()) {
                        Integer token = Integer.parseInt((String) tokenizer.nextElement());
                        Book bookToAdd = allBooks.get(token);
                        library.getBooks().add(bookToAdd);
                    }
                    libraries.add(library);
                }
            }
        }
    }


}

