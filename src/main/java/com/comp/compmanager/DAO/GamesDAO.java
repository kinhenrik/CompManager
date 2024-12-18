package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Games;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class GamesDAO {

    // Hämta konfigurationen från persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // CREATE - Lägg till ett lag
    public static boolean addGames(Games game) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(game); // Spara laget i databasen
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (manager != null && transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        } finally {
            manager.close();
        }
    }

    // READ - Hämta lag med ID
    public Games getGamesByID(int game_id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Games gameToReturn = manager.find(Games.class, game_id); // Hämtar laget baserat på ID
        manager.close();
        return gameToReturn;
    }

    // READ - Hämta alla lag
    public static List<Games> getAllGames() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Games> listToReturn = new ArrayList<>();

        // Hämta alla lag från Teams-tabellen
        TypedQuery<Games> result = manager.createQuery("FROM Games", Games.class);
        listToReturn.addAll(result.getResultList());
        manager.close();
        return listToReturn;
    }

    // UPDATE - Uppdatera lag
    public static void updateGame(Games gameToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            // Uppdaterar laget om det finns
            if (manager.contains(gameToUpdate)) {
                manager.persist(gameToUpdate); // Om objektet redan är i cache, persist
            } else {
                Games updatedGame = manager.merge(gameToUpdate); // Om inte, använd merge för att uppdatera
                System.out.println("Game with ID = " + updatedGame.getId() + " has been updated.");
            }
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

    // DELETE - Ta bort lag
    public static void deleteGame(Games game) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            // Om laget inte finns i EntityManager, gör merge för att få en referens
            if (!manager.contains(game)) {
                game = manager.merge(game);
            }
            manager.remove(game); // Ta bort laget från databasen
            transaction.commit();
            System.out.println("Game with ID = " + game.getId() + " has been removed from database.");
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