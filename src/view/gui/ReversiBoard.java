package view.gui;

import java.awt.*;

/**
 * A view for a Reversi game HexagonalBoard along with the actions it can perform. A class that
 * implements this interface is free to represent the HexagonalBoard however it likes.
 */
public interface ReversiBoard {

  /**
   * Select the tile at the given location. Only one tile can be selected at a time.
   * @param tileLocation The point referring to the coordinates of the tile on the HexagonalBoard.
   * @throws IllegalArgumentException if the given point is null or the point is not on the HexagonalBoard.
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


  /**
   * Add a class to the list of those who implement features of this board so that they
   * can be updated with feature-level events.
   * @param feature The class to add to the list of features.
   */
  void addFeature(BoardFeatures feature);
}
