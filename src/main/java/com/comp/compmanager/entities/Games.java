package com.comp.compmanager.entities;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

//SIMON WESTERLUNDS KLASS


// CREATE TABLE //
@Entity //NAME TABLE
@Table(name = "Games")
public class Games {

    //Primary key
    @Id
    //Auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Names column
    @Column(name = "game_id")
    private int id;

    @Column(name = "game_name", length = 50, nullable = false)
    private String name;

    @OneToMany(mappedBy = "games", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Teams> teams = new ArrayList<>();

    // relation till Matches
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Matches> matches = new ArrayList<>();

    // match date, winner, match id, match type (lag mot lag/spelare mot spelare), player, lag, player_id 1-5. team_id 1-5.

    //Games constructor
    public Games() {}
    public Games(String name) {
        this.name = name;
    }

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

    public List<Matches> getMatches() {return matches;}

    public void setMatches(List<Matches> matches) {this.matches = matches;}

    @Override
    public String toString() {
        return name;
    }
}
