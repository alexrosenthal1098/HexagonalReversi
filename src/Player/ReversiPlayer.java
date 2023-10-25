package Player;

import java.awt.Point;

/**
 * An interface that represents player actions for the game Reversi.
 */
public interface ReversiPlayer {

  /**
   * Returns the coordinates of the tile that the player has chosen to make the next at.
   * @return A Point representing the location of the tile to play at.
   */
  Point getNextMove();
}
