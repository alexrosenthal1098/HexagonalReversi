import adapters.BothModels;
import adapters.ProviderStrategyAIPlayer;
import adapters.ToOurView;
import adapters.ToProviderModel;
import controller.ReversiController;
import player.HumanPlayer;
import player.ReversiPlayer;
import providers.model.strategies.MaxNumOfCells;
import providers.model.strategies.MiniMax;
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
    /*
    if (args.length != 2) { // ensure there are 2 command line arguments
      throw new IllegalArgumentException("Two players must be specified.");
    }
    // initialize the model and declare players
    ReversiModel model = new HexagonalReversi(3);
    ReversiPlayer player1;
    ReversiPlayer player2;

    switch (args[0]) {
      case "human":
        player1 = new HumanPlayer();
        break;
      case "capture-max":
        player1 = new ReversiAI(model, new CaptureMaxPieces());
        break;
      default:
        throw new IllegalArgumentException("Unsupported player type \"" + args[0] + "\"");
    }

    switch (args[1]) {
      case "human":
        player2 = new HumanPlayer();
        break;
      case "capture-max":
        player2 = new ReversiAI(model, new CaptureMaxPieces());
        break;
      default:
        throw new IllegalArgumentException("Unsupported player type \"" + args[0] + "\"");
    }

    // initialize views and controllers using given player types
    ReversiView viewPlayer1 = new ReversiFrame(model, "Player 1");
    ReversiView viewPlayer2 = new ReversiFrame(model, "Player 2");
    ReversiController controller1 =
            new ReversiController(model, player1, viewPlayer1, true);
    ReversiController controller2 =
            new ReversiController(model, player2, viewPlayer2, false);

    // start the game
    model.startGame();

     */

    BothModels theirModel = new ToProviderModel(3);
    ReversiPlayer human1 = new HumanPlayer();
    ReversiPlayer human2 = new ProviderStrategyAIPlayer(theirModel, new MaxNumOfCells());


    ReversiView ourViewPlayer1 = new ReversiFrame(theirModel, "Player 1");
    ReversiView theirViewPlayer2 = new ToOurView(theirModel);
    ReversiController controller1 =
            new ReversiController(theirModel, human1, ourViewPlayer1, true);
    ReversiController controller2 =
            new ReversiController(theirModel, human2, theirViewPlayer2, false);
    theirModel.startGame();
  }
}