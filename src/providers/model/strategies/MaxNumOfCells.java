package providers.model.strategies;

import java.util.ArrayList;
import java.util.List;

import providers.model.board.ICell;
import providers.model.board.ReversiModel;

/**
 * This class represents a strategy that goes for max num of cells.
 */
public class MaxNumOfCells implements IStrategies {
  private int passes = 0;


  /**
   * gets the cell for a player that gets them the most amount of cells.
   *
   * @param color is the current player's color.
   * @param model is the model to get the board from
   * @param board is the board to get the corners from
   * @return the cell that is the best move for the player
   */
  @Override
  public ICell strategicMove(ReversiModel model, List<List<ICell>> board, String color) {
    ICell biggest = null;
    int max = 1;

    //gets a the max number of cells that can be flipped by a move
    for (int row = 0; row < board.size(); row++) {
      for (int column = 0; column < board.get(row).size(); column++) {
        if (model.howManyCellsAreBeingChangedByMove(board.get(row).get(column), color) > max) {
          max = model.howManyCellsAreBeingChangedByMove(board.get(row).get(column), color);
        }
      }
    }

    //gets a list of all the cells that have the max number of cells that can be flipped by a move
    List<ICell> cellsWithMax = new ArrayList<>();
    for (int row = 0; row < board.size(); row++) {
      for (int column = 0; column < board.get(row).size(); column++) {
        if (model.howManyCellsAreBeingChangedByMove(board.get(row).get(column), color) == max) {
          cellsWithMax.add(board.get(row).get(column));
        }
      }
    }

    //gets the cell that is the upperleft most from the list of cells with max number of
    // cells that can be flipped by a move
    if (cellsWithMax.size() > 1) {
      biggest = cellsWithMax.get(0);
      for (int i = 0; i < cellsWithMax.size(); i++) {
        if (cellsWithMax.get(i).getX() <= biggest.getX() &&
                cellsWithMax.get(i).getY() < biggest.getY()) {
          biggest = cellsWithMax.get(i);
        }
      }
    } else {
      biggest = cellsWithMax.get(0);
    }


    //if the player has passed twice in a row then the game is over
    return biggest;
  }


}
