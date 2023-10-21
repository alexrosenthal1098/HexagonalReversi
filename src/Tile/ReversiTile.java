package Tile;

import java.awt.*;

import Player.ReversiPlayer;

/**
 * A tile of a Reversi game board that can be occupied by players of the game.
 * A tile is represented by a regular polygon, which is a shape with equal side lengths
 * and equal interior angles. Can be rendered both textually and visually
 * using java's GUI framework Swing.
 */
public interface ReversiTile {
  /**
   * Changes the player who occupies this tile to the given player.
   * @param player A ReversiPlayer to occupy this tile.
   * @throws IllegalArgumentException if the given player is null.
   */
  void changePlayer(ReversiPlayer player) throws IllegalArgumentException;

  /**
   * Returns a regular polygon with the given side length that is
   * centered around the given point. This polygon represents the shape of the tile.
   * @param center A point that represents the center of this tile.
   * @param sideLength The side length of this shape, in pixels.
   * @return The polygon that represents this tile.
   * @throws IllegalArgumentException If the center point is null or the
   *                                  side length is non-positive.
   */
  Polygon buildTile(Point center, int sideLength) throws IllegalArgumentException;

  @Override
  String toString();

  @Override
  boolean equals(Object other);

  @Override
  int hashCode();
}
