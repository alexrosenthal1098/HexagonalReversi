package providers.controller;

import providers.model.board.ICell;

/**
 * this interface is used to represent what events will be listined to from the model.
 */
public interface ModelListener {

  /**
   * this method is used to listen to the model events.
   */
  void onModelEvent(ICell cell);
}