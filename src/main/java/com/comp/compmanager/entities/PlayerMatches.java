//package com.comp.compmanager.entities;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//@Entity
//@Table(name = "PlayerMatches")
//public class PlayerMatches {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "playerMatch_id")
//    private int playerMatchId;
//
//    @Column(name = "playerMatch_date", nullable = false)
//    @Temporal(TemporalType.DATE)
//    private Date playerMatchDate;
//
//    @Column(name = "playerMatch_type", length = 50, nullable = false)
//    private String playerMatchType;
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
//    @JoinColumn(name = "winner_player", nullable = false)
//    private Player winnerPlayer;
//
//    public PlayerMatches() {}
//    public PlayerMatches(String playerMatchtype) {
//        this.playerMatchType = playerMatchtype;
//    }
//
//    // Getters and Setters
//
//    public int getPlayerMatchId() {
//        return playerMatchId;
//    }
//
//    public void setPlayerMatchId(int playerMatchId) {
//        this.playerMatchId = playerMatchId;
//    }
//
//    public Date getPlayerMatchDate() {
//        return playerMatchDate;
//    }
//
//    public void setPlayerMatchDate(Date playerMatchDate) {
//        this.playerMatchDate = playerMatchDate;
//    }
//
//    public String getPlayerMatchType() {
//        return playerMatchType;
//    }
//
//    public void setPlayerMatchType(String playerMatchType) {
//        this.playerMatchType = playerMatchType;
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
//
//    public Player getWinnerPlayer() {
//        return winnerPlayer;
//    }
//
//    public void setWinnerPlayer(Player winnerPlayer) {
//        this.winnerPlayer = winnerPlayer;
//    }
//}
