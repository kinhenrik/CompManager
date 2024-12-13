package com.comp.compmanager.entities;
import jakarta.persistence.*;
import java.sql.Date;

// CREATE TABLE //
@Entity//NAME TABLE
@Table(name = "Games")
public class Games {


    //@ManyToOne (ForeignKey)
    //@JoinColumn
    // @JoinColumn(name = "games_id", nullable = false) ersätt games_id med det jag behöver koppla till.
    //Winner, Teams,


    //Primary key
    @Id
//Auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//Names column
    @Column(name = "game_id")
    private int id;                //detta gör att den blir primary key.

    @Column(name = "game_name", length = 50, nullable = false)
    private String name;
//    @Column(name = "match_date", length = 50, nullable = false)
//    private Date matchdate;
//
//    @ManyToOne
//    @JoinColumn(name = "team1_id", nullable = false)
//    private Teams team1;
//    @ManyToOne
//    @JoinColumn(name = "team2_id", nullable = false)
//    private Teams team2;
//    @ManyToOne
//    @JoinColumn(name = "team3_id", nullable = false)
//    private Teams team3;
//    @ManyToOne
//    @JoinColumn(name = "team4_id", nullable = false)
//    private Teams team4;
//    @ManyToOne
//    @JoinColumn(name = "team5_id", nullable = false)
//    private Teams team5;
//
//
//    @ManyToOne
//    @JoinColumn(name = "player1_id", nullable = false)
//    private Player player1;
//    @ManyToOne
//    @JoinColumn(name = "player2_id", nullable = false)
//    private Player player2;
//    @ManyToOne
//    @JoinColumn(name = "player3_id", nullable = false)
//    private Teams player3;
//    @ManyToOne
//    @JoinColumn(name = "player4_id", nullable = false)
//    private Teams player4;
//    @ManyToOne
//    @JoinColumn(name = "player5_id", nullable = false)
//    private Teams player5;
//
//
//    @ManyToOne
//    @JoinColumn(name = "winner_id", nullable = false)
//    private Teams winner;






    // match date, winner, match id, match type (lag mot lag/spelare mot spelare), player, lag, player_id 1-5. team_id 1-5.


//
//    //Games constructor
//    public Games() {
//    }
//
//    public Games(String name, String team) {
//        this.name = name;
//        this.team = team;
//    }
//
//
////gör klart denna idag. Kolla vad som behövs i DAO-klassen. Gör det imorgon om jag inte hinner idag.
//


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGames() {
        return getGames();
    }
}

// här ska alla spel jag la till i mySQL vara.