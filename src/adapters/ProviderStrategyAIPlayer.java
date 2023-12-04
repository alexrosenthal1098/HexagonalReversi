package adapters;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import player.PlayerActionListener;
import player.ReversiPlayer;
import providers.model.board.ICell;
import providers.model.board.ReversiModel;
import providers.model.strategies.IStrategies;

/**
 * A reversi player that uses a strategy from the provider.
 */
public class ProviderStrategyAIPlayer implements ReversiPlayer {
  private final ReversiModel model; // the model that is being played on
  private final IStrategies strategy; // the strategy this model uses
  private final List<PlayerActionListener> listeners; // a list of listeners to this player

  /**
   * A constructor that takes in a provider strategy.
   * @param strategy A strategy for this player to use.
   */
  public ProviderStrategyAIPlayer(BothModels model, IStrategies strategy) {
    if (model == null || strategy == null) {
      throw new IllegalArgumentException("Given model or strategy cannot be null.");
    }
    this.model = model;
    this.strategy = strategy;
    this.listeners = new ArrayList<>();
  }

  @Override
  public void addListener(PlayerActionListener listener) throws IllegalArgumentException {
    if (listener == null) { // check if the listener is null and throw exception if it is
      throw new IllegalArgumentException("Given listener cannot be null.");
    }

    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener); // add the listener to the list of listeners
    }
  }

  @Override
  public void makeMove() {
    ICell cellToMove = this.strategy.strategicMove(this.model, this.model.getBoard(),
            this.model.getColor());
    if (cellToMove == null) {
      for (PlayerActionListener listener : this.listeners) {
        listener.turnPassed();
      }

    }
    else {
      Point ourCoordsMove = AdapterUtils.toOurCoordinates(cellToMove, this.model.getSize());
      for (PlayerActionListener listener : this.listeners) {
        listener.moveMade(ourCoordsMove);
      }
    }
  }


  // since this is an AI player, ignore actions from the board
  @Override
  public void moveMade(Optional<Point> tilePoint) {
    // AI player does not receive moves from the board.
  }

  @Override
  public void turnPassed() {
    // AI player does not receive moves from the board.
  }
}