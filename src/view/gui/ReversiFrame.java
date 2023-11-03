package view.gui;

import java.awt.*;

import javax.swing.*;

import model.HexagonalReversi;
import model.ReadOnlyReversiModel;

public class ReversiFrame extends JFrame implements ReversiView {
  HexagonalBoard board;
  public ReversiFrame(ReadOnlyReversiModel model) {
    super();
    setSize(new Dimension(800, 800));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.board = new HexagonalBoard(800, 800, model);
    add(this.board);
  }
}
