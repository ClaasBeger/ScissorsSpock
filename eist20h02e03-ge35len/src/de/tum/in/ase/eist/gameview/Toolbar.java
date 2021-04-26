package de.tum.in.ase.eist.gameview;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.Optional;

import de.tum.in.ase.eist.BumpersApplication;

/**
 *
 * This class visualizes the tool bar with start, stop and exit buttons above
 * the game board.
 *
 */
public class Toolbar extends ToolBar {
    private BumpersApplication gameWindow;
    private Button start;
    private Button stop;
    private Button HowToPlay;
    private Button Easy;
    private Button Middle;
    private Button Hard;
    private Button PacManMode;

    public Toolbar(BumpersApplication gameWindow) {
        this.start = new Button("Start");
        this.stop = new Button("Stop");
        this.HowToPlay = new Button("How to play");
        this.Easy = new Button("Easy");
        this.Middle = new Button("Middle");
        this.Hard = new Button("Hard");
        this.PacManMode = new Button("PacManMode");
        initActions();

        this.getItems().addAll(start, new Separator(), stop, new Separator(), HowToPlay, new Separator(), Easy, new Separator(), Middle, new Separator(), Hard, new Separator(), PacManMode);
        this.setGameWindow(gameWindow);
    }

    /**
     * Initialises the actions
     */
    private void initActions() {
        this.start.setOnAction(event -> getGameWindow().gameBoardUI.startGame());

        this.stop.setOnAction(event -> {
            Toolbar.this.getGameWindow().gameBoardUI.stopGame();

            ButtonType YES = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType NO = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(AlertType.CONFIRMATION, "Do you really want to stop the game ?", YES, NO);
            alert.setTitle("Stop Game Confirmation");
            alert.setHeaderText("");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == YES) {
                getGameWindow().gameBoardUI.gameSetup();
            } else {
                getGameWindow().gameBoardUI.startGame();
            }
        });
            this.HowToPlay.setOnAction(event -> {
            	Image im = new Image("RPSLS_rules.jpg");
            	ImageView imageview = new ImageView(im);
            	Alert alert = new Alert(AlertType.INFORMATION, "How to Play");
            	alert.setGraphic(imageview);
            	alert.setHeaderText("");
                alert.setContentText("Hello! This game is called Rock Paper Scissors Spock. \nYou can see the rules on the left but be careful, your symbol changes every few seconds ! \nYou can steer your Icon(which is on the top Left) by clicking with the mouse! \nIf you want to, you may adjust the difficulty with the other Buttons, but watch out, if you get hit once your game is overrr. \nWhen you adjust the difficulty, more symbols will be generated and there will be less time between symbol changes!\nYou can also play the PacMan Mode, in which you only have winning opponent symbols for 10 seconds and then only losing ones for another ten and after that they keep changing!\nEverytime the types change your opponents become faster:)\nHave fun playing and live long and prosper!\n");
            	alert.setResizable(true);
            	
                alert.showAndWait();

            });
            this.Easy.setOnAction(event ->{
            	this.gameWindow.gameBoardUI.setMode("Easy");
            });
            this.Middle.setOnAction(event ->{
            	this.gameWindow.gameBoardUI.setMode("Middle");
            });
            this.Hard.setOnAction(event ->{
            	this.gameWindow.gameBoardUI.setMode("Hard");
            });
            this.PacManMode.setOnAction(event ->{
            	this.gameWindow.gameBoardUI.setPacManMode();
            });
    }

    /**
     * Resets the toolbar button status
     * @param running Used to disable/enable buttons
     */
    public void resetToolBarButtonStatus(boolean running) {
        this.start.setDisable(running);
        this.stop.setDisable(!running);
        this.Easy.setDisable(running);
        this.Middle.setDisable(running);
        this.Hard.setDisable(running);
        this.PacManMode.setDisable(running);
    }

    /**
     * @return current gameWindow
     */
    public BumpersApplication getGameWindow() {
        return this.gameWindow;
    }

    /**
     * @param gameWindow New gameWindow to be set
     */
    public void setGameWindow(BumpersApplication gameWindow) {
        this.gameWindow = gameWindow;
    }
}