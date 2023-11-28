package view.gui;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import model.ReadOnlyReversiModel;

/**
 * The frame of a GUI for a game of Reversi.
 */
public class ReversiFrame extends JFrame implements ReversiView {
  private final HexagonalBoard board; // the display of the board

  /**
   * A constructor for a ReversiFrame that takes in a reversi model to display.
   * @param model A ReadOnlyReversiModel to display.
   */
  public ReversiFrame(ReadOnlyReversiModel model, String title) {
    super();
    this.setSize(new Dimension(800, 800));
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    this.board = new HexagonalBoard(800, 800, model);
    this.add(this.board, BorderLayout.CENTER);

    // the title label
    JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
    titleLabel.setBackground(new Color(50, 50, 50));
    titleLabel.setOpaque(true);
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Comic Sans", Font.PLAIN, 30));
    this.add(titleLabel, BorderLayout.NORTH);

    this.pack();
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

  @Override
  public void showErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }
}
