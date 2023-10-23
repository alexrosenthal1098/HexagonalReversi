package Tile;

import java.awt.*;


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
    this.q = coordinates.x; // get the x coordinate from the point and set q field
    this.r = coordinates.y; // get the y coordinate from the point and set r field
  }

  /**
   * A constructor that creates a copy of the given PointyTopHexagon.
   * @param hex The hexagon to copy.
   */
  public PointyTopHexagon(PointyTopHexagon hex) {
    this.q = hex.getCoordinates().x; // get the x coordinate of the given hexagon and set q field
    this.r = hex.getCoordinates().y; // get the y coordinate of the given hexagon and set r field
  }

  @Override
  public Polygon buildTile(Point center, int sideLength) throws IllegalArgumentException {
    // ensure the center point is not null and side length is positive
    if (center == null || sideLength <= 0) {
      throw new IllegalArgumentException("Center cannot be null and side length must be positive");
    }

    Polygon hexagon = new Polygon(); // create a new polygon

    for (int sideNum = 0; sideNum < 6; sideNum++) { // iterate over the number of sides (6)
      // calculate x and y position (math was found on the internet)
      int xPos = (int) (center.x + sideLength * Math.cos(sideNum * 2 * Math.PI / 6));
      int yPos = (int) (center.y + sideLength * Math.sin(sideNum * 2 * Math.PI / 6));
      hexagon.addPoint(xPos, yPos); // add the point for each side to the polygon
    }

    return hexagon; // return the polygon
  }

  @Override
  public Point getCoordinates() {
    return new Point(this.q, this.r); // create a copy of the coordinates to avoid mutation
  }
}
