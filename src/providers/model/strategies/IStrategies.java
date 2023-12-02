package providers.model.strategies;

import java.util.List;

import providers.model.board.Cell;

import providers.model.board.ReversiModel;

/**
 * This interface represents a strategy for the computer player.
 */
public interface IStrategies {

  /**
   * gets the cell for a player that gets them the most amount of cells.
   *
   * @param color is the current player's color.
   * @param model is the model to get the board from
   * @param board is the board to get the corners from
   * @return the cell that is the best move for the player
   */
  Cell strategicMove(ReversiModel model, List<List<Cell>> board, String color);

}
