package Tile;

import java.awt.*;
import java.util.Objects;


/**
 * A class that represents a pointy-top shaped hexagon tile for the
 * game Reversi.
 */
public class PointyTopHexagon implements ReversiTile {
  private final int q; // the
  private final int r;
  private Color color; // the color disk that currently occupies this tile
  // if this field is null, then there is no disk on this tile

  /**
   * A constructor for a PointyTopHexagon that receives a point. Used to initialize the state
   * of the board in a Reversi game before players have made moves.
   */
  public PointyTopHexagon(Point coordinates) {
    this.q = coordinates.x;
    this.r = coordinates.y;
  }

  /**
   * A constructor that creates a copy of the given PointyTopHexagon.
   * @param hex The object to copy.
   */
  public PointyTopHexagon(PointyTopHexagon hex) {
    this.q = hex.getCoordinates().x;
    this.r = hex.getCoordinates().y;
    this.color = hex.getDiscColor();
  }

  @Override
  public Color getDiscColor() {
    if (this.color == null) {
      return null;
    }
    return new Color(this.color.getRGB()); // return a copy of the color
  }

  @Override
  public Polygon buildTile(Point center, int sideLength) throws IllegalArgumentException {
    // TODO: add 6 points to a polygon to make hexagon with the given side length
    // ensure the center point is not null and side length is positive
    if (center == null || sideLength <= 0) {
      throw new IllegalArgumentException("Center cannot be null and side length must be positive");
    }
    return new Polygon();
  }

  @Override
  public void changeColor(Color color) {
    // ensure given color is not null and set this color
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public Point getCoordinates() {
    return new Point(this.q, this.r);
  }

  @Override
  public String toString() {
    if (this.color == null) { // if this tile is unoccupied (color is null)
      return "_";
    }
    else if (this.color.equals(Color.BLACK)) { // if the color is black
      return "X";
    }
    else if (this.color.equals(Color.WHITE)) { // if the color is white
      return "O";
    }
    else { // if the color is not null, black, or white, throw an exception
      throw new IllegalStateException("The color that occupies this tile is unrecognized");
    }
  }
}
