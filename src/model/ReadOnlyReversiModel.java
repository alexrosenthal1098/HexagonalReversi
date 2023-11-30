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
   * @throws IllegalStateException if the game has not started.
   */
  boolean isMovePossible(int x, int y) throws IllegalArgumentException, IllegalStateException;

  /**
   * Determines if the current player has any legal moves to play.
   * @return True if there are moves left, false if there aren't.
   * @throws IllegalStateException if the game has not started.
   */
  boolean anyMoves() throws IllegalStateException;

  /**
   * Determines if the game is over (both players have run out of legal moves).
   * @return True if the game is over, false if it is not.
   * @throws IllegalStateException if the game has not started.
   */
  boolean isGameOver() throws IllegalStateException;

  /**
   * Gets the score of the current player. Score is the number of disks of their color that are
   * on the board
   * @return An int representing the score.
   * @throws IllegalStateException if the game has not started.
   */
  int getCurrentPlayerScore() throws IllegalStateException;

  /**
   * Gets the score of the player who is not currently moving. Score is the number of disks of
   * their color that are on the board
   * @return An int representing the score.
   * @throws IllegalStateException if the game has not started.
   */
  int getOtherPlayerScore() throws IllegalStateException;

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
   * Creates and returns a mutable copy of this model.
   * @return A mutable copy of the model where the game has not yet started.
   */
  ReversiModel copyModel();

  /**
   * Add a listener to this model that can only view it as read-only.
   * @param listener The listener of this read-only model.
   * @throws IllegalArgumentException if the given listener is null.
   */
  void addReadOnlyListener(ModelListener listener) throws IllegalArgumentException;
}
