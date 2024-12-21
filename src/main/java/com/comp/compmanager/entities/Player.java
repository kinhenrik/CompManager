package com.comp.compmanager.entities;

import jakarta.persistence.*;

import java.util.List;

//CREATE TABLE
@Entity
//NAME TABLE
@Table(name = "players")

public class Player {

    //Primary key
    @Id
    //Auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Names column
    @Column(name = "player_id")
    private int id;

    @Column(name = "player_name", length = 50, nullable = false)
    private String name;
    @Column(name = "player_surname", length = 50, nullable = false)
    private String surname;
    //Col-name, max-length, not null
    @Column(name = "player_nickname", length = 50, nullable = false)
    private String nickname;

    //ForeignKeys
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false) // team_id är kolumnen i Teams-tabellen som refererar till Games-tabellen
    private Teams team; // Relationen till Games-objektet i Games/Matches classen

    //----------FK till player / matches hopefully christoffer-------------

    @OneToMany(mappedBy = "player1", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matches> matchesAsPlayer1;

    @OneToMany(mappedBy = "player2", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matches> matchesAsPlayer2;

    @OneToMany(mappedBy = "winnerPlayer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Matches> matchesWonAsPlayer;

    //Player constructor
    public Player(){}
    public Player(String name, String surname, String nickname, Teams team) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.team = team;
    }

    //GETTERS & SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name;}

    public String getSurname() {return surname;}
    public void setSurname(String surname) { this.surname = surname;}

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) { this.nickname = nickname; }

    public Teams getTeam() {
        return team;
    }
    public void setTeam(Teams team) { this.team = team; }

    //get and set ----matches/player---


    public List<Matches> getMatchesAsPlayer1() {return matchesAsPlayer1;}

    public void setMatchesAsPlayer1(List<Matches> matchesAsPlayer1) {this.matchesAsPlayer1 = matchesAsPlayer1;}

    public List<Matches> getMatchesAsPlayer2() {return matchesAsPlayer2;}

    public void setMatchesAsPlayer2(List<Matches> matchesAsPlayer2) {this.matchesAsPlayer2 = matchesAsPlayer2;}

    public List<Matches> getMatchesWonAsPlayer() {return matchesWonAsPlayer;}

    public void setMatchesWonAsPlayer(List<Matches> matchesWonAsPlayer) {this.matchesWonAsPlayer = matchesWonAsPlayer;}

    @Override
    public String toString() {
        return nickname;
    }
}