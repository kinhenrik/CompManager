package com.comp.compmanager.DAO;

import com.comp.compmanager.entities.Games;
import com.comp.compmanager.entities.Matches;
import com.comp.compmanager.entities.Teams;
import jakarta.persistence.*;

import java.util.List;

public class MatchesDAO {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("myconfig");

    // CREATE - Lägg till en match
//    public void addMatch(Matches match) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            transaction = manager.getTransaction();
//            transaction.begin();
//            manager.persist(match);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//
//        } finally {
//            manager.close();
//        }
//    }
    public void addMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            // Kontrollera att 'game' är korrekt instansierad (och inte null)
            if (match.getGame() == null) {
                throw new IllegalArgumentException("The game for the match cannot be null.");
            }
            // Merge the 'game' entity if not already managed
            if (!manager.contains(match.getGame())) {
                match.setGame(manager.merge(match.getGame())); // Om game är transient eller inte finns i sessionen, merge den
            }

            // Hantera lag
            if (match.getTeam1() != null && !manager.contains(match.getTeam1())) {
                match.setTeam1(manager.merge(match.getTeam1()));  // Merge team1 om det inte redan finns
            }
            if (match.getTeam2() != null && !manager.contains(match.getTeam2())) {
                match.setTeam2(manager.merge(match.getTeam2()));  // Merge team2 om det inte redan finns
            }
            if (match.getWinnerTeam() != null && !manager.contains(match.getWinnerTeam())) {
                match.setWinnerTeam(manager.merge(match.getWinnerTeam()));  // Merge winnerTeam om det inte redan finns
            }

            // Hantera spelare
            if (match.getPlayer1() != null && !manager.contains(match.getPlayer1())) {
                match.setPlayer1(manager.merge(match.getPlayer1()));  // Merge player1 om det inte redan finns
            }
            if (match.getPlayer2() != null && !manager.contains(match.getPlayer2())) {
                match.setPlayer2(manager.merge(match.getPlayer2()));  // Merge player2 om det inte redan finns
            }
            if (match.getWinnerPlayer() != null && !manager.contains(match.getWinnerPlayer())) {
                match.setWinnerPlayer(manager.merge(match.getWinnerPlayer()));  // Merge winnerPlayer om det inte redan finns
            }

            // Persistera matchen
            manager.persist(match);
            transaction.commit();
            System.out.println("Match with ID=" + match.getMatchId() + " has been added to the database.");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            manager.close();
        }
    }
//    public void addMatch(Matches match) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            transaction = manager.getTransaction();
//            transaction.begin();
//
//            // Hantera relationer för lag och spelare
//            if (match.getTeam1() != null && !manager.contains(match.getTeam1())) {
//                match.setTeam1(manager.merge(match.getTeam1()));  // Endast merge om objektet inte redan finns i sessionen
//            }
//            if (match.getTeam2() != null && !manager.contains(match.getTeam2())) {
//                match.setTeam2(manager.merge(match.getTeam2()));  // Endast merge om objektet inte redan finns i sessionen
//            }
//            if (match.getWinnerTeam() != null && !manager.contains(match.getWinnerTeam())) {
//                match.setWinnerTeam(manager.merge(match.getWinnerTeam()));  // Endast merge om objektet inte redan finns i sessionen
//            }
//
//            if (match.getPlayer1() != null && !manager.contains(match.getPlayer1())) {
//                match.setPlayer1(manager.merge(match.getPlayer1()));  // Endast merge om objektet inte redan finns i sessionen
//            }
//            if (match.getPlayer2() != null && !manager.contains(match.getPlayer2())) {
//                match.setPlayer2(manager.merge(match.getPlayer2()));  // Endast merge om objektet inte redan finns i sessionen
//            }
//            if (match.getWinnerPlayer() != null && !manager.contains(match.getWinnerPlayer())) {
//                match.setWinnerPlayer(manager.merge(match.getWinnerPlayer()));  // Endast merge om objektet inte redan finns i sessionen
//            }
//
//            // Persist the new match
//            manager.persist(match);
//            transaction.commit();
//            System.out.println("Match with ID=" + match.getMatchId() + " has been added to the database.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//        } finally {
//            manager.close();
//        }
//    }

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

//    // DELETE - Ta bort en match
//    public void deleteMatch(Matches match) {
//        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        EntityTransaction transaction = null;
//
//        try {
//            transaction = manager.getTransaction();
//            transaction.begin();
//            if (!manager.contains(match)) {
//                match = manager.merge(match);
//            }
//            manager.remove(match);
//            transaction.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (transaction != null && transaction.isActive()) {
//                transaction.rollback();
//            }
//        } finally {
//            manager.close();
//        }
//    }

    public void deleteMatch(Matches match) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            // Ta bort relaterade objekt som har foreign keys till matchen.

            // Om spelare är kopplade till matchen
            if (match.getPlayer1() != null) {
                match.getPlayer1().getMatchesAsPlayer1().remove(match);  // Ta bort matchen från spelarens lista
                manager.merge(match.getPlayer1());  // Uppdatera spelaren
            }

            if (match.getPlayer2() != null) {
                match.getPlayer2().getMatchesAsPlayer2().remove(match);  // Ta bort matchen från spelarens lista
                manager.merge(match.getPlayer2());  // Uppdatera spelaren
            }

            // Om det finns lag kopplade till matchen, ta bort referenser till matchen från lagen.
            if (match.getTeam1() != null) {
                match.getTeam1().getMatchesAsTeam1().remove(match);  // Ta bort matchen från lagets lista
                manager.merge(match.getTeam1());  // Uppdatera laget
            }

            if (match.getTeam2() != null) {
                match.getTeam2().getMatchesAsTeam2().remove(match);  // Ta bort matchen från lagets lista
                manager.merge(match.getTeam2());  // Uppdatera laget
            }

            if (match.getWinnerTeam() != null) {
                match.getWinnerTeam().getMatchesWonTeam().remove(match);  // Ta bort matchen från lagets lista
                manager.merge(match.getWinnerTeam());  // Uppdatera laget
            }
            if (match.getWinnerPlayer() != null) {
                match.getWinnerPlayer().getMatchesWonAsPlayer().remove(match);  // Ta bort matchen från lagets lista
                manager.merge(match.getWinnerTeam());  // Uppdatera laget
            }

            // Ta bort själva matchen
            if (!manager.contains(match)) {
                match = manager.merge(match);
            }

            manager.remove(match);
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
