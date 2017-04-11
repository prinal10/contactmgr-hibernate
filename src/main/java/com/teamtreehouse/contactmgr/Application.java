package com.teamtreehouse.contactmgr;

import com.teamtreehouse.contactmgr.model.Contact;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

/**
 * Created by psp58 on 1/19/2017.
 */
public class Application {

    // Hold a reusable reference to a Session Factory (since we need only one   )

    private static final SessionFactory mSessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        //create a StandardServiceRegistry Object...
        // this object will give access to hibernate main services such as jdbc connectivity, hibernate configuration via xml import data from database
        // and also building a session factory


        //StandardServiceRegistryBuilder() creates a builder object that is used to create a Standard Service Registry
        //configure() loads the hibernate config file(i.e the file named hibernate.cfg.xml) from its default location
        //call to build() method will construct Standard Service Registry object
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();


        //metadata sources object which is the manner by which we can start loading the JPA annotated anodised that we reference in the hibernate config file
        // which is simply the contact class annotated in our case.

         //.buildSessionFactory() creats a Session Factory object which is the one object that encapsulates all the configuration of all our mappings,
        // as well as the configuration for how to connect to the database itself.

        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    public static void main(String[] args){


        Contact contact = new Contact.ContactBuilder("Not_valid","Not_valid")
                .withEmail("not_valid@gmail.com")
                .withPhone(1234567896L).build();

        int id = save(contact);

        //display a list of contacts before the update

        System.out.println("\n\nBefore the update");
        fetchAllContacts().stream().forEach(System.out::println);


        //get the persisted contact
        Contact c = findContactById(id);


        //update the contact
        c.setFirstName("Primus");


        //persist the changes
        System.out.println("\n\nUpdating..............");
        update(c);
        System.out.println("\n\nUpdate complete........");

        //display a list of contacts after the update and before delete

        System.out.println("\n\n---------   After the update and Before the Delete  ----------\n\n");
        fetchAllContacts().stream().forEach(System.out::println);


        //delete the contact
        System.out.println("\n\nDeleting........");
        delete(c);
        System.out.println("\n\nDelete Completed.....");


        //display a list of contacts after delete

        System.out.println("\n\n---------   After the Delete  ----------\n\n");
        fetchAllContacts().stream().forEach(System.out::println);





    }

    private static void delete(Contact contact) {

        //open a session
        Session session = mSessionFactory.openSession();

        //begin a transaction
        session.beginTransaction();


        //use the session to update the contact
        session.delete(contact);


        //commit the transaction
        session.getTransaction().commit();


        //close the session
        session.close();

    }


    private static Contact findContactById(int id){

        //open a session

        Session session = mSessionFactory.openSession();


        //retrieve the persistent object (or null if not found) i.e (the Contact object with the id value as the parameter)

        Contact contact = session.get(Contact.class, id);


        //close the session

        session.close();

        return contact;

    }

    private static void update(Contact contact){

        //open a session
        Session session = mSessionFactory.openSession();

        //begin a transaction
        session.beginTransaction();


        //use the session to update the contact
        session.update(contact);


        //commit the transaction
        session.getTransaction().commit();


        //close the session
        session.close();

    }

    @SuppressWarnings("unchecked")
    private static List<Contact> fetchAllContacts(){

        //open a session

        Session session = mSessionFactory.openSession();


        //create criteria object
        // (used to filter results as per one or more restriction)
        // or simply a criteria can be used to produce a list of persisted entity objects
        //here the entity is Contact class as we marked it with @Entity

        // we can add restriction (Contact.class, "restriction")
        //restriction such as restricting results to contacts whose first name is Fiona or
        // whose email contains teamtreehouse.com

        Criteria criteria = session.createCriteria(Contact.class);



        //get a list contact objects according to the criteria object

        List<Contact> contacts = criteria.list();


        //close a session

        session.close();


        return contacts;


    }

    private static int save(Contact contact){


        //open a session

        Session session = mSessionFactory.openSession();

        //begin a transaction

        session.beginTransaction();

        //use the session to save the contact
        //Whenever an entity is saved with a sessions save method,
        ///that save method returns the assigned identifier,
        //whether its generated by application code or by the database, as is the case for us as we used @Id and @GeneratedValue annotation

        int id = (int) session.save(contact);

        //commit the transaction

        session.getTransaction().commit();

        //close session

        session.close();

        return id;




    }
}
