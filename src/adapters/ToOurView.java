package adapters;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
  private final ArrayList<BoardListener> listeners;

  /**
   * A constructor that takes in the provider's model.
   * @param model The model to base the view off of.
   */
  public ToOurView(ReadonlyReversiModel model) {
    super(model);
    super.addListener(this);
    this.listeners = new ArrayList<>();
    super.setVisible(true);

    this.addMouseListener(new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        refresh();
      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    });
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
        listener.moveMade(Optional.of(new Point(cell.getX(), cell.getY())));
      }
    }
  }
}
