package bookstore.services;

import javax.persistence.*;
import bookstore.entities.Author;
import bookstore.entities.Book;
import bookstore.entities.Editorial;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class AuthorService {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManager em = Persistence.createEntityManagerFactory("BookstoreTrialPU").createEntityManager();

    public static void main(String[] args) throws Exception {
        AuthorService as = new AuthorService();
        BookService bs = new BookService();
        EditorialService es = new EditorialService();
        Author author = new Author(1, "Juan Carlos", true);
        Author author1 = new Author(2, "Pepe Lui", true);
        Author author2 = new Author(3, "Ernesto Chupapija", true);
        Author author3 = new Author(4, "Raúl HIJODEPUTA", true);
        Author author4 = new Author();

        author4 = as.findAuthorName("Juan Carlos");
        System.out.println(author4.toString());
        Editorial editorial = new Editorial(1, "Siglo XXI", true);
        Collection<Book> books = new ArrayList();
        Book book = new Book(1, "Don Quijote", 1803, 20, 10, true, author, editorial);
        Book book1 = new Book(2, "Pijole", 2003, 20, 10, true, author, editorial);
        Book book2 = new Book(3, "Botellón", 1910, 20, 10, true, author, editorial);
        Book book3 = new Book(4, "Alberto Johnsonn", 1950, 20, 10, true, author, editorial);
        Book book4 = new Book();
        books = bs.findBooksEditorial(editorial.getName());
        for (Book book5 : books) {
            System.out.println("xd");
            System.out.println(book5.toString());
        }

    }

    public void persistAuthor(Author author) throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("BookstoreTrialPU").createEntityManager();
        try {
            if (author == null) {
                throw new Exception("Error: Author is empty");
            }
            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public void releaseAuthor(int id) {
        Author author = em.find(Author.class, id);
        author.setReleased(false);
        em.getTransaction().begin();
        em.merge(author);
        em.getTransaction().commit();
    }

    public void editAuthor(int id) throws Exception {
        Author author = em.find(Author.class, id);
        System.out.println("Which attributes do you want to edit?");
        Field[] authorFields = Author.class.getDeclaredFields();
        try {
            String option = leer.next().toLowerCase();
            for (Field authorField : authorFields) {
                System.out.println(authorField.getName());
            }
            switch (option) {
                case "id":
                    System.out.println("Enter the new id");
                    author.setId(leer.nextInt());
                    if (author.getId() == null) {
                        throw new Exception("Required field: Enter an ID");
                    }

                    break;
                case "name":
                    System.out.println("Enter the new name:");
                    author.setName(leer.next());
                    if (author.getName() == null) {
                        throw new Exception("Required field: Enter a name");
                    }
                    break;
                case "release":
                    System.out.println("Do you want to release the author? <Y> or <N>");
                    boolean released = author.isReleased();
                    String releaseOrder = leer.next();
                    if (releaseOrder.charAt(0) == 'Y') {
                        released = false;
                    }
                    author.setReleased(released);
                    break;
                default:
                    throw new Exception("The entered value is not valid");
            }
            em.getTransaction().begin();
            em.merge(author);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }

    }

    public Collection<Author> listAuthors() {
        Collection<Author> authors = em.createQuery("select a from Author a").getResultList();
        return authors;
    }

    public Author findAuthorName(String authorName) throws Exception {
        Author author = new Author();
        AuthorService as = new AuthorService();
        try {
            if (authorName == null) {
                throw new Exception("Required field: Enter an author's name");
            }
            author = (Author) em.createQuery("select a from Author a WHERE a.name='" + authorName + "'").getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        return author;
    }

    public Author registerAuthor() throws Exception {
        Author author = new Author();
        try {
            System.out.println("Creating an author:");
            System.out.println("Enter the author's name");
            author.setName(leer.next());
            if (author.getName().equals(findAuthorName(author.getName()).getName())) {
                System.out.println("Error. The author was already registered");
            }
            persistAuthor(author);
        } catch (Exception ex) {
            throw ex;
        }
        return author;
    }

}
