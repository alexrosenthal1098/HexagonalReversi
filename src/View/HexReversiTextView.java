package View;

import java.awt.*;
import java.util.Objects;

import Model.ReversiModel;
import Tile.ReversiTile;

public class HexReversiTextView implements TextView {
  private final ReversiModel model;

  public HexReversiTextView(ReversiModel model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public String toString() {
    int sideLength = this.getModelSideLength(); // get the side length of this models hex grid
    // initialize a StringBuilder that represents the hex map.
    StringBuilder mapString = new StringBuilder();

    // iterate over all r values that occur in the hex map
    for (int r = -sideLength + 1; r <= sideLength - 1; r++) {
      // iterate over q values, ensuring that they start and end in a location that ensures a
      // correct number of total columns (this math was derived by looking at the formula to
      // generate the HashMap)
      for (int q = Math.min(-sideLength + 1, -sideLength + 1 - r);
           q <= Math.max(sideLength - 1, sideLength - 1 - r); q++) {
        // Get the tile at the current q, r from the map or null if there isn't one there
        ReversiTile tile = this.model.getTiles().get(new Point(q, r));
        if (tile == null) { // if there is no tile at that location, append a space
          mapString.append(" ");
          continue; // continue onto the next coordinate
        }
        // if there is a tile at the location, append its toString and a space for padding
        mapString.append(tile).append(" ");
      }
      mapString.append("\n"); // append a new line once the end of the row is reached
    }

    return mapString.toString(); // get contents of the StringBuilder with toString and return it
  }

  int getModelSideLength() {
    // the equation below uses the number of hexagons that compose the board to
    // get the side length of the board. I derived it myself :)

    // first, we get an intermediate computation and ensure that it is an integer value
    // if it is not, then the model's board is not a valid hex grid, so we throw an exception
    double intermediateValue = Math.sqrt((12 * this.model.getTiles().size()) - 3);
    if (intermediateValue % 1 != 0) {
      throw new IllegalStateException("This view's model is not a valid hex grid.");
    }

    // now that we know the model is a valid hex grid, continue computing the side length
    // using the intermediate value as an integer
    return (3 + (int)intermediateValue) / 6; // return the final value
  }
}
