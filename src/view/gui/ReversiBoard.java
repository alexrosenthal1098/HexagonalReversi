package view.gui;

import java.awt.Point;
import java.util.Optional;

import model.ModelListener;

/**
 * A view for a Reversi game board along with the actions it can perform. A class that
 * implements this interface is free to represent the board however it likes.
 */
public interface ReversiBoard {

  /**
   * Select the tile at the given location. Only one tile can be selected at a time.
   * @param tileLocation The point referring to the coordinates of the tile on the board.
   * @throws IllegalArgumentException if the given point is null
   * @throws IllegalStateException if there is already a tile selected or the given point is
   *                               not on the board.
   */
  void selectTile(Point tileLocation) throws IllegalArgumentException, IllegalStateException;

  /**
   * Deselect the tile that is currently selected. If no tile is currently selected,
   * then nothing should change.
   */
  void deselectCurrentTile();

  /**
   * Get the tile that is currently selected.
   * @return An optional point representing the tile that is selected. If it is empty, then no
   *         tile is selected.
   */
  Optional<Point> getSelectedTile();


  /**
   * Add a class to the list of those who listen to events from this board.
   * @param listener The class to add to the list of listeners.
   * @throws IllegalArgumentException if the given listener is null.
   */
  void addListener(BoardListener listener) throws IllegalArgumentException;
}
