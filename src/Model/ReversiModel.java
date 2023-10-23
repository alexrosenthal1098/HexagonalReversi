package Model;

import java.awt.*;
import java.util.Map;

import Player.ReversiPlayer;
import Tile.ReversiTile;


/**
 * An interface that defines the methods necessary to play the game Reversi.
 * The game can be played using regular polygon as a tile and any board arrangement
 * that can be represented using a two-dimensional array.
 */
public interface ReversiModel {
  /**
   * Make the current player move at the tile given by its x and y position.
   * @param x The x position to move at.
   * @param y The y position to move at.
   * @throws IllegalArgumentException if the given x or y position are out of bounds,
   *                                  i.e. too small or too large.
   * @throws IllegalStateException if the current player cannot make a move on that tile.
   */
  void moveAt(int x, int y) throws IllegalArgumentException, IllegalStateException;

  /**
   * Pass the current player's turn to the other player.
   */
  void passTurn();

  /**
   * Determines whether the player whose move it currently is can make a move at
   * the tile given by its x and y position.
   * @param x The x position to look at.
   * @param y The y position to look at.
   * @return True if the move is possible, false if it is not.
   * @throws IllegalArgumentException if the given x or y position are out of bounds,
   *                                  i.e. too small or too large.
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
   * Returns the player whose turn it currently is.
   * @return A ReversiPlayer whose turn it is.
   */
  ReversiPlayer getCurrentPlayer();

  /**
   * Returns the color of the disk that occupies the tile at the given x and y position,
   * or null if there is no disk at that position.
   * @param x The x position to look at.
   * @param y The y position to look at.
   * @return The color of the disk that occupies the tile, or null.
   * @throws IllegalArgumentException if the given x or y position are out of bounds,
   *                                  i.e. too small or too large.
   */
  Color getColorAt(int x, int y) throws IllegalArgumentException;

  /**
   * Returns a map of points to tiles that represents the game board.
   * @return A map of Point to ReversiTile.
   */
  Map<Point, ReversiTile> getTiles();

  /**
   * Returns a map of tiles to colors that represents the color disc at each tile.
   * @return A map of ReversiTile to Color.
   */
  Map<ReversiTile, Color> getTileColors();
}
