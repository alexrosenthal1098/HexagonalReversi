package player;


import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.ReadOnlyReversiModel;
import strategy.ReversiStrategy;

/**
 * An AI player for a game of Reversi that uses the given strategy to select a move.
 */
public class ReversiAI implements ReversiPlayer {
  private final ReadOnlyReversiModel model; // the model that is being played on
  private final List<PlayerActionListener> listeners; // a list of listeners to this player
  private final ReversiStrategy strategy; // the strategy this model uses

  /**
   * A constructor for a human GUI player that accepts the model to play on.
   * @param model A read-only version of the model.
   * @throws IllegalArgumentException if the given model or strategy is null.
   */
  public ReversiAI(ReadOnlyReversiModel model, ReversiStrategy strategy) {
    if (model == null) { // check if the model is null and throw exception if it is
      throw new IllegalArgumentException("Given model cannot be null.");
    }
    if (strategy == null) { // check if the strategy is null and throw exception if it is
      throw new IllegalArgumentException("Given strategy cannot be null.");
    }
    this.model = model; // set the model
    this.listeners = new ArrayList<>(); // initialize listeners to empty array list
    this.strategy = strategy;
  }

  @Override
  public void addListener(PlayerActionListener listener) {
    if (listener == null) { // check if the listener is null and throw exception if it is
      throw new IllegalArgumentException("Given listener cannot be null.");
    }
    this.listeners.add(listener); // add the listener to the list of listeners
  }

  @Override
  public void makeMove() {
    Optional<Point> move = this.strategy.chooseMove(this.model); // get move from the strategy
    if (move.isEmpty()) { // if the move is empty, then notify listeners to pass the turn
      for (PlayerActionListener listener : this.listeners) {
        listener.turnPassed();
      }
    }
    else { // if the move is not empty, notify listeners to move at the strategy's chosen point.
      for (PlayerActionListener listener : this.listeners) {
        listener.moveMade(move.get());
      }
    }
  }


  // since this is an AI player, ignore actions from the board
  @Override
  public void moveMade(Point tilePoint) {

  }

  @Override
  public void turnPassed() {

  }
}