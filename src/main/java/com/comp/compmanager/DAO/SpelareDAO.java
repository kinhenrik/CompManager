package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Spelare;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class SpelareDAO {

    //Hämtar konfigurationen från persistence.xml-filen som är döpt till "myconfig"
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    //CRUD-operationer
    //CREATE
    public boolean addPlayer(Spelare player) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(player);
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

    //READ
    public Spelare getPlayerByID(int id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Spelare playerToReturn = manager.find(Spelare.class, id);
        manager.close();
        return playerToReturn;
    }

    public List<Spelare> getAllPlayers() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Spelare> listToReturn = new ArrayList<>();

        //Få fram alla i tabellen
        TypedQuery<Spelare> result = manager.createQuery("FROM Spelare", Spelare.class);

        //Få fram mer specifikt i tabellen
        /*TypedQuery<Spelare> result = manager.createQuery("FROM Spelare s WHERE s.lag LIKE :variabel", Spelare.class);
        result.setParameter("variabel", "Fiskmånnen");*/

        listToReturn.addAll(result.getResultList());
        return listToReturn;
    }

    //UPDATE
    public void updatePlayer(Spelare playerToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            if (manager.contains(playerToUpdate)) {
                manager.persist(playerToUpdate);
            } else {
                Spelare updatedPlayer = manager.merge(playerToUpdate);
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
    public void deletePlayer(Spelare player) {
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
