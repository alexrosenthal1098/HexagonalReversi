package providers.model.board;

import java.util.List;

/**
 * Represents a Readonly Reversi Model.
 */
public interface ReadonlyReversiModel {
  int getSize();

  /**
   * Gets the board.
   *
   * @return the board.
   */
  List<List<Cell>> getBoard();

  /**
   * Needs to check to see if two players have passed there turns in a row.
   * Will fully implement during future assignments.
   *
   * @return whether or not the game is over.
   */
  boolean isGameOver();

  /**
   * Checks if any legal moves are possible on the board for either player.
   *
   * @return whether or not any legal moves are left.
   */
  boolean anyLegalMovesLeft();

  /**
   * Checks if any legal moves on the board are possible for a given player.
   *
   * @param playerColor is the color of the player playing.
   * @return whether or not any legal moves are left for that player.
   */
  boolean ifPlayerHasAnyLegalMoves(String playerColor);

  /**
   * Checks if a given cell and color to turn that cell is a valid move.
   *
   * @param cell        is the cell trying to be moved to
   * @param playerColor is the player color of the current playing player
   * @return whether or not the move is valid
   */
  boolean isValidMove(ICell cell, String playerColor);

  /**
   * Gets the surrounding cells of a given cell.
   *
   * @param cell is the cell in the middle trying to find the surrounding of.
   * @return a list of the surrounding cells.
   */
  List<Cell> getCellsSurrounding(ICell cell);

  /**
   * Gets the upper left cell of a given cell.
   *
   * @param cell is the cell we want to get the upper left of
   * @return the upper left cell
   */
  Cell getUpperLeftCell(ICell cell);

  /**
   * Gets the upper right cell of a given cell.
   *
   * @param cell is the cell we want to get the upper right of
   * @return the upper right cell
   */
  Cell getUpperRightCell(ICell cell);

  /**
   * Gets the right cell of a given cell.
   *
   * @param cell is the cell we want to get the right of
   * @return the right cell
   */
  Cell getRightCell(ICell cell);

  /**
   * Gets the lower right cell of a given cell.
   *
   * @param cell is the cell we want to get the lower right of
   * @return the lower right cell
   */
  Cell getLowerRightCell(ICell cell);

  /**
   * Gets the lower left cell of a given cell.
   *
   * @param cell is the cell we want to get the lower left of
   * @return the lower left cell
   */
  Cell getLowerLeftCell(ICell cell);

  /**
   * Gets the left cell of a given cell.
   *
   * @param cell is the cell we want to get the left of
   * @return the left cell
   */
  Cell getLeftCell(ICell cell);


  /**
   * gets the number of cells that would change color if a move was made at that cell.
   *
   * @param cell        is the cell we want to get the number of cells that
   *                    would change color if a move
   *                    was made at that cell.
   * @param playerColor is the color of the player making the move.
   * @return the number of cells that would change color if a move was made at that cell.
   */
  int howManyCellsAreBeingChangedByMove(ICell cell, String playerColor);

  /**
   * Gets the score of a given player.
   *
   * @param playerColor is the color of the player we want to get the score of.
   * @param board       is the board.
   * @return the score of the player.
   */
  int getScore(List<List<Cell>> board, String playerColor);


  /**
   * Gets the opposite player color.
   *
   * @param playerColor is the color of the player
   * @return the opposite player color
   */
  String oppositePlayerColor(String playerColor);

  /**
   * Gets the color of the current player.
   *
   * @return the color of the current player.
   */
  String getColor();
}
