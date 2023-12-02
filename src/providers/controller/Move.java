package providers.controller;

import providers.model.board.ICell;

/**
 * this class is used to represent the move event.
 */
public class Move implements IEvent {
  ICell cell;

  /**
   * this is the constructor of the class.
   */
  public Move(ICell cell) {
    this.cell = cell;
  }

  /**
   * gets the cell that the player wants to move to.
   */
  public ICell getCell() {
    return cell;
  }
}
