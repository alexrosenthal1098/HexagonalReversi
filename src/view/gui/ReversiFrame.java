package view.gui;

import java.awt.Dimension;

import javax.swing.*;

import model.ReadOnlyReversiModel;

/**
 * The frame of a GUI for a game of Reversi.
 */
public class ReversiFrame extends JFrame implements ReversiView {
  private final HexagonalBoard board;
  private final JLabel titleLabel;

  /**
   * A constructor for a ReversiFrame that takes in a reversi model to display.
   * @param model A ReadOnlyReversiModel to display.
   */
  public ReversiFrame(ReadOnlyReversiModel model, String title) {
    super();
    setSize(new Dimension(800, 800));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    // todo figure out the layout thing
    this.board = new HexagonalBoard(800, 800, model);
    add(this.board);

    this.titleLabel = new JLabel(title);
    //add(this.titleLabel); // todo make this north

    this.setVisible(true);
  }

  @Override
  public void addBoardListener(BoardListener listener) {
    if (listener == null) { // check if the given listener is null and throw exception if it is
      throw new IllegalArgumentException("Cannot register a null listener.");
    }
    this.board.addListener(listener); // add the listener to the board
  }

  @Override
  public void clearSelectedTiles() {
    this.board.deselectCurrentTile();
  }

  @Override
  public void update() {
    this.board.repaint();
  }
}
