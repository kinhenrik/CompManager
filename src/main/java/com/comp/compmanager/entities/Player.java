package com.comp.compmanager.entities;

import jakarta.persistence.*;

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

    @Column(name = "player_team", length = 50, nullable = false)
    private String team;

    //Player constructor
    public Player(){}
    public Player(String name, String surname, String nickname, String team) {
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

    public String getTeam() {
        return team;
    }
    public void setTeam(String team) { this.team = team; }


}