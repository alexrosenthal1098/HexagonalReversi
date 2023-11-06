package model.tile;

import java.awt.Color;


/**
 * A class that represents a pointy-top shaped hexagonal tile for the
 * game Reversi. The tile can have a disk placed on it with one color on each side.
 */
public class PointyTopHexagon implements ReversiTile {
  private boolean hasDisk;
  private Color topColor;
  private Color bottomColor;

  /**
   * An empty constructor for a PointyTopHexagon that is not occupied.
   * Used to initialize the state of the HexagonalBoard in a Reversi game before
   *  players have made any moves.
   */
  public PointyTopHexagon() {
    this.hasDisk = false;
  }

  /**
   * A constructor that creates a hexagonal tile with a disk with the given top and bottom color
   * @param topColor the top color of the disk on this tile.
   * @param bottomColor the bottom color of the disk on this tile.
   */
  public PointyTopHexagon(Color topColor, Color bottomColor) {
    if (topColor == null || bottomColor == null) { // check if either color is null
      // if either is null, throw exception
      throw new IllegalArgumentException("Cannot create a tile with a null color disk.");
    }
    this.hasDisk = true; // set hasDisk to true
    this.topColor = new Color(topColor.getRGB()); // copy the top color
    this.bottomColor = new Color(bottomColor.getRGB()); // copy the bottom color
  }

  @Override
  public boolean hasDisk() {
    return this.hasDisk;
  }

  @Override
  public void placeDisk(Color topColor, Color bottomColor) throws IllegalArgumentException,
          IllegalStateException {
    if (topColor == null || bottomColor == null) { // if either color is null
      throw new IllegalArgumentException("A disk cannot have null colors."); // throw exception
    }
    if (this.hasDisk) { // if this tile already has a disk
      throw new IllegalStateException("This tile already has a disk."); // throw exception
    }
    this.topColor = new Color(topColor.getRGB()); // copy and set the top color
    this.bottomColor = new Color(bottomColor.getRGB()); // copy and set the bottom color
    this.hasDisk = true; // set has disk field to true
  }

  @Override
  public void flipDisk() throws IllegalStateException {
    if (!this.hasDisk) { // if this tile has no disk
      throw new IllegalStateException("This tile has no disk to flip."); // throw exception
    }
    Color topColorCopy = new Color(this.topColor.getRGB()); // copy the top color
    this.topColor = new Color(bottomColor.getRGB()); // set top color to copy of bottom color
    // this avoids an aliasing bug
    this.bottomColor = topColorCopy; // set the bottom color to the copy of the top color
  }

  @Override
  public Color getTopColor() throws IllegalStateException {
    if (!this.hasDisk) { // if this tile has no disk
      throw new IllegalStateException("This tile has no disk to flip."); // throw exception
    }
    return new Color(this.topColor.getRGB()); // return a copy of the top color
  }
}
