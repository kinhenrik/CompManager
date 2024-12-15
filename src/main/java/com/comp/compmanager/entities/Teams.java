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

        //foreign key för att koppla spelare till olika team
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    //foreign Key till spelet laget tävlar inom
//    @ManyToOne
//    @JoinColumn(name = "game_id") // game_id är kolumnen i Games-tabellen som refererar till Games-tabellen
//    private Games games; // Team relationen till Games-objektet i Games tabellen

//    public Games getGames() {
//        return games;
//    }
//    public void setGame(Games games) {
//        this.games = games;
//    }

    public Player getPlayer (){
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
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

}