package view.gui;

import java.awt.*;

/**
 * A view for a Reversi game board along with the actions it can perform.
 */
public interface BoardView {

  /**
   * Select the tile at the given location. Only one tile can be selected at a time.
   * @param tileLocation The point referring to the coordinates of the tile on the board.
   * @throws IllegalArgumentException if the given point is null or the point is not on the board.
   * @throws IllegalStateException if there is already a tile selected.
   */
  void selectTile(Point tileLocation) throws IllegalArgumentException, IllegalStateException;

  /**
   * Deselect the tile that is currently selected. If no tile is currently selected,
   * then nothing should change.
   */
  void deselectCurrentTile();

  /**
   * Get the tile that is currently selected.
   * @return A Points representing the tile that is selected.
   * @throws IllegalStateException if there is no tile selected.
   */
  Point getSelectedTile() throws IllegalStateException;
}
