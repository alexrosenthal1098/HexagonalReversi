package Tile;

import java.awt.*;


/**
 * A class that represents a pointy-top shaped hexagon tile for the
 * game Reversi. The position of this tile is given by its axial coordinates.
 */
public class PointyTopHexagon implements ReversiTile {
  private boolean occupied;
  private Color discColor;

  /**
   * An empty constructor for a PointyTopHexagon that is not occupied.
   * Used to initialize the state of the board in a Reversi game before
   *  players have made any moves.
   */
  public PointyTopHexagon() {
    this.occupied = false;
  }

  /**
   * A copy constructor that creates a hexagonal tile with the given tiles disc.
   * @param tile The tile to copy.
   */
  public PointyTopHexagon(ReversiTile tile) {
    if (tile == null) { // check if the given tile is null
      throw new IllegalArgumentException("Cannot copy a null tile."); // if it is, throw exception
    }
    this.occupied = tile.isOccupied(); // copy the occupied field
    if (this.occupied) { // if this tile is occupied
      this.discColor = tile.getDiscColor(); // copy the disc color
    }
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
  public void changeDiscColor(Color color) {
    if (color == null) { // check if the color is null
      // throw exception if it is
      throw new IllegalArgumentException("Cannot change to null color.");
    }
    this.discColor = color; // set this color to the given one
    this.occupied = true; // set the occupied state to true
  }

  @Override
  public boolean isOccupied() {
    return this.occupied; // simply return if this tile is occupied or not
  }

  @Override
  public Color getDiscColor() throws IllegalStateException {
    if (!this.occupied) { // if this tile is not occupied
      throw new IllegalStateException("This tile is not occupied."); // throw an error
    }
    return new Color(this.discColor.getRGB()); // return a copy of the disc color
  }

}
