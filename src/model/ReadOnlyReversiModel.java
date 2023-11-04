package model;

import java.awt.Point;
import java.awt.Color;
import java.util.Map;

import model.tile.ReversiTile;

/**
 * An interface that defines only the methods that can view the state of a
 * {@link model.ReversiModel} and not the methods that mutate the model.
 * This prevents view classes from mutating the model.
 */
public interface ReadOnlyReversiModel {
  /**
   * Determines whether the player whose move it currently is can make a move at
   * the tile given by its x and y position.
   * @param x The x position to look at.
   * @param y The y position to look at.
   * @return True if the move is possible, false if it is not.
   * @throws IllegalArgumentException if the given x or y position are outside
   *                                  the bounds of the HexagonalBoard.
   */
  boolean isMovePossible(int x, int y) throws IllegalArgumentException;

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
   * Returns the color of the player whose turn it currently is.
   * @return The color of the current player.
   */
  Color getCurrentPlayer();

  /**
   * Returns the color of the disk that occupies the tile at the given x and y position.
   * @param x The x position to look at.
   * @param y The y position to look at.
   * @return The color of the disk that occupies the tile.
   * @throws IllegalArgumentException if the given x or y position are outside
   *                                  the bounds of the HexagonalBoard.
   * @throws IllegalStateException if the tile at the given position is not occupied.
   */
  Color getColorAt(int x, int y) throws IllegalArgumentException, IllegalStateException;

  /**
   * Returns a map of points to tiles that represents the game board.
   * @return A map of Point to ReversiTile.
   */
  Map<Point, ReversiTile> getTiles();
}
