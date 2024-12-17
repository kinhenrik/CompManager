//package com.comp.compmanager.DAO;
//
//import com.comp.compmanager.entities.PlayerMatches;
//import jakarta.persistence.*;
//
//import java.util.List;
//
//public class PlayerMatchDAO {
//
//    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");
//
//    // CREATE - Lägg till en match
//    public boolean addPlayerMatch(PlayerMatches playerMatch) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            transaction = manager.getTransaction();
//            transaction.begin();
//            manager.persist(playerMatch);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//            return false;
//        } finally {
//            manager.close();
//        }
//    }
//
//    // READ - Hämta en match via ID
//    public PlayerMatches getPlayerMatchById(int id) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        try {
//            return manager.find(PlayerMatches.class, id);
//        } finally {
//            manager.close();
//        }
//    }
//
//    // READ - Hämta alla matcher
//    public static List<PlayerMatches> getAllPlayerMatches() {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        try {
//            return manager.createQuery("FROM PlayerMatches", PlayerMatches.class).getResultList();
//        } finally {
//            manager.close();
//        }
//    }
//
//    // UPDATE - Uppdatera en match
//    public static boolean updatePlayerMatch(PlayerMatches playerMatch) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            transaction = manager.getTransaction();
//            transaction.begin();
//            manager.merge(playerMatch);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//            return false;
//        } finally {
//            manager.close();
//        }
//    }
//
//    // DELETE - Ta bort en match
//    public boolean deletePlayerMatch(PlayerMatches playerMatch) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            transaction = manager.getTransaction();
//            transaction.begin();
//            if (!manager.contains(playerMatch)) {
//                playerMatch = manager.merge(playerMatch);
//            }
//            manager.remove(playerMatch);
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//            return false;
//        } finally {
//            manager.close();
//        }
//    }
//}
