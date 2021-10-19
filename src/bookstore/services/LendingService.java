package bookstore.services;

import bookstore.entities.Book;
import bookstore.entities.Client;
import bookstore.entities.Lending;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class LendingService {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManager em = Persistence.createEntityManagerFactory("BookstoreTrialPU").createEntityManager();

    public void registerLending() throws Exception {

        Lending lending = new Lending();
        BookService bs = new BookService();
        ClientService cs = new ClientService();
        lending.setDateLended(LocalDate.MIN.now());
        System.out.println("Enter the return date in the next format: YEAR-M-D");
        lending.setDateReturned(LocalDate.parse(leer.next()));
        System.out.println("How many books?");
        int i = leer.nextInt();
        System.out.println("Enter the books name");
        Collection<Book> books = new ArrayList();
        for (int j = 0; j < i; j++) {
            books.add(bs.findBookTitle(leer.next()));
        }
        lending.setBooks(books);
        System.out.println("Enter the client name");
        Client client = new Client();
        client = cs.findClientName(leer.next());
        if (client == null) {
            System.out.println("Please, create a new client: ");
            client = cs.registerClient();
        }
    }

    public void registerRestore(Lending lending) throws Exception {
        if (lending == null) {
            throw new Exception("Error. Lending is empty");
        }
        em.getTransaction().begin();
        em.remove(lending);
        em.getTransaction().commit();
    }

    public Collection<Lending> findClientLendings(String clientName) throws Exception {
        Collection<Lending> lendings = new ArrayList();
        Client client = new Client();
        ClientService cs = new ClientService();
        client = cs.findClientName(clientName);
        if (client == null) {
            throw new Exception("Error. Client doesn't exist");
        }
        lendings = em.createQuery("select l from Lending l where l.client.name" + clientName).getResultList();
        return lendings;
    }

}
