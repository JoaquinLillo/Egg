package bookstore.services;

import bookstore.entities.Client;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class ClientService {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManager em = Persistence.createEntityManagerFactory("BookstoreTrialPU").createEntityManager();

    public Client findClientName(String clientName) throws Exception {
        Client client = new Client();
        if (clientName == null) {
            throw new Exception("Required field: Enter the client's name");
        }
        client = (Client) em.createQuery("select c from Client c where c.name'" + clientName + "'").getSingleResult();
        return client;

    }

    public Client registerClient() {
        Client client = new Client();
        System.out.println("Creating a new Client:");
        System.out.println("Enter the client's document");
        client.setDocument(leer.nextLong());
        System.out.println("Enter the client's name");
        client.setName(leer.next());
        System.out.println("Enter the client's last name");
        client.setLastName(leer.next());
        System.out.println("Enter the client's telephone number");
        client.setTelephoneNumber(leer.next());
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
        return client;
    }

}
