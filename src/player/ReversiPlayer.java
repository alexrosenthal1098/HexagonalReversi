package player;

import java.awt.Point;

import model.ReadOnlyReversiModel;

/**
 * An interface that represents a player of a game of Reversi.
 */
public interface ReversiPlayer {

  /**
   * Add a class to the list of those who listen to events from this player.
   * @param listener The class to add to the list of listeners.
   */
  void addListener(PlayerActionListener listener);
}
