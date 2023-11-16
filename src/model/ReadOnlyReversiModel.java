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
   *                                  the bounds of the board.
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
   * Gets the score of the current player. Score is the number of disks of their color that are
   * on the board
   * @return An int representing the score.
   */
  int getCurrentPlayerScore();

  /**
   * Gets the score of the player who is not currently moving. Score is the number of disks of
   * their color that are on the board
   * @return An int representing the score.
   */
  int getOtherPlayerScore();

  /**
   * Returns the color of the player whose turn it currently is.
   * @return The color of the current player.
   */
  Color currentPlayerColor();

  /**
   * Returns the color of the player whose turn it currently is NOT.
   * @return The color of the other player.
   */
  Color otherPlayerColor();

  /**
   * Returns the tile on the board at the given x and y position.
   * @param x The x position to look at.
   * @param y The y position to look at.
   * @return The ReversiTile at that location
   * @throws IllegalArgumentException if the given x or y position are outside
   *                                  the bounds of the board.
   */
  ReversiTile getTileAt(int x, int y) throws IllegalArgumentException;

  /**
   * Returns a map of points to tiles that represents the game board.
   * @return A map of Point to ReversiTile.
   */
  Map<Point, ReversiTile> getTiles();

  /**
   * Register the given listener to this model so that it receives event notifications from
   * this model. The listener can register as either the first or second player using the
   * boolean parameter.
   * @param listener The class that listens to this model.
   * @param firstPlayer A boolean representing which player to listen to. True for player one
   *                    (the one that moves first), false for player two.
   */
  void addListener(ModelListener listener, boolean firstPlayer);
}
