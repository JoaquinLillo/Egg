package bookstore.services;

import bookstore.entities.Author;
import bookstore.entities.Book;
import bookstore.entities.Book;
import bookstore.entities.Editorial;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import javax.persistence.*;

public class BookService {

    EntityManager em = Persistence.createEntityManagerFactory("BookstoreTrialPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static void main(String[] args) {
        BookService bs = new BookService();
        try {
            bs.editBook(3);
        } catch (Exception e) {
            throw e;
        }

    }

    public Book registerBook() throws Exception {
        Book book = new Book();
        BookService bs = new BookService();
        try {
            System.out.println("Enter the new title:");
            book.setTitle(leer.next());
            if (book.getTitle() == null) {
                throw new Exception("Required field: Enter the book title");
            }
            if (book.getTitle().equals(bs.findBookTitle(book.getTitle()).getTitle())) {
                throw new Exception("The book was already registered");
            }
            System.out.println("Enter the book's ISBN");
            book.setIsbn(leer.nextLong());
            if (book == bs.findBookISBN(book.getIsbn())) {
                throw new Exception("The book was already registered");
            }
            System.out.println("Enter the book's year:");
            book.setYear(leer.nextInt());
            System.out.println("Enter the number of copies:");
            book.setCopies(leer.nextInt());
            System.out.println("Enter the number of copies lended:");
            book.setCopiesLended(leer.nextInt());
            System.out.println("Enter the author name");
            Author author = new Author();
            author.setName(leer.next());
            if (author.getName() == null) {
                throw new Exception("Required field: Enter the author's name");
            }
            AuthorService as = new AuthorService();
            Collection<Author> authors = as.listAuthors();
            for (Author author1 : authors) {
                if (author1.getName().equals(author.getName())) {
                    author = author1;
                }
            }
            book.setAuthor(author);
            System.out.println("Enter the new editorial");
            Editorial editorial = new Editorial();
            editorial.setName(leer.next());
            if (editorial.getName() == null) {
                throw new Exception("Required field: Enter the editorial's name");
            }
            EditorialService es = new EditorialService();
            Collection<Editorial> editorials = es.listEditorials();
            for (Editorial editorial1 : editorials) {
                if (editorial1.getName().equals(editorial.getName())) {
                    editorial = editorial1;
                }
            }
            book.setEditorial(editorial);
            persistBook(book);
        } catch (Exception e) {
            throw e;
        }
        return book;
    }

    public void persistBook(Book book) throws Exception {
        if (book == null) {
            throw new Exception("Error. The book is empty");
        }
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
    }

    public void releaseBook(long id) {
        Book book = em.find(Book.class,
                id);
        book.setReleased(true);
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    public void editBook(long id) {
        Book book = em.find(Book.class,
                id);
        System.out.println("Which attributes do you want to edit?");
        String option = leer.next().toLowerCase();
        Field[] bookFields = Book.class
                .getDeclaredFields();
        for (Field bookField : bookFields) {
            System.out.println(bookField.getName());
        }
        switch (option) {
            case "isbn":
                System.out.println("Enter the new ISBN");
                book.setIsbn(leer.nextLong());
                break;
            case "title":
                System.out.println("Enter the new title:");
                book.setTitle(leer.next());
                break;
            case "year":
                System.out.println("Enter the new year:");
                book.setYear(leer.nextInt());
                break;
            case "copies":
                System.out.println("Enter the new number of copies:");
                book.setCopies(leer.nextInt());
                break;
            case "copieslended":
                System.out.println("Enter the new number of copies lended:");
                book.setCopiesLended(leer.nextInt());
                break;
            case "copiesremaining":
                System.out.println("Enter the new number of copies remaining:");
                book.setCopiesRemaining(leer.nextInt());
                break;
            case "author":
                System.out.println("Enter the new author name");
                Author author = new Author();
                author.setName(leer.next());
                AuthorService as = new AuthorService();
                Collection<Author> authors = as.listAuthors();
                for (Author author1 : authors) {
                    if (author1.getName().equals(author.getName())) {
                        author = author1;
                    }
                }
                book.setAuthor(author);
                break;
            case "editorial":
                System.out.println("Enter the new editorial");
                Editorial editorial = new Editorial();
                editorial.setName(leer.next());
                EditorialService es = new EditorialService();
                Collection<Editorial> editorials = es.listEditorials();
                for (Editorial editorial1 : editorials) {
                    if (editorial1.getName().equals(editorial.getName())) {
                        editorial = editorial1;
                    }
                }
                book.setEditorial(editorial);
                break;
            case "release":
                System.out.println("Do you want to release the book? <Y> or <N>");
                boolean released = book.isReleased();
                String releaseOrder = leer.next();
                if (releaseOrder.charAt(0) == 'Y') {
                    released = true;
                }
                book.setReleased(released);
                break;
            default:
                throw new AssertionError();
        }
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    public Book findBookISBN(long ISBN) {
        Book book = new Book();
        BookService as = new BookService();
        System.out.println(ISBN);
        try {
            book = (Book) em.createQuery("select b from Book b WHERE b.isbn=" + ISBN).getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        return book;
    }

    public Book findBookTitle(String title) throws Exception {
        Book book = new Book();
        BookService as = new BookService();
        try {
            if (title == null) {
                throw new Exception("Required fiel: Enter the book's title");
            }
            book = (Book) em.createQuery("select b from Book b WHERE b.title='" + title + "'").getSingleResult();
        } catch (Exception e) {
            throw e;
        }
        return book;
    }

    public Collection<Book> findBooksAuthor(String authorName) {
        Collection<Book> books = em.createQuery("select b from Book b where b.author.name='" + authorName + "'").getResultList();
        return books;
    }

    public Collection<Book> findBooksEditorial(String editorialName) {
        Collection<Book> books = em.createQuery("select b from Book b where b.editorial.name='" + editorialName + "'").getResultList();
        return books;
    }

}
