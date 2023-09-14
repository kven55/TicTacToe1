package com.example.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TicTacToe extends Application {

    private Label playerXScoreLabel , playerYScoreLabel;

    private boolean playerXTurn = true;

    private Button buttons[][] = new Button[3][3];
    
    private int playerXScore = 0, playerYScore = 0;

    private int noOfTimesButtonClicked = 0; //for checking tie

    private BorderPane createPane() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color : black");
        //UI PARTS

        //TITLE
        Label titleLabel = new Label("TIC TAC TOE");
        BorderPane.setAlignment(titleLabel , Pos.CENTER);
        titleLabel.setStyle("-fx-text-fill : whitesmoke; -fx-font-size : 25pt ; -fx-font-weight : bold;");
        root.setTop(titleLabel);



        //BOARD
        GridPane gridPane = new GridPane(); // IN GRID PANE (I,J) REFERS TO (COL , ROW)
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button();
                button.setPrefSize(100 , 100);
                button.setStyle("-fx-background-color : white;");
                button.setOnAction(event->buttonClicked(button));
                buttons[i][j] = button; //DO CHECK ITS ROLE AT END
                gridPane.add(button , j , i);
            }
        }
        root.setCenter(gridPane);

        //SCORE
        playerXScoreLabel = new Label("Player X : 0");
        playerXScoreLabel.setStyle("-fx-font-size : 15pt ; -fx-text-fill : grey; -fx-font-weight : bold;");
        playerYScoreLabel = new Label("Player Y : 0");
        playerYScoreLabel.setStyle("-fx-font-size : 15pt ; -fx-text-fill : maroon; -fx-font-weight : bold;");
        HBox scoreBoard = new HBox(25);
        scoreBoard.setAlignment(Pos.CENTER);
        scoreBoard.getChildren().addAll(playerXScoreLabel , playerYScoreLabel);
        root.setBottom(scoreBoard);

        return root;
    }
    private void buttonClicked(Button button)
    {
        if(button.getText().equals(""))
        {
            noOfTimesButtonClicked++;
            if(playerXTurn)
            {
                button.setStyle("-fx-text-fill : white; -fx-background-color : grey; -fx-font-size : 20pt ; -fx-font-weight : bold;");
                button.setText("X");
            }
            else
            {
                button.setStyle("-fx-text-fill : white; -fx-background-color : maroon; -fx-font-size : 20pt ; -fx-font-weight : bold;");
                button.setText("O");

            }
            playerXTurn = !playerXTurn;
            isWinner();
        }

        return; //IF BUTTON IS ALREADY CLICKED
    }
    private void isWinner()
    {
        //same row elements
        for (int row = 0; row < 3; row++) {
            if(buttons[row][0].getText().equals(buttons[row][1].getText())
                    && buttons[row][1].getText().equals(buttons[row][2].getText())
                    && !buttons[row][0].getText().isEmpty())
            {
                String winner = buttons[row][0].getText();
                showWinner(winner);
                updateScore((winner));
                resetBoard();
                noOfTimesButtonClicked = 0;
                return;
            }
        }

        //same col elements
        for (int col = 0; col < 3; col++) {
            if(buttons[0][col].getText().equals(buttons[1][col].getText())
                    && buttons[1][col].getText().equals(buttons[2][col].getText())
                    && !buttons[0][col].getText().isEmpty())
            {
                String winner = buttons[0][col].getText();
                showWinner(winner);
                updateScore(winner);
                resetBoard();
                noOfTimesButtonClicked = 0;
                return;
            }
        }

        //same diagonal elements

        //left->right diagonal
        if(buttons[0][0].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][2].getText())
                && !buttons[0][0].getText().isEmpty())
        {
            String winner = buttons[0][0].getText();
            showWinner(winner);
            updateScore(winner);
            resetBoard();
            noOfTimesButtonClicked = 0;
            return;
        }

        //right->left diagonal
        if(buttons[0][2].getText().equals(buttons[1][1].getText())
                && buttons[1][1].getText().equals(buttons[2][0].getText())
                && !buttons[0][2].getText().isEmpty())
        {
            String winner = buttons[0][2].getText();
            showWinner(winner);
            updateScore(winner);
            resetBoard();
            noOfTimesButtonClicked = 0;
            return;
        }

        if(noOfTimesButtonClicked == 9) //if we reach here and still not found any winner then it is a tie
        {
            showTie();
            resetBoard();
            noOfTimesButtonClicked = 0;
        }


    }

    private void showTie()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tie");
        alert.setHeaderText("");
        alert.setContentText("Game Over! It is a Tie");
        alert.showAndWait();
    }
    private void resetBoard()
    {
        for(Button []row : buttons)
        {
            for(Button button : row)
            {
                button.setText("");
                button.setStyle("-fx-background-color : white");
            }
        }
    }
    private void updateScore(String winner)
    {
        if(winner.equals("X"))
        {
            playerXScore++;
            playerXScoreLabel.setText("Player X : " + playerXScore);
        }
        else
        {
            playerYScore++;
            playerYScoreLabel.setText(("Player Y : " + playerYScore));
        }
    }
    private void showWinner(String winner)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setHeaderText("");
        if(winner.equals("O")) //DOING THIS BCZ I HAVE TAKEN O AS Y
            alert.setContentText("Congratulations Y ! You Won The Game");
        else
            alert.setContentText("Congratulations " + winner + " !  You Won The Game");
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) throws IOException {

        Scene scene = new Scene(createPane());
        stage.setTitle("Tic Tac Toe");
        Image icon = new Image("C:\\Users\\DELL\\IdeaProjects\\TicTacToe\\src\\TTTicon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}