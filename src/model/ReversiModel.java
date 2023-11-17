package model;


/**
 * An interface that defines the methods necessary to play the game Reversi.
 * The game can be played using any regular polygon as a tile and any board arrangement
 * that can be represented using two-dimensional coordinates.
 */
public interface ReversiModel extends ReadOnlyReversiModel {
  /**
   * Make the current player move at the tile given by its x and y position, and
   * move the turn to the other player.
   * @param x The x position to move at.
   * @param y The y position to move at.
   * @throws IllegalArgumentException if the given x or y position are outside
   *                                  the bounds of the board.
   * @throws IllegalStateException if the game has not started or if the current player cannot
   *                               make a move on that tile.
   */
  void moveAt(int x, int y) throws IllegalArgumentException, IllegalStateException;

  /**
   * Pass the current player's turn to the other player.
   * @throws IllegalStateException if the game has not sarted.
   */
  void passTurn() throws IllegalStateException;

  /**
   * Register the given listener to this model so that it receives event notifications from
   * this model. The listener can register as either the first or second player using the
   * boolean parameter. A listener can only register BEFORE the game starts.
   * @param listener The class that listens to this model.
   * @param firstPlayer A boolean representing which player to listen to. True for player one
   *                    (the one that moves first), false for player two.
   * @throws IllegalArgumentException if the given listener is null.
   * @throws IllegalStateException if the game has already started.
   */
  void addListener(ModelListener listener, boolean firstPlayer) throws IllegalArgumentException,
          IllegalStateException;

  /**
   * Starts the game, notifies listeners of the first player that their turn has started.
   * @throws IllegalStateException if the game has already started.
   */
  void startGame() throws IllegalStateException;
}
