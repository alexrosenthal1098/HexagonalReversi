package providers.model.strategies;

import java.util.ArrayList;
import java.util.List;

import providers.model.board.Cell;
import providers.model.board.ReversiModel;

/**
 * This class represents a strategy that goes for max num of cells.
 */
public class TryStrat implements IStrategies {
  IStrategies maxNumOfCells = new MaxNumOfCells();
  IStrategies avoidCellsSurroundingCorners = new AvoidCellsSurroundingCorners();
  IStrategies goForCorners = new GoForCorners();
  IStrategies miniMax = new MiniMax();

  int maxDiff;
  List<Cell> maxDiffCells;


  /**
   * gets the cell for a player that gets them the most amount of cells.
   * @param model is the model to get the board from
   * @param board is the board to get the corners from
   * @param color is the current player's color.
   * @return
   */
  @Override
  public Cell strategicMove(ReversiModel model, List<List<Cell>> board, String color) {

    //gets the cells that each diffrent type of strtegy would make
    Cell biggest = new Cell(board.size() * 100, board.size() * 100);
    Cell maxCell = maxNumOfCells.strategicMove(model, board, color);
    Cell avoidNeighborsCell = avoidCellsSurroundingCorners.strategicMove(model, board, color);
    Cell cornersCell = goForCorners.strategicMove(model, board, color);
    Cell miniMaxCell = miniMax.strategicMove(model, board, color);

    //puts all the cells into a list
    List<Cell> cells = new ArrayList<Cell>();
    cells.add(maxCell);
    cells.add(avoidNeighborsCell);
    cells.add(cornersCell);
    cells.add(miniMaxCell);


    maxDiff = Integer.MIN_VALUE;
    maxDiffCells = new ArrayList<Cell>();

    //gets the max diffrence of the scores of the player and the opponent would make
    // with each type of move from the diffrent strategies
    for (int i = 0; i < cells.size(); i++) {
      List<List<Cell>> tempBoard = new ArrayList<>();
      for (int row = 0; row < board.size(); row++) {
        tempBoard.add(new ArrayList<Cell>());
        for (int column = 0; column < board.get(row).size(); column++) {
          Cell cell = new Cell();
          cell.changeX(board.get(row).get(column).getX());
          cell.changeY(board.get(row).get(column).getY());
          cell.changeFill(board.get(row).get(column).getFill());
          tempBoard.get(row).add(cell);
        }
      }
      int moveX = cells.get(i).getX();
      int moveY = cells.get(i).getY();
      tempBoard.get(moveY).get(moveX).setColor(color);
      int playerScore = model.getScore(tempBoard, color);
      int opponentScore = model.getScore(tempBoard, model.oppositePlayerColor(color));
      int diff = playerScore - opponentScore;
      if (diff > maxDiff) {
        maxDiff = diff;
        maxDiffCells.clear();
        maxDiffCells.add(cells.get(i));
      }
      if (diff == maxDiff) {
        maxDiffCells.add(cells.get(i));
      }
    }

    //gets the cell that is the upperleft most from the list of cells with max diffrence
    if (maxDiffCells.size() > 1) {
      for (int i = 0; i < maxDiffCells.size(); i++) {
        if (maxDiffCells.get(i).getX() <= biggest.getX() &&
                maxDiffCells.get(i).getY() < biggest.getY()) {
          biggest = maxDiffCells.get(i);
        }
      }
    } else {
      biggest = maxDiffCells.get(0);
    }

    //return the upperleftmost of the cells with the max diffrence
    return biggest;
  }
}
