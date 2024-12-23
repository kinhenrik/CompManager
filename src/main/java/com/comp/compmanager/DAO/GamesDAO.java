package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Games;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

//SIMON WESTERLUNDS KLASS

public class GamesDAO {

    //retrieve "myconfig" from persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //CREATE
    public static void addGame(Games games) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(games);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }

    public static List<Games> getAllGames() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Games> listToReturn = new ArrayList<>();

        //Get all players from Table
        TypedQuery<Games> result = manager.createQuery("FROM Games", Games.class);

        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //UPDATE
    public static void updateGame(Games gameToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (manager.contains(gameToUpdate)) {
                manager.persist(gameToUpdate);
            } else {
                Games updatedGame = manager.merge(gameToUpdate);
                System.out.println("Game with ID=" + updatedGame.getId() + " has been updated.");
            }
            manager.merge(gameToUpdate);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }

    //DELETE
    public static void deleteGame(Games game) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (!manager.contains(game)) {
                game = manager.merge(game);
            }
            manager.remove(game);
            transaction.commit();
            System.out.println("Game with ID=" + game.getId() + " has been removed from database.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }

}
