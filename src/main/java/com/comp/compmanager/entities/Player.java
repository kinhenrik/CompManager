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

    //foreign key för att koppla spelare till olika team .. test // christoffer
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Teams team;

//    @OneToMany(mappedBy = "player1", cascade = CascadeType.ALL)
//    private List<Matches> matchesAsPlayer1;
//
//    @OneToMany(mappedBy = "player2", cascade = CascadeType.ALL)
//    private List<Matches> matchesAsPlayer2;
//
//    @OneToMany(mappedBy = "player3", cascade = CascadeType.ALL)
//    private List<Matches> matchesAsPlayer3;
//
//    @OneToMany(mappedBy = "player4", cascade = CascadeType.ALL)
//    private List<Matches> matchesAsPlayer4;
//
//    @OneToMany(mappedBy = "player5", cascade = CascadeType.ALL)
//    private List<Matches> matchesAsPlayer5;


//    //testar koppla players till olika lag //christoffer
//    @OneToMany(mappedBy = "player", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    private List<Teams> teams = new ArrayList<>();

//    @Column(name = "player_team", length = 50, nullable = false)
//    private String team;

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

    public Teams getTeam (){
        return team;
    }
    public void setTeam(Teams team) {
        this.team = team;
    }

//    public List<Matches> getMatchesAsPlayer1() {
//        return matchesAsPlayer1;
//    }
//
//    public void setMatchesAsPlayer1(List<Matches> matchesAsPlayer1) {
//        this.matchesAsPlayer1 = matchesAsPlayer1;
//    }
//
//    public List<Matches> getMatchesAsPlayer2() {
//        return matchesAsPlayer2;
//    }
//
//    public void setMatchesAsPlayer2(List<Matches> matchesAsPlayer2) {
//        this.matchesAsPlayer2 = matchesAsPlayer2;
//    }
//
//    public List<Matches> getMatchesAsPlayer3() {
//        return matchesAsPlayer3;
//    }
//
//    public void setMatchesAsPlayer3(List<Matches> matchesAsPlayer3) {
//        this.matchesAsPlayer3 = matchesAsPlayer3;
//    }
//
//    public List<Matches> getMatchesAsPlayer4() {
//        return matchesAsPlayer4;
//    }
//
//    public void setMatchesAsPlayer4(List<Matches> matchesAsPlayer4) {
//        this.matchesAsPlayer4 = matchesAsPlayer4;
//    }
//
//    public List<Matches> getMatchesAsPlayer5() {
//        return matchesAsPlayer5;
//    }
//
//    public void setMatchesAsPlayer5(List<Matches> matchesAsPlayer5) {
//        this.matchesAsPlayer5 = matchesAsPlayer5;
//    }
}