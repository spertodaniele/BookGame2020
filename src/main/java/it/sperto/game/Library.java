package it.sperto.game;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Library implements Comparable<Library> {
    static Integer instanceCount = 0;
    private int id;
    private int signupDay;
    private int bookPerDay;
    private int bookCanScan;
    private List<Book> books = new ArrayList<>();
    private List<Integer> booksToScanOrder = new ArrayList<>();
    private float score = 0;
    private int totalBookScore = 0;

    public Library(int id) {
        synchronized (instanceCount) {
            instanceCount++;
        }
        this.id = id;
    }

    public static Integer getInstanceCount() {
        return instanceCount;
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

    public List<Integer> getBooksToScanOrder() {
        return booksToScanOrder;
    }

    public void createScanBookList(){
        for (Book book : this.getBooks()) {
            this.booksToScanOrder.add(book.getId());
        }
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", Library.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("signupDay=" + signupDay)
                .add("bookPerDay=" + bookPerDay)
                .add("books=" + books.size())
                .add("booksToScanOrder=" + booksToScanOrder.size())
                .add("score=" + score)
                //.add("totalBookScore=" + totalBookScore)
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
