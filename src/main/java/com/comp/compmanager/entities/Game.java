

package com.comp.compmanager.entities;

import jakarta.persistence.*;

//CREATE TABLE
@Entity
//NAME TABLE
@Table(name = "games")

public class Game {

    //Primary key
    @Id
    //Auto-increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Names column
    @Column(name = "game_id")
    private int id;

    @Column(name = "game_name", length = 50, nullable = false)
    private String name;


    //Game constructor
    public Game(){}
    public Game(String name) {
        this.name = name;
    }


    //GETTERS & SETTERS
    public int getId() {
        return id;
    }
    public void setId(int id) {this.id = id; }

    public String getName() {return name;}
    public void setName(String name) { this.name = name;}

}

