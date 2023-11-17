package player;


import view.gui.BoardListener;

/**
 * An interface that represents a player of a game of Reversi.
 */
public interface ReversiPlayer extends BoardListener {

  /**
   * Add a class to the list of those who listen to events from this player.
   * @param listener The class to add to the list of listeners.
   */
  void addListener(PlayerActionListener listener);

  /**
   * Ask the player to make a move. The listener will be notified when the move is made.
   */
  void makeMove();
}
