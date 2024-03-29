package player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A human player of the game Reversi that interacts with the game using a GUI. The player
 * must be registered to the view.
 */
public final class HumanPlayer implements ReversiPlayer {
  private final List<PlayerActionListener> listeners; // a list of listeners to this player
  private boolean makingMove; // a boolean that represents if the player is currently making a move

  /**
   * A constructor for a human GUI player that accepts the model to play on.
   * @throws IllegalArgumentException if the given model is null.
   */
  public HumanPlayer() {
    this.listeners = new ArrayList<>(); // initialize listeners to empty array list
    this.makingMove = false; // set making move to false
  }

  @Override
  public void addListener(PlayerActionListener listener) {
    if (listener == null) { // check if the listener is null and throw exception if it is
      throw new IllegalArgumentException("Given listener cannot be null.");
    }

    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener); // add the listener to the list of listeners
    }
  }

  @Override
  public void makeMove() {
    this.makingMove = true; // set making move to true
  }

  @Override
  public void moveMade(Optional<Point> tilePoint) {
    // if this player isn't supposed to be making a move, notify listeners of an error
    if (!this.makingMove) {
      this.notifyOfError("It is not your turn!");
      return; // exit the method
    }

    // if the given tilePoint is null, notify listeners of an error
    if (tilePoint.isEmpty()) {
      this.notifyOfError("There is no tile selected.");
      return; // exit the method
    }

    this.makingMove = false; // set makingMove to false before notifying listeners
    // if the move is invalid, then the listener will tell the player to try again

    // iterate through all listeners and notify them that a move has been made at the tile point
    for (PlayerActionListener listener : this.listeners) {
      listener.moveMade(tilePoint.get());
    }
  }

  @Override
  public void turnPassed() {
    // if this player isn't supposed to be making a move, notify listeners of an error
    if (!this.makingMove) {
      this.notifyOfError("It is not your turn!");
      return; // exit the method
    }

    this.makingMove = false; // set makingMove to false

    // iterate through all listeners and notify them that the turn has been passed
    for (PlayerActionListener listener : this.listeners) {
      listener.turnPassed();
    }
  }

  // notifies all listeners of this player that an error occurred with the given message
  private void notifyOfError(String message) {
    for (PlayerActionListener listener : this.listeners) {
      listener.errorOccurred(message);
    }
  }
}
