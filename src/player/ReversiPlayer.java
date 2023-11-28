package player;


import view.gui.BoardListener;

/**
 * An interface that represents a player of a game of Reversi.
 */
public interface ReversiPlayer extends BoardListener {

  /**
   * Add a class to the list of those who listen to events from this player.
   * @param listener The class to add to the list of listeners.
   * @throws IllegalArgumentException if the given listener is null.
   */
  void addListener(PlayerActionListener listener) throws IllegalArgumentException;

  /**
   * Ask the player to make a move. The listener will be notified when the move is made.
   */
  void makeMove();
}
