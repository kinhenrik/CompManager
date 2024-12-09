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

    //Col-name, max-length, not null
    @Column(name = "player_name", length = 20, nullable = false)
    private String nickname;

    @Column(name = "player_team", length = 30, nullable = false)
    private String team;

    //constructor
    public Player(){}
    public Player(String nickname, String lag) {
        this.nickname = nickname;
        this.team = lag;
    }

    //getters & setters
    public int getId() {
        return id;
    } public void setId(int id) {
        this.id = id; }

    public String getNickname() {
        return nickname;
    } public void setNickname(String nickname) {
        this.nickname = nickname; }

    public String getTeam() {
        return team;
    }
    public void setTeam(String team) { this.team = team; }
}
