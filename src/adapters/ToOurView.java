package adapters;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Optional;

import providers.controller.ViewListener;
import providers.model.board.ICell;
import providers.model.board.ReadonlyReversiModel;
import providers.view.ReversiGraphicsView;
import view.gui.BoardListener;
import view.gui.ReversiView;

/**
 * A class that adapts the provider's view to our view interface.
 */
public class ToOurView extends ReversiGraphicsView implements ReversiView, ViewListener {
  private final ArrayList<BoardListener> listeners; // a list of listeners to this view
  private final int sideLength; // the side length of the board, in number of tiles

  /**
   * A constructor that takes in the provider's model.
   * @param model The model to base the view off of.
   */
  public ToOurView(ReadonlyReversiModel model) {
    super(model);
    super.addListener(this);
    this.listeners = new ArrayList<>();
    this.sideLength = model.getSize();
    super.setVisible(true);
  }

  @Override
  public void addBoardListener(BoardListener listener) throws IllegalArgumentException {
    if (listener == null) { // check if the given listener is null and throw exception if it is
      throw new IllegalArgumentException("Cannot register a null listener.");
    }
    if (!listeners.contains(listener)) {
      this.listeners.add(listener);
    }
  }

  @Override
  public void clearSelectedTiles() {
    // the provider's view does not implement this feature
  }

  @Override
  public void update() {
    super.refresh();
  }

  @Override
  public void onViewEvent(ICell cell) {
    if (cell == null) {
      for (BoardListener listener : this.listeners) {
        listener.turnPassed();
      }
    }
    else {
      for (BoardListener listener : this.listeners) {
        Point ourCoords = AdapterUtils.toOurCoordinates(cell, this.sideLength);
        listener.moveMade(Optional.of(new Point(ourCoords.x, ourCoords.y)));
      }
    }
  }
}
