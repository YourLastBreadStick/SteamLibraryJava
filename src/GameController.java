package src;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.sql.Date;
import java.sql.SQLException;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    public ComboBox cboFormAction;
    public TextField txtTitle;
    public TextField txtAppId;
    public TextField txtDeveloper;
    public TextField txtPublisher;
    public TextField txtPrice;
    public TextField txtReviewScore;
    public DatePicker dteReleaseData;
    public TextField txtPlayTime;
    public TextField txtAchievementsCompleted;
    public TextField txtTotalAchievements;
    public TextField txtGameEngine;
    public Button btnConfirm;
    public Button btnCancel;
    public TextField txtAppIdInput;
    public Button btnPull;

    public void initializeForm(){
        cboFormAction.setItems(FXCollections.observableArrayList("Insert","Modify","Delete"));
        cboFormAction.getSelectionModel().select(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeForm();
    }
    @FXML
    private void exitApplication(){
        Platform.exit();
    }
    @FXML
    private void grabInfo() throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        GameDAO dao = new GameDAO();
        int appId = 0;
        if (Validator.isInt(txtAppIdInput.getText().trim()) == true){
            appId = Integer.parseInt(txtAppIdInput.getText().trim());
        }
        Game foundGame = dao.findGameByAppId(appId);
        txtAppId.setText(Integer.toString(foundGame.getAppId()));
        txtTitle.setText(foundGame.getTitle());
        txtDeveloper.setText(foundGame.getDeveloper());
        txtPublisher.setText(foundGame.getPublisher());
        txtPrice.setText(Double.toString(foundGame.getPrice()));
        txtReviewScore.setText(Double.toString(foundGame.getReviewScore()));
        LocalDate localDate = foundGame.getReleaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        dteReleaseData.setValue(localDate);
        txtPlayTime.setText(Double.toString(foundGame.getPlayTime()));
        txtAchievementsCompleted.setText(Integer.toString(foundGame.getAchievementsCompleted()));
        txtTotalAchievements.setText(Integer.toString(foundGame.getTotalAchievements()));
        txtGameEngine.setText(foundGame.getGameEngine());
    }
    @FXML
    private void confirm() throws SQLException {
        // Default values
        int appId = 0;
        String title = "";
        String developer = "";
        String publisher = "";
        double price = 0.0;
        double reviewScore = 0.0;
        Date releaseDate = null;
        double playTime = 0.0;
        int achievementsCompleted = 0;
        int totalAchievements = 0;
        String gameEngine = "";
        GameDAO dao = new GameDAO();
        if (cboFormAction.getSelectionModel().getSelectedIndex() == 0) {
            // Parse and validate inputs
            String appIdText = txtAppId.getText().trim();
            if (Validator.isInt(appIdText)) {
                appId = Integer.parseInt(appIdText);
            } else {
                showAlert("App ID must be an integer!");
                return;
            }

            title = txtTitle.getText().trim();
            developer = txtDeveloper.getText().trim();
            publisher = txtPublisher.getText().trim();

            String priceText = txtPrice.getText().trim();
            if (Validator.isDouble(priceText)) {
                price = Double.parseDouble(priceText);
            }

            String reviewScoreText = txtReviewScore.getText().trim();
            if (Validator.isDouble(reviewScoreText)) {
                reviewScore = Double.parseDouble(reviewScoreText);
            }

            if (dteReleaseData.getValue() != null) {
                releaseDate = Date.valueOf(dteReleaseData.getValue());
            } else {
                showAlert("Please select a release date!");
                return;
            }

            String playTimeText = txtPlayTime.getText().trim();
            if (Validator.isDouble(playTimeText)) {
                playTime = Double.parseDouble(playTimeText);
            }

            String achievementsCompletedText = txtAchievementsCompleted.getText().trim();
            if (Validator.isInt(achievementsCompletedText)) {
                achievementsCompleted = Integer.parseInt(achievementsCompletedText);
            }

            String totalAchievementsText = txtTotalAchievements.getText().trim();
            if (Validator.isInt(totalAchievementsText)) {
                totalAchievements = Integer.parseInt(totalAchievementsText);
            }

            gameEngine = txtGameEngine.getText().trim();

            // Create Game object
            Game newGame = new Game(appId, title, developer, publisher, price, reviewScore,
                    releaseDate, playTime, achievementsCompleted, totalAchievements, gameEngine);

            // Insert into database
            try {
                dao.insert(newGame);
                showAlert("Game added successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error inserting game into database: " + ex.getMessage());
            }
        }
        if (cboFormAction.getSelectionModel().getSelectedIndex() == 1){
            // Parse and validate inputs
            String appIdText = txtAppId.getText().trim();
            if (Validator.isInt(appIdText)) {
                appId = Integer.parseInt(appIdText);
            } else {
                showAlert("App ID must be an integer!");
                return;
            }

            title = txtTitle.getText().trim();
            developer = txtDeveloper.getText().trim();
            publisher = txtPublisher.getText().trim();

            String priceText = txtPrice.getText().trim();
            if (Validator.isDouble(priceText)) {
                price = Double.parseDouble(priceText);
            }

            String reviewScoreText = txtReviewScore.getText().trim();
            if (Validator.isDouble(reviewScoreText)) {
                reviewScore = Double.parseDouble(reviewScoreText);
            }

            if (dteReleaseData.getValue() != null) {
                releaseDate = Date.valueOf(dteReleaseData.getValue());
            } else {
                showAlert("Please select a release date!");
                return;
            }

            String playTimeText = txtPlayTime.getText().trim();
            if (Validator.isDouble(playTimeText)) {
                playTime = Double.parseDouble(playTimeText);
            }

            String achievementsCompletedText = txtAchievementsCompleted.getText().trim();
            if (Validator.isInt(achievementsCompletedText)) {
                achievementsCompleted = Integer.parseInt(achievementsCompletedText);
            }

            String totalAchievementsText = txtTotalAchievements.getText().trim();
            if (Validator.isInt(totalAchievementsText)) {
                totalAchievements = Integer.parseInt(totalAchievementsText);
            }

            gameEngine = txtGameEngine.getText().trim();

            // Create Game object
            Game newGame = new Game(appId, title, developer, publisher, price, reviewScore,
                    releaseDate, playTime, achievementsCompleted, totalAchievements, gameEngine);

            // Insert into database
            try {
                dao.update(newGame);
                showAlert("Game updated successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                showAlert("Error updating game into database: " + ex.getMessage());
            }
        }
        if (cboFormAction.getSelectionModel().getSelectedIndex() == 2){
            String appIdText = txtAppId.getText().trim();
            if (Validator.isInt(appIdText)) {
                appId = Integer.parseInt(appIdText);
            } else {
                showAlert("App ID must be an integer!");
                return;
            }
            int IndexId = dao.getIndexIdByAppId(appId);
            dao.delete(IndexId);
        }
    }

    // Utility method to show simple alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
