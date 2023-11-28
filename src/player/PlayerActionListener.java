package player;

import java.awt.Point;

/**
 * An interface that describes the player actions that can be emitted for the game Reversi. A
 * class that implements this interface can subscribe to a ReversiPlayer and listen to the actions.
 */
public interface PlayerActionListener {
  /**
   * An event that responds to the player attempting to move at the given tile location.
   * @param tile A point representing the location of the tile the move was made at.
   * @throws IllegalArgumentException if the given Point is null.
   */
  void moveMade(Point tile) throws IllegalArgumentException;

  /**
   * An event that responds to an attempt to pass the turn of the player.
   */
  void turnPassed();

  /**
   * An event that triggers when an error occurs with the player.
   * @param message A message explaining the error.
   */
  void errorOccurred(String message);
}
