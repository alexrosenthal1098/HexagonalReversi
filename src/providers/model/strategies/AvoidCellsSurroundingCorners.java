package providers.model.strategies;

import java.util.ArrayList;
import java.util.List;

import providers.model.board.ICell;
import providers.model.board.ReversiModel;

/**
 * This class represents a strategy that avoids cells surrounding corners.
 */
public class AvoidCellsSurroundingCorners implements IStrategies {


  /**
   * gets the cell for a player that gets them away for the cells suroding the corners.
   *
   * @param color is the current player's color.
   * @param model is the model to get the board from
   * @param board is the board to get the corners from
   * @return the cell that is the best move for the player
   */
  @Override
  public ICell strategicMove(ReversiModel model, List<List<ICell>> board, String color) {
    ICell upperLeftMost = null;

    //gets a list of all teh surronding cells n the board
    List<ICell> surroundingCorners = new ArrayList<>();
    surroundingCorners.addAll(model.getCellsSurrounding(board.get(0).get(0)));
    surroundingCorners.addAll(model.getCellsSurrounding(board.get(0).get(board.get(0).size() - 1)));
    surroundingCorners.addAll(model.getCellsSurrounding(board.get(model.getSize() - 1).get(0)));
    surroundingCorners.addAll(model.getCellsSurrounding(board.get(model.getSize() - 1).get(board.
            get(model.getSize() - 1).size() - 1)));
    surroundingCorners.addAll(model.getCellsSurrounding(board.get(board.size() - 1).get(0)));
    surroundingCorners.addAll(model.getCellsSurrounding(board.get(board.size() - 1).get(board.
            get(board.size() - 1).size() - 1)));


    //gets a list of all teh valid moves that the player can make
    List<ICell> validMoves = new ArrayList<>();
    for (List<ICell> cells : board) {
      for (ICell cell : cells) {
        if (model.isValidMove(cell, color)) {
          validMoves.add(cell);
        }
      }
    }

    //removes all the valid moves that are in the list of surrounding cells
    if (validMoves.size() > 1) {
      List<ICell> validMovesCopy = new ArrayList<>(validMoves);
      for (int i = validMovesCopy.size() - 1; i >= 0; i--) {
        for (int j = surroundingCorners.size() - 1; j >= 0; j--) {
          if (validMovesCopy.get(i).equals(surroundingCorners.get(j))) {
            if (validMoves.size() > 1) {
              validMoves.remove(i);
            }
          }
        }
      }
    }

    //if there are no valid moves left then return the upper left most cell
    if (validMoves.isEmpty()) {
      return upperLeftMost;
    }

    //gets the upperleft most value of teh valid moves
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
