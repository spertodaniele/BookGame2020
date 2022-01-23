package it.sperto.book;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Judge {

    private int days;
    private List<Book> books;
    private List<Library> libraries;

    public static void main(String... args) throws Exception {
        int totalScore = 0;
        File solutionsDirectory = new File("resources");
        File scenariosDirectory = new File("resources");

        for (File solution : solutionsDirectory.listFiles((dir, name) -> name.endsWith(".solution"))) {
            try {
                File scenario = new File(scenariosDirectory, "/" + solution.getName().replace(".solution", ".txt"));
                try (Reader scenarioIn = new InputStreamReader(new FileInputStream(scenario), StandardCharsets.US_ASCII);
                     Reader solutionIn = new InputStreamReader(new FileInputStream(solution), StandardCharsets.US_ASCII)) {
                    Judge judge = Judge.forScenario(scenarioIn);
                    int score = judge.computeScore(solutionIn);
                    System.out.printf("Solution for scenario %s scored %d%n", scenario, score);
                    totalScore += score;
                }
            } catch (Exception e) {
                throw new RuntimeException("Error in solution " + solution.getAbsolutePath(), e);
            }
        }

        System.out.printf("Total score: %d%n", totalScore);
    }

    private int computeScore(Reader in) {
        int score = 0;
        Scanner scanner = new Scanner(in);
        List<Signup> signups = listOfSize(scanner.nextInt());
        for (int i = 0; i < signups.size(); i++) {
            Signup signup = new Signup(libraries.get(scanner.nextInt()));
            signups.set(i, signup);
            signup.shipments = listOfSize(scanner.nextInt());
            for (int s = 0; s < signup.shipments.size(); s++) {
                Book book = books.get(scanner.nextInt());
                signup.shipments.set(s, new Shipment(book));
            }
        }

        Signup currentSignup = null;

        for (int day = 0; day < days; day++) {
            if (currentSignup != null && currentSignup.isFinished()) {
                int lastTheoreticShipmentIndex = currentSignup.library.shipmentsPerDay * (days - day);
                for (int s = 0; s < Math.min(currentSignup.shipments.size(), lastTheoreticShipmentIndex); s++) {
                    Book book = currentSignup.shipments.get(s).book;
                    if (!currentSignup.library.hasBook(book)) {
                        throw new RuntimeException("Library " + currentSignup.library.id + " does not have book " + book.id);
                    }
                    book.award();
                }
                currentSignup = null;
            }

            if (currentSignup == null && signups.size() > 0) {
                currentSignup = signups.remove(0);
            }

            if (currentSignup != null) {
                currentSignup.advance();
            }
        }

        for (Book book : books) {
            if (book.awarded) {
                score += book.score;
            }
        }

        return score;
    }

    private static class Signup {

        private final Library library;

        private List<Shipment> shipments;

        private int passedDays;

        private Signup(Library library) {
            this.library = library;
        }

        public void advance() {
            passedDays++;
            if (isFinished()) {
                library.signedUp = true;
            }
        }

        public boolean isFinished() {
            return passedDays > library.signupDays;
        }
    }

    private static class Shipment {

        private final Book book;

        private Shipment(Book book) {
            this.book = book;
        }
    }

    private static Judge forScenario(Reader in) {
        Judge judge = new Judge();
        Scanner scanner = new Scanner(in);
        judge.books = listOfSize(scanner.nextInt());
        judge.libraries = listOfSize(scanner.nextInt());
        judge.days = scanner.nextInt();

        for (int bookId = 0; bookId < judge.books.size(); bookId++) {
            judge.books.set(bookId, new Book(bookId, scanner.nextInt()));
        }

        for (int libraryId = 0; libraryId < judge.libraries.size(); libraryId++) {
            Library library = new Library(libraryId);
            judge.libraries.set(libraryId, library);
            library.books = listOfSize(scanner.nextInt());
            library.signupDays = scanner.nextInt();
            library.shipmentsPerDay = scanner.nextInt();
            for (int bookIndex = 0; bookIndex < library.books.size(); bookIndex++) {
                library.books.set(bookIndex, judge.books.get(scanner.nextInt()));
            }
        }

        return judge;
    }

    private static class Book {

        private final int id;
        private final int score;
        private boolean awarded;

        private Book(int id, int score) {
            this.id = id;
            this.score = score;
        }

        public void award() {
            awarded = true;
        }
    }

    private static class Library {

        private final int id;
        public List<Book> books;
        public int signupDays;
        public int shipmentsPerDay;
        public boolean signedUp;

        private Library(int id) {
            this.id = id;
        }

        public boolean hasBook(Book book) {
            for (Book thatBook : books) {
                if (thatBook.id == book.id) {
                    return true;
                }
            }

            return false;
        }
    }

    private static <T> List<T> listOfSize(Integer size) {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(null);
        }
        return list;
    }

}
