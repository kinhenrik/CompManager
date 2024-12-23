package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Games;
import com.comp.compmanager.entities.Matches;
import jakarta.persistence.*;

import java.util.List;

//ALLAS KLASS

public class MatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    public void addMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            // Tilldela spelet baserat på lag eller spelare
            if (match.getMatchType().equals("T vs T")) {
                if (match.getTeam1() != null && match.getTeam2() != null) {
                    if (match.getTeam1().getGames().getId() == match.getTeam2().getGames().getId()) {
                        match.setGame(match.getTeam1().getGames());
                    } else {
                        throw new IllegalArgumentException("Error! Teams must play the same game.");
                    }
                }
            } else if (match.getMatchType().equals("P vs P")) {
                if (match.getPlayer1() != null && match.getPlayer2() != null) {
                    Games game1 = match.getPlayer1().getTeam().getGames();
                    Games game2 = match.getPlayer2().getTeam().getGames();

                    if (game1.getId() == game2.getId()) {
                        match.setGame(game1);
                    } else {
                        throw new IllegalArgumentException("Error! Players must play the same game.");
                    }
                }
            }

            // Spara matchen
            manager.persist(match);
            transaction.commit();
            System.out.println("Match added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            manager.close();
        }
    }

    // READ - Hämta alla matcher
    public static List<Matches> getAllMatches() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return manager.createQuery("SELECT m FROM Matches m JOIN FETCH m.game", Matches.class).getResultList();
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

            // Kontrollera att spelet är satt
            if (match.getGame() == null) {
                throw new IllegalArgumentException("Game is required for the match.");
            }

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

    public void deleteMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            Matches managedMatch = manager.find(Matches.class, match.getMatchId());

            // Om spelare är kopplade till matchen
            if (managedMatch.getPlayer1() != null) {
                managedMatch.getPlayer1().getMatchesAsPlayer1().remove(managedMatch);  // Ta bort matchen från spelarens lista
            }

            if (managedMatch.getPlayer2() != null) {
                managedMatch.getPlayer2().getMatchesAsPlayer2().remove(managedMatch);  // Ta bort matchen från spelarens lista
            }

            // Om det finns lag kopplade till matchen, ta bort referenser till matchen från lagen.
            if (managedMatch.getTeam1() != null) {
                managedMatch.getTeam1().getMatchesAsTeam1().remove(managedMatch);  // Ta bort matchen från lagets lista
            }

            if (managedMatch.getTeam2() != null) {
                managedMatch.getTeam2().getMatchesAsTeam2().remove(managedMatch);  // Ta bort matchen från lagets lista
            }

            if (managedMatch.getWinnerTeam() != null) {
                managedMatch.getWinnerTeam().getMatchesWonTeam().remove(managedMatch);  // Ta bort matchen från lagets lista
            }
            if (managedMatch.getWinnerPlayer() != null) {
                managedMatch.getWinnerPlayer().getMatchesWonAsPlayer().remove(managedMatch);  // Ta bort matchen från lagets lista
            }

            managedMatch.setTeam1(null);
            managedMatch.setTeam2(null);
            managedMatch.setWinnerTeam(null);
            managedMatch.setPlayer1(null);
            managedMatch.setPlayer2(null);
            managedMatch.setWinnerPlayer(null);
            managedMatch.setGame(null);

            manager.remove(managedMatch);
            transaction.commit();
            System.out.println("Match with ID=" + match.getMatchId() + " has been removed from the database.");
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
