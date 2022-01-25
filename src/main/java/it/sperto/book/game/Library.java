package it.sperto.book.game;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Library implements Comparable<Library> {
    private int id;
    private int signupDay;
    private int bookPerDay;
    private int bookCanScan;
    private List<Book> books = new ArrayList<>();
    private float score = 0;

    public Library(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSignupDay() {
        return signupDay;
    }

    public void setSignupDay(int signupDay) {
        this.signupDay = signupDay;
    }

    public int getBookCanScan() {
        return bookCanScan;
    }

    public void setBookCanScan(int bookCanScan) {
        this.bookCanScan = bookCanScan;
    }

    public int getBookPerDay() {
        return bookPerDay;
    }

    public void setBookPerDay(int bookPerDay) {
        this.bookPerDay = bookPerDay;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Library.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("signupDay=" + signupDay)
                .add("bookPerDay=" + bookPerDay)
                .add("books=" + books.size())
                .add("score=" + score)
                .toString();
    }

    @Override
    public int compareTo(Library o) {
        if (this.score > o.score)
            return 1;
        else if (o.score > this.score)
            return -1;
        else
            return 0;
    }
}
