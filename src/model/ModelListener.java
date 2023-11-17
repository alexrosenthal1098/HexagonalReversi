package model;

/**
 * An interface that defines the events emitted from a Reversi model that a class can listen to.
 */
public interface ModelListener {

  /**
   * Notifies the listener for a certain player that it is now their turn.
   */
  void yourTurn();

  /**
   * Notifies the listener that the model has changed.
   */
  void modelChanged();
}
