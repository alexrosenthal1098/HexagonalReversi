import model.HexagonalReversi;
import model.ReversiModel;
import view.gui.ReversiFrame;

public final class Reversi {
  public static void main(String[] args) {
    ReversiModel model = new HexagonalReversi(6);
    ReversiFrame view = new ReversiFrame(model);
    view.setVisible(true);
  }
}