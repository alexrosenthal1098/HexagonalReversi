package strategy;

import java.awt.Point;
import java.util.Optional;

import model.ReadOnlyReversiModel;

/**
 * An interface that defines a strategy for choosing the next move in a game of Reversi.
 */
public interface ReversiStrategy {

  /**
   * Choose the next move to play for the given read-only reversi model. The move cannot be made
   * on the model, so just return the move.
   * @param model The read-only model to choose a move to play for.
   * @return A point to play the move at.
   * @throws IllegalArgumentException if the given model is null.
   * @throws IllegalStateException if there are no moves to play for the current player.
   */
  Optional<Point> chooseMove(ReadOnlyReversiModel model) throws IllegalArgumentException,
          IllegalStateException;
}
