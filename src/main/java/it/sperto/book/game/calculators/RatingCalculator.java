package it.sperto.book.game.calculators;

import it.sperto.book.game.Book;
import it.sperto.book.game.Library;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class RatingCalculator {

    public Library computeBestLibrary(List<Library> libraries, int daysRemaining){
        for (Library library : libraries) {
            calculateLibraryRating(library, daysRemaining);
        }
        Collections.sort(libraries, Collections.reverseOrder());
        Library ret = libraries.get(0);
        Collections.sort(ret.getBooks(), Collections.reverseOrder());
        return ret;
    }

    abstract void calculateLibraryRating(Library library, int daysRemaining);

    static class SimpleRatingCalculator extends RatingCalculator {

        void calculateLibraryRating(Library library, int daysRemaining) {
            int score = 0;
            int dayScan = (int) Math.floor(library.getBooks().size() / library.getBookPerDay());
            int nDayTotal = library.getSignupDay() + dayScan;
            int totalBookScore = 0;
            for (Book book : library.getBooks()) {
                totalBookScore += book.getScore();
            }
            score = totalBookScore / nDayTotal;
            library.setScore(score);
        }
    }


    public static class TimedRatingCalculator extends RatingCalculator {

        void calculateLibraryRating(Library library, int daysRemaining) {
            Collections.sort(library.getBooks(), Collections.reverseOrder());
            int totalBookScore = 0;
            int dayForScan = daysRemaining - library.getSignupDay();
            int nBookCanScan = dayForScan * library.getBookPerDay();

            List<Book> booooks = library.getBooks();
            if (nBookCanScan > booooks.size()) {
                nBookCanScan = booooks.size();
            }

            for (Book book : booooks) {
                totalBookScore += book.getScore();
                nBookCanScan--;
                if (nBookCanScan < 1) break;
            }
            float score = ((float) totalBookScore / (float) daysRemaining)/ library.getSignupDay();
            library.setScore(score);
        }
    }

    public static class BestRatingCalculator extends RatingCalculator {

        void calculateLibraryRating(Library library, int daysRemaining) {
            Collections.sort(library.getBooks(), Collections.reverseOrder());
            int totalBookScore = 0;
            int dayForScan = daysRemaining - library.getSignupDay();
            int nBookCanScan = dayForScan * library.getBookPerDay();

            List<Book> booooks = library.getBooks();
            if (nBookCanScan > booooks.size()) {
                nBookCanScan = booooks.size();
            }

            for (Book book : booooks) {
                totalBookScore += book.getScore();
                nBookCanScan--;
                if (nBookCanScan < 1) break;
            }
            float score = ((float) totalBookScore  )/ library.getSignupDay();
            library.setScore(score);
        }
    }

    public static class RandomRatingCalculator extends RatingCalculator {

        private static float MIN = 0;
        private static float MAX = 1_000_000;
        private static Random RANDOM = new Random();

        void calculateLibraryRating(Library library, int daysRemaining) {
             float score = MIN + RANDOM.nextFloat() * (MAX - MIN);
            library.setScore(score);
        }
    }
}


