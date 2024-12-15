package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Teams;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

public class TeamManagerDAO {

    // Hämta konfigurationen från persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // CREATE - Lägg till ett lag
    public boolean addTeam(Teams team) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            manager.persist(team); // Spara laget i databasen
            transaction.commit();
            System.out.println("New team added with id: " + team.getId() + " has been added in database.");
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
    public Teams getTeamByID(int team_id) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        Teams teamToReturn = manager.find(Teams.class, team_id); // Hämtar laget baserat på ID
        manager.close();
        return teamToReturn;
    }

    // READ - Hämta alla lag
    public static List<Teams> getAllTeams() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        List<Teams> listToReturn = new ArrayList<>();

        // Hämta alla lag från Teams-tabellen
        TypedQuery<Teams> result = manager.createQuery("FROM Teams", Teams.class);
        listToReturn.addAll(result.getResultList());
        manager.close();
        return listToReturn;
    }

    // UPDATE - Uppdatera lag
    public void updateTeam(Teams teamToUpdate) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            // Uppdaterar laget om det finns
            if (manager.contains(teamToUpdate)) {
                manager.persist(teamToUpdate); // Om objektet redan är i cache, persist
            } else {
                Teams updatedTeam = manager.merge(teamToUpdate); // Om inte, använd merge för att uppdatera
                System.out.println("Team with ID = " + updatedTeam.getId() + " has been updated.");
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
    public void deleteTeam(Teams team) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();
            // Om laget inte finns i EntityManager, gör merge för att få en referens
            if (!manager.contains(team)) {
                team = manager.merge(team);
            }
            manager.remove(team); // Ta bort laget från databasen
            transaction.commit();
            System.out.println("Team with ID = " + team.getId() + " has been removed from database.");
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