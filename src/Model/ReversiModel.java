package Model;

import Player.ReversiPlayer;
import Tile.ReversiTile;

import java.util.Arrays;

/**
 * An interface that defines the methods necessary to play the game Reversi.
 * The game can be played using regular polygon as a tile and any board arrangement
 * that can be represented using a two-dimensional array.
 */
public interface ReversiModel {
  /**
   * Make the current player move at the tile given by its row and column number.
   * @param row The row to move at.
   * @param col The column to move at.
   * @throws IllegalArgumentException if the given row or column number are out of bounds,
   *                                  i.e. too small or too large.
   * @throws IllegalStateException if the current player cannot make a move on that tile.
   */
  void moveAt(int row, int col) throws IllegalArgumentException, IllegalStateException;

  /**
   * Pass the current player's turn to the other player.
   */
  void passTurn();

  /**
   * Determines whether the player whose move it currently is can make a move at
   * the tile given by its row and column number.
   * @param row The row to look at.
   * @param col The column to look at.
   * @return True if the move is possible, false if it is not.
   * @throws IllegalArgumentException if the given row or column number are out of bounds,
   *                                  i.e. too small or too large.
   */
  boolean isMovePossible(int row, int col) throws IllegalArgumentException;

  /**
   * Determines if the current player has any legal moves to play.
   * @return True if there are moves left, false if there aren't.
   */
  boolean anyMoves();

  /**
   * Determines if the game is over (both players have run out of legal moves).
   * @return True if the game is over, false if it is not.
   */
  boolean isGameOver();

  /**
   * Gets the score of player 1, the player who went first and uses the black discs.
   * @return An int representing the score.
   */
  int getPlayer1Score();

  /**
   * Gets the score of player 1, the player who went second and uses the white discs.
   * @return An int representing the score.
   */
  int getPlayer2Score();

  /**
   * Returns the player whose turn it currently is.
   * @return A ReversiPlayer whose turn it is.
   */
  ReversiPlayer getCurrentPlayer();

  /**
   * Returns the player that occupies the tile at the given row and column.
   * @param row The row to look at.
   * @param col The column to look at.
   * @return The ReversiPlayer that occupies the tile.
   * @throws IllegalArgumentException if the given row or column number are out of bounds,
   *                                  i.e. too small or too large.
   */
  ReversiPlayer getPlayerAt(int row, int col) throws IllegalArgumentException;

  /**
   * Returns the two-dimensional array of tiles that represents the game board.
   * @return A 2D array of Reversi tiles.
   */
  ReversiTile[][] getTiles();
}
