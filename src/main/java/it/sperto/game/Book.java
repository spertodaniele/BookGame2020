package it.sperto.game;

import java.util.Objects;
import java.util.StringJoiner;

public class Book implements Comparable<Book> {
    static Integer instanceCount = 0;
    private int id;
    private Integer score;

    public Book(int id, int score) {
        synchronized (instanceCount){
            instanceCount++;
        }
        this.id = id;
        this.score = score;
    }

    public static Integer getInstanceCount(){
        return instanceCount;
    }

    public int getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("score=" + score)
                .toString();
    }

    @Override
    public int compareTo(Book o) {
        if (this.score > o.score)
            return 1;
        else if (o.score > this.score)
            return -1;
        else
            return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
