package src.sql;

import src.Game;
import src.GameDAO;
import src.Gift;
import src.GiftDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameMain {
    public static void main(String[] args) throws SQLException {
        GameDAO dao = new GameDAO();
        List<Game> games = dao.findAll();
        totalValue();
        totalHours();
        mostPlayed();
        averageHours();
        mixMaxRatings();
        displayAllGames();
    }
    private static void mostPlayed() throws SQLException {
        Game game = new Game();
        GameDAO dao = new GameDAO();
        List<Game> allGames = dao.findAll();
        double hours = 0;
        String maxName = "";
        if (!allGames.isEmpty()){
            for (Game g : allGames){
                if (g.getPlayTime() >= hours){
                    hours = g.getPlayTime();
                    maxName = g.getTitle();
                }
            }
            System.out.println("Most hours in one game: " + game.formatHours(hours) + "(" + maxName + ")");
        }
    }
    private static void mixMaxRatings() throws SQLException {
        Game game = new Game();
        GameDAO dao = new GameDAO();
        List<Game> allGames = dao.findAll();
        double low = 100;
        String lowName = "";
        double max = 0;
        String maxName = "";
        if (!allGames.isEmpty()){
            for (Game g : allGames){
                if (g.getReviewScore() >= max){
                    max = g.getReviewScore();
                    maxName = g.getTitle();
                }
                if (g.getReviewScore() <= low){
                    low = g.getReviewScore();
                    lowName = g.getTitle();
                }
            }
            System.out.println("Lowest review score: " + low + "%(" + lowName + ")" );
            System.out.println("Highest review score: " + max + "%(" + maxName + ")");
        }
    }
    private static void averageHours() throws SQLException {
        Game game = new Game();
        GameDAO dao = new GameDAO();
        List<Game> allGames = dao.findAll();
        double average = 0;
        double games = 0;
        double time = 0;
        if (!allGames.isEmpty()){
            for (Game g : allGames){
                time += g.getPlayTime();
                games++;
            }
            average = time/games;
            System.out.println("Average hour played per game: " + game.formatHours(average));
        }
    }
    private static void totalHours() throws SQLException {
        Game game = new Game();
        GameDAO dao = new GameDAO();
        List<Game> allGames = dao.findAll();
        double time = 0;
        if (!allGames.isEmpty()){
            for (Game g : allGames){
                time += g.getPlayTime();
            }
            System.out.println("Total hours played: " + game.formatHours(time));
        }
    }
    private static void totalValue() throws SQLException {
        Game game = new Game();
        GameDAO dao = new GameDAO();
        List<Game> allGames = dao.findAll();
        double worth = 0;
        if (!allGames.isEmpty()){
            for (Game g : allGames){
               worth += g.getPrice();
            }
            System.out.println("Library games worth: " + game.formatCurrency(worth));
        }
    }
    private static void displayAllGames() throws SQLException {
        GameDAO dao = new GameDAO();
        List<Game> allGames = dao.findAll();
        if (!allGames.isEmpty()){
            for (Game g : allGames)
                System.out.println("-----\n" + g.displayGameProperties());
        } else
            System.out.println("\nThere are no games in the Library");
    }
}
