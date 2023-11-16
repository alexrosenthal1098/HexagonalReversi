package player;

import java.awt.*;

/**
 * An interface that describes the player actions that can be emitted for the game Reversi. A
 * class that implements this interface can subscribe to a ReversiPlayer and listen to the actions.
 */
public interface PlayerActionListener {
  /**
   * An event that responds to the player attempting to move at the given tile location.
   * @param tile A point representing the location of the tile the move was made at.
   */
  void moveMade(Point tile);

  /**
   * An event that responds to an attempt to pass the turn of the player.
   */
  void turnPassed();
}
