package providers.model.strategies;

import java.util.ArrayList;
import java.util.List;

import providers.model.board.ICell;
import providers.model.board.ReversiModel;

/**
 * This class represents a strategy that goes for max num of cells.
 */
public class MiniMax implements IStrategies {
  int numStrat1 = 0;
  int numStrat2 = 0;
  int numStrat3 = 0;

  List<List<ICell>> pastBoard = new ArrayList<List<ICell>>();

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
    //needs to access opponent's past moves and try to guess what strategy they are using
    //then make a decision that is best based on minimizing ur opponent's moves

    //if the board is empty, then just use the max num of cells strategy
    if (pastBoard.isEmpty()) {
      pastBoard = board;
      return new MaxNumOfCells().strategicMove(model, board, color);
    }

    //getting the diffrent cells that the thre strategies would
    // make to see what startegty the oponent is using
    ICell moveIfStrat1;
    ICell moveIfStrat2;
    ICell moveIfStrat3;
    moveIfStrat1 = new MaxNumOfCells().strategicMove(model,
            pastBoard, model.oppositePlayerColor(color));
    moveIfStrat2 = new AvoidCellsSurroundingCorners().
            strategicMove(model, pastBoard, model.oppositePlayerColor(color));
    moveIfStrat3 = new GoForCorners().strategicMove(model,
            pastBoard, model.oppositePlayerColor(color));

    //counts how many of each move the opponent makes that represnets the stratgies
    // that are available to see what pattern they are using
    for (int row = 0; row < board.size(); row++) {
      for (int column = 0; column < board.get(row).size(); column++) {
        if (this.pastBoard.get(row).get(column) != board.get(row).get(column)) {
          if (board.get(row).get(column) == moveIfStrat1) {
            numStrat1++;
            break;
          }
          if (board.get(row).get(column) == moveIfStrat2) {
            numStrat2++;
            break;
          }
          if (board.get(row).get(column) == moveIfStrat3) {
            numStrat3++;
            break;
          }
        }
      }
    }

    // making move based on which one is highest
    if (numStrat1 >= numStrat2 && numStrat1 >= numStrat3) {
      ICell cell = new MaxNumOfCells().strategicMove(model, board, model.oppositePlayerColor(color));
      if (model.isValidMove(cell, color)) {
        return cell;
      }
      return new MaxNumOfCells().strategicMove(model, board, color);
    } else if (numStrat2 > numStrat1 && numStrat2 > numStrat3) {
      ICell cell = new AvoidCellsSurroundingCorners().strategicMove(model, board,
              model.oppositePlayerColor(color));
      if (model.isValidMove(cell, color)) {
        return cell;
      }
      return new MaxNumOfCells().strategicMove(model, board, color);
    } else if (numStrat3 > numStrat2) {
      ICell cell = new GoForCorners().strategicMove(model, board, model.oppositePlayerColor(color));
      if (model.isValidMove(cell, color)) {
        return cell;
      }
      return new MaxNumOfCells().strategicMove(model, board, color);
    }

    pastBoard = board;

    //if the opponent is not using any of the strategies then just use the max num of cells strategy
    return new MaxNumOfCells().strategicMove(model, board, color);
  }
}
