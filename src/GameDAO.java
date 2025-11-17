package src;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class GameDAO {
    public static final String TABLE = "dbo.Games";
    public int insert(Game g) throws SQLException {
        final String sql = "INSERT INTO " + TABLE +
                " (AppId, Title, Developer, Publisher, Price, ReviewScore, ReleaseDate, PlayTime, AchievementsCompleted, TotalAchievements, GameEngine) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);" +
                " SELECT SCOPE_IDENTITY();";
        try (Connection c = GameDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, g.getAppId());
            ps.setString(2, g.getTitle());
            ps.setString(3, g.getDeveloper());
            ps.setString(4, g.getPublisher());
            ps.setDouble(5, g.getPrice());
            ps.setDouble(6, g.getReviewScore());
            java.util.Date utilDate = g.getReleaseDate();
            java.sql.Date sqlDate = utilDate != null ? new java.sql.Date(utilDate.getTime()) : null;
            ps.setDate(7, sqlDate);
            ps.setDouble(8, g.getPlayTime());
            ps.setInt(9, g.getAchievementsCompleted());
            ps.setInt(10, g.getTotalAchievements());
            ps.setString(11, g.getGameEngine());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ((Number) rs.getObject(1)).intValue();
                }
            }
        }
        return -1;
    }
    public Game findGameByAppId(int id) throws SQLException {
        final String sql = "SELECT * FROM " + TABLE + " WHERE AppId = ?";
        try (Connection c = GameDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }
    public int getIndexIdByAppId(int appId) throws SQLException {
        final String sql = "SELECT IndexId FROM " + TABLE + " WHERE AppId = ?";

        try (Connection c = GameDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, appId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IndexId");
                }
            }
        }
        return -1; // Not found
    }


    public List<Game> findAll() throws SQLException {
        final String sql = "SELECT * FROM " + TABLE + " ORDER BY Title DESC, AppId";
        List<Game> list = new ArrayList<>();
        try (Connection c = GameDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public int update(Game g) throws SQLException {
        int indexId = getIndexIdByAppId(g.getAppId());
        if (indexId == -1) {
            throw new SQLException("Game with AppId " + g.getAppId() + " not found.");
        }

        final String sql = "UPDATE " + TABLE +
                " SET Title=?, Developer=?, Publisher=?, Price=?, ReviewScore=?, ReleaseDate=?, " +
                "PlayTime=?, AchievementsCompleted=?, TotalAchievements=?, GameEngine=? " +
                "WHERE IndexId=?";

        try (Connection c = GameDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, g.getTitle());
            ps.setString(2, g.getDeveloper());
            ps.setString(3, g.getPublisher());
            ps.setDouble(4, g.getPrice());
            ps.setDouble(5, g.getReviewScore());

            java.util.Date utilDate = g.getReleaseDate();
            java.sql.Date sqlDate = utilDate != null ? new java.sql.Date(utilDate.getTime()) : null;
            ps.setDate(6, sqlDate);

            ps.setDouble(7, g.getPlayTime());
            ps.setInt(8, g.getAchievementsCompleted());
            ps.setInt(9, g.getTotalAchievements());
            ps.setString(10, g.getGameEngine());

            ps.setInt(11, indexId); // Use the IndexId we just looked up

            return ps.executeUpdate(); // number of rows affected
        }
    }


    public boolean delete(int id) throws SQLException {
        // Ask user for confirmation
        Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Deletion");
        confirmAlert.setHeaderText("Are you sure you want to delete this entry?");
        confirmAlert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isEmpty() || result.get() != ButtonType.OK) {
            // User cancelled
            return false;
        }

        // Proceed with deletion
        final String sql = "DELETE FROM " + TABLE + " WHERE IndexId = ?";
        try (Connection c = GameDB.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();

            // Inform the user of the result
            Alert infoAlert = new Alert(AlertType.INFORMATION);
            infoAlert.setTitle("Deletion Result");
            if (affectedRows == 1) {
                infoAlert.setHeaderText("Deletion Successful");
                infoAlert.setContentText("The entry was successfully deleted.");
            } else {
                infoAlert.setAlertType(AlertType.WARNING);
                infoAlert.setHeaderText("Deletion Failed");
                infoAlert.setContentText("No entry was deleted. It may not exist.");
            }
            infoAlert.showAndWait();

            return affectedRows == 1;
        }
    }

    private Game map(ResultSet rs) throws SQLException {
        Game g = new Game();
        g.setAppId(rs.getInt("AppId"));
        g.setTitle(rs.getString("Title"));
        g.setDeveloper(rs.getString("Developer"));
        g.setPublisher(rs.getString("Publisher"));
        g.setPrice(rs.getBigDecimal("Price").doubleValue());
        g.setReviewScore(rs.getBigDecimal("ReviewScore").doubleValue());
        Timestamp ts = rs.getTimestamp("ReleaseDate");
        g.setReleaseDate(ts == null ? null : new Date(ts.getTime()));
        g.setPlayTime(rs.getBigDecimal("PlayTime").doubleValue());
        g.setAchievementsCompleted(rs.getInt("AchievementsCompleted"));
        g.setTotalAchievements(rs.getInt("TotalAchievements"));
        g.setGameEngine(rs.getString("GameEngine"));
        // If you want the DB's identity value back on the entity:
        // (add a giftId field + getter/setter to Gift and set it here)
        // g.setGiftId(rs.getInt("GiftId"));
        return g;
    }
}
