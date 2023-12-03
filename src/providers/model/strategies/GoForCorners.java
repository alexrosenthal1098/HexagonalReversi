package providers.model.strategies;

import java.util.ArrayList;
import java.util.List;

import providers.model.board.ICell;
import providers.model.board.ReversiModel;

/**
 * This class represents a strategy that goes for corners.
 */
public class GoForCorners implements IStrategies {


  /**
   * gets the cell for a player that gets them towards a corner .
   *
   * @param color is the current player's color.
   * @param model is the model to get the board from
   * @param board is the board to get the corners from
   * @return the cell that is the best move for the player
   */
  @Override
  public ICell strategicMove(ReversiModel model, List<List<ICell>> board, String color) {
    ICell upperLeftMost = null;

    //gets a list of all teh valid moves that the player can make
    List<ICell> validMoves = new ArrayList<>();
    for (int row = 0; row < board.size(); row++) {
      for (int column = 0; column < board.get(row).size(); column++) {
        if (model.isValidMove(board.get(row).get(column), color)) {
          validMoves.add(board.get(row).get(column));
        }
      }
    }

    //removes all the valid moves that are in the list of surrounding cells
    if (validMoves.isEmpty()) {
      return upperLeftMost;
    }

    //gets the sum of teh x and y coords of the center cell
    int centerVal = board.get(model.getSize() - 1).get(model.getSize() - 1).getX()
            + board.get(model.getSize() - 1).get(model.getSize() - 1).getY();
    int maxDiffrence = 0;

    //gets the max diffrence of the x and y coords of the center cell and the valid moves
    for (int i = 0; i < validMoves.size(); i++) {
      int diffrence = Math.abs(validMoves.get(i).getX() + validMoves.get(i).getY() - centerVal);
      if (diffrence > maxDiffrence) {
        maxDiffrence = diffrence;
      }
    }

    //gets a list of all the valid moves that have the max diffrence
    List<ICell> cellsWithMax = new ArrayList<>();
    for (int i = 0; i < validMoves.size(); i++) {
      int diffrence = Math.abs(validMoves.get(i).getX() + validMoves.get(i).getY() - centerVal);
      if (diffrence == maxDiffrence) {
        cellsWithMax.add(validMoves.get(i));
      }
    }

    //gets the upper left most cell from the list of cells with the max diffrence
    if (validMoves.size() > 1) {
      upperLeftMost = validMoves.get(0);
      for (ICell move : validMoves) {
        if (move.getX() <= upperLeftMost.getX() && move.getY() < upperLeftMost.getY()) {
          upperLeftMost = move;
        }
      }
    } else {
      upperLeftMost = validMoves.get(0);
    }

    //returns the upper left most valid cell
    return upperLeftMost;

  }
}