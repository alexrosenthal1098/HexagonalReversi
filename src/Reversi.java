import controller.ReversiController;
import model.HexagonalReversi;
import model.ReversiModel;
import player.HumanPlayer;
import player.ReversiAI;
import player.ReversiPlayer;
import strategy.HexCaptureMaxPieces;
import view.gui.ReversiFrame;
import view.gui.ReversiView;

/**
 * Main class for Reversi.
 */
public final class Reversi {

  /**
   * Main method for reversi.
   * @param args Command line inputs.
   */
  public static void main(String[] args) {
    ReversiModel model = new HexagonalReversi();
    ReversiView viewPlayer1 = new ReversiFrame(model, "Player 1");
    ReversiView viewPlayer2 = new ReversiFrame(model, "Player 2");
    ReversiPlayer player1 = new HumanPlayer();
    ReversiPlayer player2 = new HumanPlayer();
    ReversiController controller1 =
            new ReversiController(model, player1, viewPlayer1, true);
    ReversiController controller2 =
            new ReversiController(model, player2, viewPlayer2, false);
    model.startGame();
  }
}