package strategy;

import java.awt.Point;

import model.ReversiModel;

/**
 * An interface that defines a strategy for choosing the next move in a game of Reversi.
 */
public interface ReversiStrategy {

  /**
   * Chose the next move to play for the given model.
   * @param model The model to choose a move to play for.
   * @return A point to play the move at.
   */
  Point choseMove(ReversiModel model);
}
