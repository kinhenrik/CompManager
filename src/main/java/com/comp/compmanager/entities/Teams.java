package com.comp.compmanager.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Teams")

public class Teams {
//primary key ,AUTO_INCREMENT, Kollumn i tabellen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private int id; // Unikt ID för laget

    // Kollumn i tabellen
    @Column(name = "team_name", nullable = false, length = 100)
    private String name; // Lagets namn

    //testar koppla players till olika lag //christoffer
    @OneToMany(mappedBy = "team", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Player> players = new ArrayList<>();

    //foreign Key till spelet laget tävlar inom
    @ManyToOne
    @JoinColumn(name = "game_id") // game_id är kolumnen i Games-tabellen som refererar till Games-tabellen
    private Games games; // Team relationen till Games-objektet i Games tabellen

    @OneToMany(mappedBy = "team1", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matches> matchesAsTeam1;

    @OneToMany(mappedBy = "team2", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matches> matchesAsTeam2;

    @OneToMany(mappedBy = "winnerTeam", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matches> matchesWonTeam;

    public Teams() {}
    public Teams(String name, Games games) {
        this.name = name;
        this.games = games;
    }

    public Games getGames() {
        return games;
    }
    public void setGames(Games games) {
        this.games = games;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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

    public List<Matches> getMatchesAsTeam1() {
        return matchesAsTeam1;
    }

    public void setMatchesAsTeam1(List<Matches> matchesAsTeam1) {
        this.matchesAsTeam1 = matchesAsTeam1;
    }

    public List<Matches> getMatchesAsTeam2() {
        return matchesAsTeam2;
    }

    public void setMatchesAsTeam2(List<Matches> matchesAsTeam2) {
        this.matchesAsTeam2 = matchesAsTeam2;
    }

    public List<Matches> getMatchesWonTeam() {
        return matchesWonTeam;
    }

    public void setMatchesWonTeam(List<Matches> matchesWonTeam) {
        this.matchesWonTeam = matchesWonTeam;
    }

    @Override
    public String toString() {
        return name;
    }


}