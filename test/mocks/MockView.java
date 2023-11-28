package mocks;

import view.gui.BoardListener;
import view.gui.ReversiView;

/**
 * A mock class of a reversi view.
 */
public class MockView implements ReversiView {
  public StringBuilder log = new StringBuilder();

  @Override
  public void addBoardListener(BoardListener listener) throws IllegalArgumentException {
    // this method does not need to be supported for this mock.
  }

  @Override
  public void clearSelectedTiles() {
    this.log.append("cleared\n");
  }

  @Override
  public void update() {
    this.log.append("updated\n");
  }

  @Override
  public void showErrorMessage(String message) {
    this.log.append(message).append("\n");
  }
}
