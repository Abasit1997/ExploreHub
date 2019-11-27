package authentification;

import handlers.Convenience;
import javafx.scene.control.Alert;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Singleton Class which servers as an interface to the Database with Guest rights
 *
 * @author: Gheorghe Mironica
 */
public class GuestConnectionSingleton {
    private static EntityManagerFactory factory;
    private static GuestConnectionSingleton ourInstance = null;
    private static EntityManager em;
    private final String PERSISTENCE_UNIT_NAME = "Guest";

    public static GuestConnectionSingleton getInstance() {
        if(ourInstance == null){
            ourInstance = new GuestConnectionSingleton();
        }
        return ourInstance;
    }

    /**
     * Method that returns an instance to this class
     */
    private GuestConnectionSingleton() {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            em = factory.createEntityManager();
            System.out.println("Guest Connection started");
        }catch(Exception e){
            e.printStackTrace();
            Convenience.showAlert(Alert.AlertType.INFORMATION, "Internet Connection", "Oops, looks like you have no internet connection","Try later.");
            return;
        }
    }
    // fetch ze manager
    /**
     * Method that returns an instance to an associated with a persistence context. It is used to create, remove, update, find entities.
     */
    public EntityManager getManager(){
        return em;
    }

    /**
     * Method which closes this connection
     */
    public void closeConnection(){
        System.out.println("Guest connection shut down");
        em = null;
        ourInstance = null;
    }
}
