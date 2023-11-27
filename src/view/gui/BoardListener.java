package view.gui;

import java.awt.Point;
import java.util.Optional;

/**
 * An interface that describes the features that a Reversi game's board can provide.
 */
public interface BoardListener {
  /**
   * An event that responds to a move trying to be made at the currently selected tile.
   * @param tilePoint An optional point that has been selected to make a move at. If empty, then
   *                  there is no tile selected currently.
   */
  void moveMade(Optional<Point> tilePoint);

  /**
   * An event that responds to an attempt to pass the turn of the current player.
   */
  void turnPassed();
}
