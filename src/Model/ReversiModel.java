package Model;

import java.awt.Point;
import java.awt.Color;
import java.util.Map;

import Tile.ReversiTile;


/**
 * An interface that defines the methods necessary to play the game Reversi.
 * The game can be played using any regular polygon as a tile and any board arrangement
 * that can be represented using two-dimensional coordinates.
 */
public interface ReversiModel extends ViewReversiModel{
  /**
   * Make the current player move at the tile given by its x and y position, and
   * move the turn to the other player.
   * @param x The x position to move at.
   * @param y The y position to move at.
   * @throws IllegalArgumentException if the given x or y position are outside
   *                                  the bounds of the board.
   * @throws IllegalStateException if the current player cannot make a move on that tile.
   */
  void moveAt(int x, int y) throws IllegalArgumentException, IllegalStateException;

  /**
   * Pass the current player's turn to the other player.
   */
  void passTurn();
}
