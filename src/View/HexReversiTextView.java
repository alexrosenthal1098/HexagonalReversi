package View;

import java.awt.*;

import Model.HexagonalReversi;
import Model.ReversiModel;
import Tile.ReversiTile;

/**
 * A class that creates a textual view of a HexagonalReversi game.
 */
public class HexReversiTextView implements TextView {
  private final HexagonalReversi model; // the HexagonalReversi model to create a view from

  /**
   * A constructor that takes in a HexagonalReversi model
   * @param model a HexagonalReversi model.
   */
  public HexReversiTextView(HexagonalReversi model) {
    // ensure the given model is not null
    if (model == null) {
      throw new IllegalArgumentException("Given model cannot be null.");
    }
    this.model = model; // set the field
  }

  @Override
  public String toString() {
    int sideLength = this.getModelSideLength(); // get the side length of this models hex grid
    // initialize a StringBuilder that represents the hex map.
    StringBuilder mapString = new StringBuilder();

    // iterate over all r values that occur in the hex map
    for (int r = -sideLength + 1; r <= sideLength - 1; r++) {
      // iterate over q values, ensuring that they start and end in a location that ensures a
      // correct number of total columns to ensure proper spacing/padding
      // (this math was derived by looking at the formula to generate the HashMap)
      for (int q = Math.min(-sideLength + 1, -sideLength + 1 - r);
           q <= Math.min(sideLength - 1, sideLength - 1 - r); q++) {
        // Get the tile at the current q, r from the map or null if there isn't one there
        ReversiTile tile = this.model.getTiles().get(new Point(q, r));
        if (tile == null) { // if there is no tile at that location, append a space
          mapString.append(" ");
          continue; // continue onto the next coordinate
        }
        // if there is a tile at the location, append its toString and a space for padding
        mapString.append(this.tileToString(tile)).append(" ");
      }
      mapString.append("\n"); // append a new line once the end of the row is reached
    }

    return mapString.toString(); // get contents of the StringBuilder with toString and return it
  }

  //          HELPER METHODS

  // get the side length of the hexagonal board held by this view's model
  int getModelSideLength() {
    // we need to know the side length/radius of the grid in order to know how to render it,
    // but since the model is generalized, we cannot simply ask for this value, therefore
    // we must calculate the value we need using only properties of the tiles Map

    // the equation below uses the number of hexagons that compose the board (the area) to
    // get the side length of the board. I derived it myself :)

    // first, we get an intermediate computation and ensure that it is an integer value
    // if it is not, then the model's board is not a valid hex grid, so we throw an exception
    double intermediateValue = Math.sqrt((12 * this.model.getTiles().size()) - 3);
    if (intermediateValue % 1 != 0) {
      throw new IllegalStateException("This view's model is not a valid hexagonal grid.");
    }

    // now that we know the model is a valid hex grid, continue computing the side length
    // using the intermediate value as an integer
    return (3 + (int)intermediateValue) / 6; // return the final value
  }

  // create a string representation of a tile, used as a helper for toString
  String tileToString(ReversiTile tile) {
    if (tile == null) {
      throw new IllegalArgumentException("Cannot get a color from a null tile.");
    }
    // get the tile color from the model using the given tile's coordinates
    Color tileColor = this.model.getColorAt(tile.getCoordinates().x, tile.getCoordinates().y);

    if (tileColor == null) { // if the tile is unoccupied (color is null)
      return "_";
    }
    else if (tileColor.equals(Color.BLACK)) { // if the color is black
      return "X";
    }
    else if (tileColor.equals(Color.WHITE)) { // if the color is white
      return "O";
    }
    else { // if the color is not null, black, or white, throw an exception
      throw new IllegalStateException("The color that occupies this tile is unrecognized");
    }
  }
}
