package com.comp.compmanager.entities;

import jakarta.persistence.*;

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

    //foreign Key till spelet laget tävlar inom
//    @ManyToOne
//    @JoinColumn(name = "games_id", nullable = false) // game_id är kolumnen i Teams-tabellen som refererar till Games-tabellen
//    private Games game; // Relationen till Games-objektet i Games/Matches classen


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

//    public Games getGame() {
//        return game;
//    }
//
//    public void setGame(Games game) {
//        this.game = game;
//    }
}