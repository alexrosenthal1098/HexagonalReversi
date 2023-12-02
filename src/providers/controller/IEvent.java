package providers.controller;

import providers.model.board.ICell;

/**
 * this interface is used to represent the events that can happen in the game.
 */
public interface IEvent {

  /**
   * this method is used to get the cell that the player wants to move to.
   */
  ICell getCell();
}
