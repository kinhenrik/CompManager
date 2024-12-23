package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Matches;
import com.comp.compmanager.entities.Teams;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

//CHRISTOFFER MORALES KLASS


public class TeamManagerDAO {

    // Hämta konfigurationen från persistence.xml
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // CREATE - Lägg till ett lag
    public static boolean addTeam(Teams team) {
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
    public static Teams getTeamByID(int team_id) {
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
    public static void updateTeam(Teams teamToUpdate) {
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

    public static void deleteTeam(Teams team) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Teams managedTeam = manager.find(Teams.class, team.getId());

            if (managedTeam != null) {
                for (Matches match : managedTeam.getMatchesAsTeam1()) {
                    match.setTeam1(null);
                    manager.merge(match);
                }

                for (Matches match : managedTeam.getMatchesAsTeam2()) {
                    match.setTeam2(null);
                    manager.merge(match);
                }

                for (Matches match : managedTeam.getMatchesWonTeam()) {
                    match.setWinnerTeam(null);
                    manager.merge(match);
                }

                managedTeam.setMatchesAsTeam1(null);
                managedTeam.setMatchesAsTeam2(null);
                managedTeam.setMatchesWonTeam(null);

                managedTeam.getPlayers().clear();
                managedTeam.setGames(null);
            }
            manager.remove(managedTeam);
            transaction.commit();
            System.out.println("Team with ID = " + team.getId() + " has been removed from database.");        } catch (Exception e) {
            System.err.println("Error deleting team: " + e.getMessage());
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }
}