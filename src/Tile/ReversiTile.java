package Tile;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;

/**
 * A tile of a Reversi game board that can be occupied by a player's disk.
 * A tile is represented by a regular polygon, which is a shape with equal side lengths
 * and equal interior angles.
 * A disk has two colors, one on each side, and can be flipped.
 */
public interface ReversiTile {
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

  /**
   * Returns if this tile has a disk on it or not.
   * @return True if the tile has a disk, false if it does not.
   */
  boolean hasDisk();

  /**
   * Place a disk with given 2 colors onto this tile.
   * @param topColor The color of the top of the disk when it is placed. (the color we see)
   * @param bottomColor The color of the bottom of the disk. (the color we don't see)
   * @throws IllegalArgumentException if either of the given colors are null.
   * @throws IllegalStateException if the tile already has a disk placed on it.
   */
  void placeDisk(Color topColor, Color bottomColor) throws IllegalArgumentException,
          IllegalStateException;

  /**
   * Flips the disk that is on this tile.
   * @throws IllegalStateException if the tile does not have a disk on it.
   */
  void flipDisk() throws IllegalStateException;

  /**
   * Gets the top color of the disk on this tile.
   * @return The color of the disk.
   * @throws IllegalStateException if the disk does not have a tile on it.
   */
  Color getTopColor() throws IllegalStateException;
}
