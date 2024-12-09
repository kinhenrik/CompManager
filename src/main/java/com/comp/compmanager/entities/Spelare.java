package com.comp.compmanager.entities;

import jakarta.persistence.*;

//Skapar tabell
@Entity
//Byter namn på tabellen
@Table(name = "players")
public class Spelare {

    //Primary key
    @Id
    //Auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Väljer namn på kolumn
    @Column(name = "player_id")
    private int id;

    //Namn på kolumn, max-längd, kan inte lämnas tom
    @Column(name = "player_name", length = 20, nullable = false)
    private String nickname;

    @Column(name = "player_team", length = 30, nullable = false)
    private String lag;

    //Kontruktor
    public Spelare(){}
    public Spelare(String nickname, String lag) {
        this.nickname = nickname;
        this.lag = lag;
    }

    //Getters & Setters
    public int getId() {
        return id;
    } public void setId(int id) {
        this.id = id; }

    public String getNickname() {
        return nickname;
    } public void setNickname(String nickname) {
        this.nickname = nickname; }

    public String getLag() {
        return lag;
    } public void setLag(String lag) {
        this.lag = lag; }

}
