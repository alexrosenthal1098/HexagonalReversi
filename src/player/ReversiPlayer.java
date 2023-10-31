package player;

import java.awt.Point;

import model.ViewReversiModel;

/**
 * An interface that represents player actions for the game Reversi.
 */
public interface ReversiPlayer {

  /**
   * Returns the coordinates of the tile that this player has chosen to make the next
   * move at on the given model.
   * @param model The model (read-only) that the player uses to choose the next move.
   * @return A Point representing the location of the tile to play at.
   */
  Point getNextMove(ViewReversiModel model);
}
