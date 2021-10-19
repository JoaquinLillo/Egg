package bookstore.services;

import bookstore.entities.Editorial;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.*;

public class EditorialService {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManager em = Persistence.createEntityManagerFactory("BookstoreTrialPU").createEntityManager();

    public void persistEditorial(Editorial editorial) throws Exception {
        try {
            if (editorial == null) {
                throw new Exception("Error. The editorial is empty");
            }
            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public void releaseEditorial(int id) {
        Editorial editorial = em.find(Editorial.class, id);
        editorial.setReleased(true);
        em.getTransaction().begin();
        em.merge(editorial);
        em.getTransaction().commit();
    }

    public void editEditorial(int id) throws Exception {
        try {
            Editorial editorial = em.find(Editorial.class, id);
            System.out.println("Which attributes do you want to edit?");
            Field[] editorialFields = Editorial.class.getDeclaredFields();
            String option = leer.next().toLowerCase();
            for (Field editorialField : editorialFields) {
                System.out.println(editorialField.getName());
            }
            switch (option) {
                case "id":
                    System.out.println("Enter the new id");
                    editorial.setId(leer.nextInt());
                    break;
                case "name":
                    System.out.println("Enter the new name:");
                    editorial.setName(leer.next());
                    if (editorial.getName() == null) {
                        throw new Exception("Required field: Enter an editorial's name");
                    }
                    break;
                case "release":
                    System.out.println("Do you want to release the editorial? <Y> or <N>");
                    boolean released = editorial.isReleased();
                    String releaseOrder = leer.next();
                    if (releaseOrder.charAt(0) == 'Y') {
                        released = true;
                    }
                    editorial.setReleased(released);
                    break;
                default:
                    throw new Exception("Error. The entered value is not valid");
            }
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    Collection<Editorial> listEditorials() {
        Collection<Editorial> editorialsCollection = em.createQuery("select e from Editorial e").getResultList();
        ArrayList<Editorial> editorials = new ArrayList<>(editorialsCollection);
        return editorials;
    }

    public Editorial registerEditorial() {
        Editorial editorial = new Editorial();
        try {

            System.out.println("Creating an editorial:");
            System.out.println("Enter the editorial's name");
            editorial.setName(leer.next());
            if (editorial.getName().equals())) {
                
            }
            persistEditorial(editorial);
        } catch (Exception ex) {
            Logger.getLogger(EditorialService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return editorial;
    }
}
