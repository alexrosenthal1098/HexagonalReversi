package view.textview;

import java.awt.Point;
import java.awt.Color;
import java.util.Map;

import model.ReadOnlyReversiModel;
import model.tile.ReversiTile;
import util.HexReversiUtils;

/**
 * A class that creates a textual view of a HexagonalReversi game.
 */
public class HexReversiTextView implements TextView {
  private final ReadOnlyReversiModel model; // the model to create a view from
  // declares the model as type ReadOnlyReversiModel to prevent this class from mutating the model

  /**
   * A constructor that takes in a read only hexagonal reversi model to create a view from.
   * @param model a read only reversi model.
   */
  public HexReversiTextView(ReadOnlyReversiModel model) {
    // ensure the given model is not null
    if (model == null) {
      throw new IllegalArgumentException("Given model cannot be null.");
    }
    this.model = model; // set the field
  }

  @Override
  public String toString() {
    // get the side length of this model's board
    int sideLength = HexReversiUtils.getBoardSideLength(this.model.getTiles());
    // initialize a StringBuilder that represents the hex map.
    StringBuilder mapString = new StringBuilder();
    Map<Point, ReversiTile> board = this.model.getTiles(); // get the board map from the model

    // iterate over all r values that occur in the hex map
    for (int r = -sideLength + 1; r <= sideLength - 1; r++) {
      // iterate over q values, ensuring that they start and end in a location that ensures a
      // correct number of total columns to ensure proper spacing/padding
      // (this math was derived by looking at the formula to generate the HashMap)
      for (int q = Math.min(-sideLength + 1, -sideLength + 1 - r);
           q <= Math.min(sideLength - 1, sideLength - 1 - r); q++) {
        // if the current point is not on the board
        if (!board.containsKey(new Point(q, r))) {
          mapString.append(" "); // append a space
          continue; // and continue to the next point
        }
        // Now we know that the tile is on the board, get its string representation from a helper
        // and append it and a space for padding
        mapString.append(this.tileToString(board.get(new Point(q, r)))).append(" ");
      }
      mapString.append("\n"); // append a new line once the end of the row is reached
    }

    return mapString.toString(); // get contents of the StringBuilder with toString and return it
  }

  //          HELPER METHODS

  // create a string representation of a tile, used as a helper for toString
  String tileToString(ReversiTile tile) {
    if (tile == null) { // if the tile is null, throw an exception
      throw new IllegalArgumentException("Cannot get string representation from null tile.");
    }
    if (!tile.hasDisk()) { // if the tile has no disk
      return "_"; // return an underscore that represents empty tile
    }

    Color tileColor = tile.getTopColor(); // get the top color of the disk on the tile
    if (tileColor.equals(Color.BLACK)) { // if the color is black
      return "X";
    }
    else if (tileColor.equals(Color.WHITE)) { // if the color is white
      return "O";
    }
    else { // if the color is not black, or white, throw an exception
      throw new IllegalStateException("The color that occupies this tile is unrecognized");
    }
  }
}
