package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Player;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


public class PlayerDAO {

    //retrieve "myconfig" from persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");


    //CREATE
    public void addPlayer (Player player) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(player);
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
    public Player getPlayerByID (int player_id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Player playerToReturn = manager.find(Player.class, player_id);
        manager.close();
        return playerToReturn;
    }

    public List<Player> getAllPlayers () {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Player> listToReturn = new ArrayList<>();

        //Get all players from Table
        TypedQuery<Player> result = manager.createQuery("FROM Player", Player.class);

        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //UPDATE
    public void updatePlayer (Player playerToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (manager.contains(playerToUpdate)) {
                manager.persist(playerToUpdate);
            } else {
                Player updatedPlayer = manager.merge(playerToUpdate);
                System.out.println("Player with ID=" + updatedPlayer.getId() + " has been updated.");
            }
            manager.merge(playerToUpdate);
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
    public void deletePlayer (Player player) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (!manager.contains(player)) {
                player = manager.merge(player);
            }
            manager.remove(player);
            transaction.commit();
            System.out.println("Player with ID=" + player.getId() + " has been removed from database.");
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
