package view.gui;

import java.awt.*;

import javax.swing.*;

import model.ReadOnlyReversiModel;

/**
 * The frame of a GUI for a game of Reversi.
 */
public class ReversiFrame extends JFrame implements ReversiView {
  private final HexagonalBoard board; // the display of the board
  private final JLabel titleLabel; // the title label

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

    this.titleLabel = new JLabel(title, SwingConstants.CENTER);
    this.titleLabel.setBackground(new Color(50, 50, 50));
    this.titleLabel.setOpaque(true);
    this.titleLabel.setForeground(Color.WHITE);
    this.titleLabel.setFont(new Font("Comic Sans", Font.PLAIN, 30));
    this.add(this.titleLabel, BorderLayout.NORTH);

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
