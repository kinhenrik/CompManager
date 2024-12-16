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
//    @OneToMany(mappedBy = "winnerPlayer", cascade = CascadeType.ALL)
//    private List<Matches> matchesWonPlayer;


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
//    public List<Matches> getMatchesWonPlayer() {
//        return matchesWonPlayer;
//    }
//
//    public void setMatchesWonPlayer(List<Matches> matchesWonPlayer) {
//        this.matchesWonPlayer = matchesWonPlayer;
//    }

}