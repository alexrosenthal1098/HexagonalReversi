package strategy;

import java.awt.Point;

import model.ReadOnlyReversiModel;
import model.ReversiModel;

/**
 * An interface that defines a strategy for choosing the next move in a game of Reversi.
 */
public interface ReversiStrategy {

  /**
   * Choose the next move to play for the given model. Do not make the move on the model, simply
   * return a move to play.
   * @param model The read only model to choose a move to play for.
   * @return A point to play the move at.
   * @throws IllegalArgumentException if the given model is null.
   * @throws IllegalStateException if there are no moves to play for the current player.
   */
  Point choseMove(ReadOnlyReversiModel model) throws IllegalArgumentException,
          IllegalStateException;
}
