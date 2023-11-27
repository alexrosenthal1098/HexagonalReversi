package controller;

import java.awt.Point;

import model.ModelListener;
import model.ReversiModel;
import player.PlayerActionListener;
import player.ReversiPlayer;
import view.gui.ReversiView;

/**
 * A controller that mediates between a reversi model and the player of the game.
 */
public class ReversiController implements ModelListener, PlayerActionListener {
  // fields to hold the model, player, and view
  private final ReversiModel model;
  private final ReversiPlayer player;
  private final ReversiView view;

  /**
   * A constructor for a ReversiController that takes a model to play on, a model's player, and
   * the view for that player.
   * @param model A ReversiModel that is being played.
   * @param player A ReversiPlayer that is playing on the model.
   * @param view A ReversiView that shows the player's view.
   * @param firstPlayer A boolean representing if this controller's player is the first player
   * @throws IllegalArgumentException if any of the given arguments are null.
   */
  public ReversiController(ReversiModel model, ReversiPlayer player, ReversiView view,
                           boolean firstPlayer) {
    if (model == null || player == null || view == null) {
      throw new IllegalArgumentException("No arguments can be null.");
    }
    // initialize the model, player, and view fields
    this.model = model;
    this.player = player;
    this.view = view;

    // register as a player listener using the firstPlayer boolean and as a readOnly listener
    this.model.addListener(this, firstPlayer);
    this.model.addReadOnlyListener(this);
    this.player.addListener(this); // register as a listener for the given player;

    this.view.addBoardListener(player); // register the player as a listener for the view
  }

  @Override
  public void yourTurn() {
    try {
      this.player.makeMove(); // tell the player to make a move
    }
    catch (Exception e) {
      this.view.showErrorMessage(e.getMessage()); // if an error occurred, display the message
    }
  }

  @Override
  public void modelChanged() {
    // when the model changes, deselect the view's current tile and repaint
    this.view.clearSelectedTiles();
    this.view.update();
  }

  @Override
  public void moveMade(Point tile) {
    try {
      this.model.moveAt(tile.x, tile.y); // try the move given to us from the player
    }
    catch (IllegalStateException | IllegalArgumentException e) {
      this.view.showErrorMessage(e.getMessage()); // if an error occurred, display the message
      this.player.makeMove(); // and tell the player to try again
    }
  }

  @Override
  public void turnPassed() {
    try {
      this.model.passTurn(); // pass the turn
    }
    catch (IllegalStateException e) {
      this.view.showErrorMessage(e.getMessage()); // if an error occurred, display the message
      this.player.makeMove(); // and tell the player to try again
    }
  }

  @Override
  public void errorOccurred(String message) {
    // display the error message
    this.view.showErrorMessage(message);
  }
}
