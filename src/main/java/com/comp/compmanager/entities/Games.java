package com.comp.compmanager.entities;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "games", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Teams> teams = new ArrayList<>();

    // match date, winner, match id, match type (lag mot lag/spelare mot spelare), player, lag, player_id 1-5. team_id 1-5.
    // här ska alla spel jag la till i mySQL vara.

    //
    //Games constructor
    public Games() {}
    public Games(String name) {
        this.name = name;
    }

////gör klart denna idag. Kolla vad som behövs i DAO-klassen. Gör det imorgon om jag inte hinner idag.

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

    public List<Teams> getTeams() {
        return teams;
    }

    public void setTeams(List<Teams> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return name;
    }

    //    @Override
//    public String toString() {
//        return "Game: " + name + " (ID: " + id + ")";
//    }

}