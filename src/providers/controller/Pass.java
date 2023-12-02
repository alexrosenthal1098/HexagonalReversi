package providers.controller;

import providers.model.board.ICell;

/**
 * this class is used to represent the pass event.
 */
public class Pass implements IEvent {

  /**
   * this method is used to get the cell that the player wants to move to.
   */
  @Override
  public ICell getCell() {
    return null;
  }


}
