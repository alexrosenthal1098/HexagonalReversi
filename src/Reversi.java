import model.HexagonalReversi;
import model.ReversiModel;
import view.gui.ReversiFrame;

/**
 * Main class for Reversi.
 */
public final class Reversi {

  /**
   * Main method for reversi.
   * @param args Command line inputs.
   */
  public static void main(String[] args) {
    ReversiModel model = new HexagonalReversi(6);
    ReversiFrame view = new ReversiFrame(model);
    view.setVisible(true);
  }
}