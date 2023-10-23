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
   * Get the color of the disc that occupies this tile,
   * or a completely transparent Color if the tile is empty.
   * @return The color of the disk.
   */
  Color getDiscColor();

  /**
   * Changes the color of the disk that occupies this tile.
   * @param color A color of disk that occupies this tile.
   * @throws IllegalArgumentException if the given color is null.
   */
  void changeColor(Color color) throws IllegalArgumentException;

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
   * Returns the coordinates of this hexagon's position on the board.
   * @return A Point representing the hex's coordinates on the board.
   */
  Point getCoordinates();

  @Override
  String toString();
}
