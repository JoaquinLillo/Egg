package bookstore.entities;

import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.*;

@Entity
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Temporal(TemporalType.DATE)
    private LocalDate dateLended;
    @Temporal(TemporalType.DATE)
    private LocalDate dateReturned;
    @OneToMany
    private Collection<Book> books;
    @OneToOne
    private Client client;

    public Lending() {
    }

    public Lending(int id, LocalDate dateLended, LocalDate dateReturned, Collection<Book> books, Client client) {
        this.id = id;
        this.dateLended = dateLended;
        this.dateReturned = dateReturned;
        this.books = books;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateLended() {
        return dateLended;
    }

    public void setDateLended(LocalDate dateLended) {
        this.dateLended = dateLended;
    }

    public LocalDate getDateReturned() {
        return dateReturned;
    }

    public void setDateReturned(LocalDate dateReturned) {
        this.dateReturned = dateReturned;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
