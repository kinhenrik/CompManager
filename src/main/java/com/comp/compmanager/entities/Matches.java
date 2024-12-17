package com.comp.compmanager.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Matches")
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private int matchId;

    @Column(name = "match_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date matchDate;

    @Column(name = "match_type", length = 50, nullable = false)
    private String matchType;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private Teams team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private Teams team2;
//
//    @ManyToOne
//    @JoinColumn(name = "player1_id", nullable = false)
//    private Player player1;
//
//    @ManyToOne
//    @JoinColumn(name = "player2_id", nullable = false)
//    private Player player2;

    @ManyToOne
    @JoinColumn(name = "winner_team", nullable = false)
    private Teams winnerTeam;

//    @ManyToOne
//    @JoinColumn(name = "winner_player", nullable = false)
//    private Player winnerPlayer;

    public Matches() {}
    public Matches(String matchtype){
       this.matchType = matchtype;

    }

    // Getters and Setters

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public Date getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Date matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public Teams getTeam1() {
        return team1;
    }

    public void setTeam1(Teams team1) {
        this.team1 = team1;
    }

    public Teams getTeam2() {
        return team2;
    }

    public void setTeam2(Teams team2) {
        this.team2 = team2;
    }
//
//    public Player getPlayer1() {
//        return player1;
//    }
//
//    public void setPlayer1(Player player1) {
//        this.player1 = player1;
//    }
//
//    public Player getPlayer2() {
//        return player2;
//    }
//
//    public void setPlayer2(Player player2) {
//        this.player2 = player2;
//    }


    public Teams getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Teams winnerTeam) {
        this.winnerTeam = winnerTeam;
    }
//
//    public Player getWinnerPlayer() {
//        return winnerPlayer;
//    }
//
//    public void setWinnerPlayer(Player winnerPlayer) {
//        this.winnerPlayer = winnerPlayer;
//    }

}
