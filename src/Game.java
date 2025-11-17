package src;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Game {
    //Properties
    private int appId;
    private String title;
    private String developer;
    private String publisher;
    private double price;
    private double reviewScore;
    private Date releaseDate;
    private double playTime;
    private int achievementsCompleted;
    private int totalAchievements;
    private String gameEngine;

    //Constructor
    public Game(int appId, String title, String developer, String publisher, double price, double reviewScore, Date releaseDate, double playTime, int achievementsCompleted, int totalAchievements, String gameEngine) {
        this.appId = appId;
        this.title = title;
        this.developer = developer;
        this.publisher = publisher;
        this.price = price;
        this.reviewScore = reviewScore;
        this.releaseDate = releaseDate;
        this.playTime = playTime;
        this.achievementsCompleted = achievementsCompleted;
        this.totalAchievements = totalAchievements;
        this.gameEngine = gameEngine;
    }
    public Game(){

    }

    //Getters and Setters
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getReviewScore() {
        return reviewScore;
    }

    public void setReviewScore(double reviewScore) {
        this.reviewScore = reviewScore;
    }

    public java.util.Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPlayTime() {
        return playTime;
    }

    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    public int getAchievementsCompleted() {
        return achievementsCompleted;
    }

    public void setAchievementsCompleted(int achievementsCompleted) {
        this.achievementsCompleted = achievementsCompleted;
    }

    public int getTotalAchievements() {
        return totalAchievements;
    }

    public void setTotalAchievements(int totalAchievements) {
        this.totalAchievements = totalAchievements;
    }

    public String getGameEngine() {
        return gameEngine;
    }

    public void setGameEngine(String gameEngine) {
        this.gameEngine = gameEngine;
    }
    public String displayGameProperties(){
        String log = "";
        log =
            "App ID: " + appId + "\n" +
            "Title: " + title + "\n" +
            "Developer: " + developer + "\n" +
            "Publisher: " + publisher + "\n" +
            "Price: " + formatCurrency(price) + "\n" +
            "Review Score: " + reviewScore + "\n" +
            "Release Date: " + releaseDate + "\n" +
            "Play Time: " + playTime + "\n";
            if (totalAchievements > 0){
                double percent = (((double) 100 /totalAchievements)*achievementsCompleted);
                log += "Achievements: " + achievementsCompleted + "/" + totalAchievements + " - " + formatPercent(percent) + "%\n";
            }
        log +=
            "Game Engine: " + gameEngine;
        return log;
    }
    public String formatCurrency(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyFormatter.format(amount);
    }
    public String formatPercent(double amount){
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(amount);
    }
    public String formatHours(double amount){
        DecimalFormat formatter = new DecimalFormat("#,##0.0");
        return formatter.format(amount);
    }
}
