package providers.view;

import java.util.List;

import providers.controller.ViewListener;
import providers.model.board.Cell;
import providers.model.board.ICell;

/**
 * Interface for different representations of the view.
 */
public interface IView {

  /**
   * Make the view visible. This is usually called,
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Transmit an error message to the view, in case,
   * the command could not be processed correctly.
   *
   * @param error is the error message
   */
  void showErrorMessage(String error);

  /**
   * Signal the view to draw itself.
   */
  void refresh();

  /**
   * Provide the view with the board to be drawn.
   *
   * @param board is the board
   */
  void setBoard(List<List<Cell>> board);


  /**
   * Add a listener to the view.
   *
   * @param vl is the listener
   */
  void addListener(ViewListener vl);


  /**
   * Notify the listener of a cell.
   *
   * @param cell is the cell
   */
  void notifyListener(ICell cell);
}
