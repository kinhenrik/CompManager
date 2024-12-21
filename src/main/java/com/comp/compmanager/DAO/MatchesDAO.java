package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Matches;
import jakarta.persistence.*;

import java.util.List;

public class MatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // CREATE - Lägg till en match
    public void addMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(match);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

        } finally {
            manager.close();
        }
    }

    // READ - Hämta en match via ID
    public Matches getMatchById(int id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return manager.find(Matches.class, id);
        } finally {
            manager.close();
        }
    }

    // READ - Hämta alla matcher
    public static List<Matches> getAllMatches() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return manager.createQuery("SELECT m FROM Matches m JOIN FETCH m.game",Matches.class).getResultList();
        } finally {
            manager.close();
        }
    }

    // UPDATE - Uppdatera en match
    public static void updateMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (!manager.contains(match)) {
                match = manager.merge(match);
            }

            manager.merge(match);
            transaction.commit();
            System.out.println("Match updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Failed to update match!, check code " + e.getMessage());
        } finally {
            manager.close();
        }
    }

    // DELETE - Ta bort en match
    public void deleteMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (!manager.contains(match)) {
                match = manager.merge(match);
            }
            manager.remove(match);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }
}
