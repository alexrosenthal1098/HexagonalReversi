package player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import model.ReadOnlyReversiModel;
import view.gui.BoardListener;

/**
 * A human player of the game Reversi that interacts with the game using a GUI.
 */
public final class HumanGUIPlayer implements ReversiPlayer {
  private final ReadOnlyReversiModel model; // the model that is being played on
  private final List<PlayerActionListener> listeners; // a list of listeners to this player
  private boolean makingMove; // a boolean that represents if the player is currently making a move

  /**
   * A constructor for a human GUI player that accepts the model to play on.
   * @param model A read-only version of the model.
   * @throws IllegalArgumentException if the given model is null.
   */
  public HumanGUIPlayer(ReadOnlyReversiModel model) {
    if (model == null) { // check if the model is null and throw exception if it is
      throw new IllegalArgumentException("Given model cannot be null.");
    }
    this.model = model; // set the model
    this.listeners = new ArrayList<>(); // initialize listeners to empty array list
    this.makingMove = false; // set making move to false
  }

  @Override
  public void addListener(PlayerActionListener listener) {
    if (listener == null) { // check if the listener is null and throw exception if it is
      throw new IllegalArgumentException("Given listener cannot be null.");
    }
    this.listeners.add(listener); // add the listener to the list of listeners
  }

  @Override
  public void makeMove() {
    this.makingMove = true; // set making move to true
  }

  @Override
  public void moveMade(Point tilePoint) {
    if (tilePoint == null) { // check if the given tilePoint is null and throw exception if it is
      throw new IllegalArgumentException("Given tile point cannot be null.");
    }
    if (!this.makingMove) { // if this player isn't supposed to be making a move, throw exception
      throw new IllegalStateException("It is not your turn!");
    }
    // iterate through all listeners and notify them that a move has been made at the tile point
    for (PlayerActionListener listener : this.listeners) {
      listener.moveMade(tilePoint);
    }

    this.makingMove = false; // after all listeners have been notified, set makingMove to false
  }

  @Override
  public void turnPassed() {
    if (!this.makingMove) { // if this player isn't supposed to be making a move, throw exception
      throw new IllegalStateException("It is not your turn!");
    }

    // iterate through all listeners and notify them that the turn has been passed
    for (PlayerActionListener listener : this.listeners) {
      listener.turnPassed();
    }

    this.makingMove = false; // after all listeners have been notified, set makingMove to false
  }
}