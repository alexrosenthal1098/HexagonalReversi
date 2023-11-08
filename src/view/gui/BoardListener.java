package view.gui;

import java.awt.Point;

/**
 * An interface that describes the features that a Reversi game HexagonalBoard can provide.
 */
public interface BoardListener {

  /**
   * An event that represents a tile that is attempting to be selected on the HexagonalBoard.
   * @param tileLocation The point that represents the given tile's location on the HexagonalBoard.
   * @throws IllegalArgumentException if the given tile location is null.
   * @throws IllegalStateException if there is no tile at that location.
   */
  void tileSelected(Point tileLocation) throws IllegalArgumentException, IllegalStateException;
  // this method is shared between BoardListener and ReversiBoard because a HexagonalBoard view
  // should first send information about what tile is trying to be selected, and the controller
  // (who implements BoardListener) should use that information to call selectTile on the
  // HexagonalBoard view.
  // ex: A controller can ensure that only tiles where a valid move can be played can be selected.

  /**
   * An event that responds to an attempt to deselect the tile that is currently selected.
   */
  void tileDeselected();

  /**
   * An event that responds to a move trying to be made at the given tile location.
   * @param tileLocation A point that represents the location of the tile on the board.
   * @throws IllegalArgumentException if the given tile location is null.
   * @throws IllegalStateException if there is no tile at that location, or that move
   *                               is not possible.
   */
  void moveMade(Point tileLocation) throws IllegalArgumentException, IllegalStateException;

  /**
   * An event that responds to an attempt to pass the turn of the current player.
   */
  void turnPassed();
}
