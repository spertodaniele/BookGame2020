package it.sperto.game.calculators;

import it.sperto.game.Book;
import it.sperto.game.Library;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public interface ScoreCalculator {

    void calculateLibraryScore(Library library, int days);

    static class SimpleScoreCalculator implements ScoreCalculator {

        public void calculateLibraryScore(Library library, int days) {
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


    static class TimedScoreCalculator implements ScoreCalculator {

        public void calculateLibraryScore(Library library, int days) {
            Collections.sort(library.getBooks(), Collections.reverseOrder());
            int totalBookScore = 0;
            int dayForScan = days - library.getSignupDay();
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
            float score = ((float) totalBookScore / (float) days )/ library.getSignupDay();
            library.setScore(score);
        }
    }

    static class TimeAndParralelScoreCalculator implements ScoreCalculator {

        private static float MIN = 0;
        private static float MAX = 100;
        private static Random RANDOM = new Random();

        public void calculateLibraryScore(Library library, int days) {
            Collections.sort(library.getBooks(), Collections.reverseOrder());
            int totalBookScore = 0;
            int dayForScan = days - library.getSignupDay();
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

    static class RandomScoreCalculator implements ScoreCalculator {

        private static float MIN = 0;
        private static float MAX = 1_000_000;
        private static Random RANDOM = new Random();

        public void calculateLibraryScore(Library library, int days) {
            library.setScore(MIN + RANDOM.nextFloat() * (MAX - MIN));
        }
    }
}


