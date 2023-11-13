package view.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.ReadOnlyReversiModel;

/**
 * The frame of a GUI for a game of Reversi.
 */
public class ReversiFrame extends JFrame implements ReversiView {
  HexagonalBoard board;

  /**
   * A constructor for a ReversiFrame that takes in a reversi model to display.
   * @param model A ReadOnlyReversiModel to display.
   */
  public ReversiFrame(ReadOnlyReversiModel model) {
    super();
    setSize(new Dimension(800, 800));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.board = new HexagonalBoard(800, 800, model);
    add(this.board);
  }


  // this method will be left blank until the ReversiView interface is finalized
  @Override
  public void addLister(ReversiListener listener) {
    // will be implemented later
  }
}
