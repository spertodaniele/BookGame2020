package it.sperto.book.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponseModel {
    List<ResponseLibraryLine> libraryLines = new ArrayList<>();

    public void addLibraryLines(Library library) {
        ResponseLibraryLine libraryLine = new ResponseLibraryLine(library);
        libraryLines.add(libraryLine);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(libraryLines.size()).append(System.lineSeparator());
        for (ResponseLibraryLine rll : libraryLines) {
            sb.append(rll.libraryId).append(" ").append(rll.booksIds.size()).append(System.lineSeparator());
            sb.append(rll.printBooksLine()).append(System.lineSeparator());
        }

        return sb.toString();
    }


    static class ResponseLibraryLine {
        int libraryId;
        List<Integer> booksIds;

        public ResponseLibraryLine(Library library) {
            this.libraryId = library.getId();
            this.booksIds = library.getBooks().stream().map(book -> book.getId()).collect(Collectors.toList());
        }

        String printBooksLine() {
            return booksIds.stream().map(String::valueOf)
                    .collect(Collectors.joining(" "));
        }

        public int getLibraryId() {
            return libraryId;
        }

        public List<Integer> getBooksIds() {
            return booksIds;
        }
    }

}
