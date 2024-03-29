package model.tile;

import java.awt.Color;

/**
 * A tile on a Reversi game board that can be occupied by a disk with one color on each side.
 * A tile is represented by a regular polygon, which is a shape with equal side lengths
 * and equal interior angles. (ex. equilateral triangle, square, regular pentagon, etc.)
 */
public interface ReversiTile {
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
