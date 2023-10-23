package Tile;

import java.awt.*;
import java.util.Objects;


/**
 * A class that represents a pointy-top shaped hexagon tile for the
 * game Reversi. The position of this tile is given by its axial coordinates.
 */
public class PointyTopHexagon implements ReversiTile {
  // q and r come from the axial coordinate system described
  // in the website linked on the instructions page
  private final int q; // the q coordinate of this tile
  private final int r; // the r coordinate of this tile

  /**
   * A constructor for a PointyTopHexagon that receives a point. Used to initialize the state
   * of the board in a Reversi game before players have made moves.
   */
  public PointyTopHexagon(Point coordinates) {
    if (coordinates == null) {
      throw new IllegalArgumentException("Cannot create a tile with null coordinates.");
    }
    this.q = coordinates.x;
    this.r = coordinates.y;
  }

  /**
   * A constructor that creates a copy of the given PointyTopHexagon.
   * @param hex The hexagon to copy.
   */
  public PointyTopHexagon(PointyTopHexagon hex) {
    this.q = hex.getCoordinates().x; //
    this.r = hex.getCoordinates().y;
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
  public Point getCoordinates() {
    return new Point(this.q, this.r); // create a copy of the coordinates to avoid mutation
  }
}
