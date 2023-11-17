package view.gui;

import java.awt.Point;

/**
 * An interface that describes the features that a Reversi game's board can provide.
 */
public interface BoardListener {
  /**
   * An event that responds to a move trying to be made at the given tile point.
   * @param tilePoint The Point that has been selected to make a move at.
   * @throws IllegalArgumentException if the given point is null.
   */
  void moveMade(Point tilePoint) throws IllegalArgumentException;

  /**
   * An event that responds to an attempt to pass the turn of the current player.
   */
  void turnPassed();
}
