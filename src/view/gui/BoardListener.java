package view.gui;

/**
 * An interface that describes the features that a Reversi game's board can provide.
 */
public interface BoardListener {
  /**
   * An event that responds to a move trying to be made at the currently selected tile
   * on the board. It is up to the listener to get the currently selected tile.
   */
  void moveMade();

  /**
   * An event that responds to an attempt to pass the turn of the current player.
   */
  void turnPassed();
}
