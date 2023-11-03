package view.gui;

import java.awt.Point;

/**
 * An interface that describes the features that a Reversi game HexagonalBoard can provide.
 */
public interface BoardFeatures {

  /**
   * Select a tile on the HexagonalBoard using the given tile location.
   * @param tileLocation The point that represents the given tile's location on the HexagonalBoard.
   * @throws IllegalArgumentException if the given tile location is null.
   * @throws IllegalStateException if there is no tile at that location.
   */
  void selectTile(Point tileLocation) throws IllegalArgumentException, IllegalStateException;
  // this method is shared between BoardFeatures and ReversiBoard because a HexagonalBoard view should first
  // send information about what tile is trying to be selected, and the controller (who implements
  // BoardFeatures) should use that information to call selectTile on the HexagonalBoard view.
  // ex: A controller can ensure that only tiles where a valid move can be played can be selected.

  /**
   * Deselect the tile that is currently selected. If no tile is currently selected,
   * then nothing should change.
   */
  void deselectTile();

  /**
   * Make a move at the given location.
   * @throws IllegalArgumentException if the given tile location is null.
   * @throws IllegalStateException if there is no tile at that location, or that move
   *                               is not possible.
   */
  void makeMove(Point tileLocation) throws IllegalArgumentException, IllegalStateException;

  /**
   * Pass the turn of the current player.
   */
  void passTurn();

  /**
   * Exit the game and stop playing.
   */
  void exitGame();
}
