package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Game;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class GameDAO {

    //retrieve "myconfig" from persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");


    //CREATE
    public void addGame (Game game) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(game);
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

    //GET
    public Game getGameByID (int game_id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Game gameToReturn = manager.find(Game.class, game_id);
        manager.close();
        return gameToReturn;
    }


    public List<Game> getAllGames () {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Game> listToReturn = new ArrayList<>();

        //Get all games from Table
        TypedQuery<Game> result = manager.createQuery("From Game", Game.class);

        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //UPDATE
    public void updateGame (Game gameToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (manager.contains(gameToUpdate)) {
                manager.persist(gameToUpdate);
            } else {
                Game updatedGame = manager.merge(gameToUpdate);
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
    public void deleteGame (Game game) {
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
