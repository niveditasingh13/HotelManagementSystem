package config;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;



public class Hibernateutil {

    private static final EntityManagerFactory emf =
            buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {

        try {

            return Persistence.createEntityManagerFactory(
                    "hotelPU");

        } catch (Exception e) {

            throw new RuntimeException(
                    "EntityManagerFactory creation failed", e);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {

        return emf;
    }

}
