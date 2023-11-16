package model;

/**
 * An interface that defines the events emitted from a Reversi model that a class can listen to.
 */
public interface ModelListener {

  /**
   * Notifies the listener that it is now their turn.
   */
  void yourTurn();
}
