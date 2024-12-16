//package com.comp.compmanager.entities;
//
//import jakarta.persistence.*;
//import java.util.Date;
//
//@Entity
//@Table(name = "Matches")
//public class Matches {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "match_id")
//    private int matchId;
//
//    @Column(name = "match_date", nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date matchDate;
//
//    @Column(name = "match_type", length = 50, nullable = false)
//    @Enumerated(EnumType.STRING)
//    private String matchType;
//
//    @ManyToOne
//    @JoinColumn(name = "team1_id", nullable = false)
//    private Teams team1;
//
//    @ManyToOne
//    @JoinColumn(name = "team2_id", nullable = false)
//    private Teams team2;
//
//    @ManyToOne
//    @JoinColumn(name = "team3_id")
//    private Teams team3;
//
//    @ManyToOne
//    @JoinColumn(name = "team4_id")
//    private Teams team4;
//
//    @ManyToOne
//    @JoinColumn(name = "team5_id")
//    private Teams team5;
//
//    @ManyToOne
//    @JoinColumn(name = "player1_id", nullable = false)
//    private Player player1;
//
//    @ManyToOne
//    @JoinColumn(name = "player2_id", nullable = false)
//    private Player player2;
//
//    @ManyToOne
//    @JoinColumn(name = "player3_id")
//    private Player player3;
//
//    @ManyToOne
//    @JoinColumn(name = "player4_id")
//    private Player player4;
//
//    @ManyToOne
//    @JoinColumn(name = "player5_id")
//    private Player player5;
//
//    @ManyToOne
//    @JoinColumn(name = "winner_id", nullable = false)
//    private Teams winner;
//
//    public Matches() {}
//    public Matches(String matchtype){
//       this.matchType = matchtype;
//
//
//    }
//
//    // Getters and Setters
//
//    public int getMatchId() {
//        return matchId;
//    }
//
//    public void setMatchId(int matchId) {
//        this.matchId = matchId;
//    }
//
//    public Date getMatchDate() {
//        return matchDate;
//    }
//
//    public void setMatchDate(Date matchDate) {
//        this.matchDate = matchDate;
//    }
//
//    public String getMatchType() {
//        return matchType;
//    }
//
//    public void setMatchType(String matchType) {
//        this.matchType = matchType;
//    }
//
//    public Teams getTeam1() {
//        return team1;
//    }
//
//    public void setTeam1(Teams team1) {
//        this.team1 = team1;
//    }
//
//    public Teams getTeam2() {
//        return team2;
//    }
//
//    public void setTeam2(Teams team2) {
//        this.team2 = team2;
//    }
//
//    public Teams getTeam3() {
//        return team3;
//    }
//
//    public void setTeam3(Teams team3) {
//        this.team3 = team3;
//    }
//
//    public Teams getTeam4() {
//        return team4;
//    }
//
//    public void setTeam4(Teams team4) {
//        this.team4 = team4;
//    }
//
//    public Teams getTeam5() {
//        return team5;
//    }
//
//    public void setTeam5(Teams team5) {
//        this.team5 = team5;
//    }
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
//
//    public Player getPlayer3() {
//        return player3;
//    }
//
//    public void setPlayer3(Player player3) {
//        this.player3 = player3;
//    }
//
//    public Player getPlayer4() {
//        return player4;
//    }
//
//    public void setPlayer4(Player player4) {
//        this.player4 = player4;
//    }
//
//    public Player getPlayer5() {
//        return player5;
//    }
//
//    public void setPlayer5(Player player5) {
//        this.player5 = player5;
//    }
//
//    public Teams getWinner() {
//        return winner;
//    }
//
//    public void setWinner(Teams winner) {
//        this.winner = winner;
//    }
//
//}
