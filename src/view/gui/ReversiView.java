package view.gui;

/**
 * A view for a Reversi game that displays information about the game. This interface
 * defines the actions that the view can perform.
 */
public interface ReversiView {

  /**
   * Add a class to the list of those who listen to events from this board.
   * @param listener The class to add to the list of listeners.
   * @throws IllegalArgumentException if the given listener is null;
   */
  void addBoardListener(BoardListener listener) throws IllegalArgumentException;

  /**
   * Clears any selected tiles on the board.
   */
  void clearSelectedTiles();

  /**
   * Updates the view according to the current state of the model.
   */
  void update();

  /**
   * Displays an error message.
   * @param message The string of the error message to be displayed.
   */
  void showErrorMessage(String message);
}
