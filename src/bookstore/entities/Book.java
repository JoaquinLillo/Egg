package bookstore.entities;

import javax.persistence.*;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long isbn;
    private String title;
    private Integer year;
    private Integer copies;
    private Integer copiesLended;
    private Integer copiesRemaining;
    private boolean released;

    @ManyToOne
    private Author author;

    @ManyToOne
    private Editorial editorial;

    public Book(long isbn, String title, Integer year, Integer copies, Integer copiesLended, boolean released, Author author, Editorial editorial) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.copies = copies;
        this.copiesLended = copiesLended;
        this.copiesRemaining = copies - copiesLended;
        this.released = released;
        this.author = author;
        this.editorial = editorial;
    }

    public Book() {
        this.released = true;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getCopiesLended() {
        return copiesLended;
    }

    public void setCopiesLended(Integer copiesLended) {
        this.copiesLended = copiesLended;
    }

    public Integer getCopiesRemaining() {
        return copiesRemaining;
    }

    public void setCopiesRemaining(Integer copiesRemaining) {
        this.copiesRemaining = copiesRemaining;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString() {
        return "Book{" + "isbn=" + isbn + ", title=" + title + ", year=" + year + ", copies=" + copies + ", copiesLended=" + copiesLended + ", copiesRemaining=" + copiesRemaining + ", released=" + released + ", author=" + author + ", editorial=" + editorial + '}';
    }

}
