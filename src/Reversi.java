import adapters.BothModels;
import adapters.ProviderStrategyPlayer;
import adapters.ToOurView;
import adapters.ToProviderModel;
import controller.ReversiController;
import player.HumanPlayer;
import player.ReversiAI;
import player.ReversiPlayer;
import providers.model.strategies.AvoidCellsSurroundingCorners;
import providers.model.strategies.GoForCorners;
import providers.model.strategies.MaxNumOfCells;
import providers.model.strategies.MiniMax;
import providers.model.strategies.TryStrat;
import strategy.CaptureMaxPieces;
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
    if (args.length != 2) { // ensure there are 2 command line arguments
      throw new IllegalArgumentException("Two players must be specified.");
    }

    // initialize the model and players
    BothModels theirModel = new ToProviderModel();
    ReversiPlayer ourStratPlayer = Reversi.parsePlayer1(args[0], theirModel);
    ReversiPlayer theirStratPlayer = Reversi.parsePlayer2(args[1], theirModel);

    // initialize views and controllers using given player types
    ReversiView ourView = new ReversiFrame(theirModel, "Player 1");
    ReversiView theirView = new ToOurView(theirModel);
    ReversiController controller1 =
            new ReversiController(theirModel, ourStratPlayer, ourView, true);
    ReversiController controller2 =
            new ReversiController(theirModel, theirStratPlayer, theirView, false);

    // start the game
    theirModel.startGame();
  }


  // parses the given string representing which strategy/player to use and returns
  // an instance of the corresponding player from OUR strategies
  private static ReversiPlayer parsePlayer1(String player, BothModels model) {
    switch (player) {
      case "human":
        return new HumanPlayer();
      case "capture-max":
        return new ReversiAI(model, new CaptureMaxPieces());
      default:
        throw new IllegalArgumentException("Unsupported player1 type \"" + player + "\"");
    }
  }

  // parses the given string representing which strategy/player to use and returns
  // an instance of the corresponding player from the PROVIDER strategies
  private static ReversiPlayer parsePlayer2(String player, BothModels model) {
    switch (player) {
      case "human":
        return new HumanPlayer();
      case "max-cells":
        return new ProviderStrategyPlayer(model, new MaxNumOfCells());
      case "corners":
        return new ProviderStrategyPlayer(model, new GoForCorners());
      case "avoid-near-corners":
        return new ProviderStrategyPlayer(model, new AvoidCellsSurroundingCorners());
      case "minimax":
        return new ProviderStrategyPlayer(model, new MiniMax());
      case "try-all":
        return new ProviderStrategyPlayer(model, new TryStrat());
      default:
        throw new IllegalArgumentException("Unsupported player2 type \"" + player + "\"");
    }
  }
}