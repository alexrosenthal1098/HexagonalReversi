package view.gui;

/**
 * A view for a Reversi game that displays information about the game. This interface
 * defines the actions that the view can perform.
 */
public interface ReversiView {
  /**
   * Adds a listener that received events from this view.
   * @param listener A class that listens to the events that occur in this view.
   */
  void addLister(ReversiListener listener);

  // leaving this empty for now because we don't know what the overall ReversiFrame for the
  // game is going to look like

  // could include things like exitGame, setMoveNumber, displayControls, etc.
}
