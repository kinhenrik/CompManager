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
    @OneToMany(mappedBy = "team", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Player> players = new ArrayList<>();

    //foreign Key till spelet laget tävlar inom
    @ManyToOne
    @JoinColumn(name = "game_id") // game_id är kolumnen i Games-tabellen som refererar till Games-tabellen
    private Games games; // Team relationen till Games-objektet i Games tabellen


    public Teams() {}
    public Teams(String name, Games games) {
        this.name = name;
        this.games = games;
    }

    public Games getGames() {
        return games;
    }
    public void setGame(Games games) {
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

    @Override
    public String toString() {
        return name;
    }
//    @Override
//    public String toString() {
//        return "Team: " + name + ", Game: " + (games != null ? games.getName() : "None");
//    }

}