package Tile;

import java.awt.*;

import javax.swing.text.Position;

import Player.ReversiPlayer;

/**
 * A tile of a Reversi game board that can be occupied by a player's disk.
 * A tile is represented by a regular polygon, which is a shape with equal side lengths
 * and equal interior angles. Can be rendered both textually and visually
 * using java's GUI framework Swing.
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
   * Changes the color of the disc on this tile to the given color.
   * @param color The color to change to.
   * @throws IllegalArgumentException if the given color is null.
   */
  void changeDiscColor(Color color) throws IllegalArgumentException;

  /**
   * Returns if this tile is occupied by a player's disc or not.
   * @return True if the tile is occupied, false if it isn't.
   */
  boolean isOccupied();

  /**
   * Returns the color of the disc that occupies this hexagon.
   * @return The color of the disc on this tile.
   * @throws IllegalStateException if there is no disc on this tile.
   */
  Color getDiscColor() throws IllegalStateException;
}
