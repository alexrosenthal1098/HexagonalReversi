package util;

import java.awt.Point;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import model.tile.ReversiTile;

/**
 * A class that holds utility functions for a HexagonalReversi game.
 */
public class HexReversiUtils {

  /**
   * Get the side length, in tiles, of the given board map.
   * @param map A map of Point to ReversiTile that represents a model's board.
   * @return The side length.
   * @throws IllegalArgumentException if the given map is null.
   */
  public static int getBoardSideLength(Map<Point, ReversiTile> map) {
    if (map == null) {
      throw new IllegalArgumentException("Given map cannot be null.");
    }
    // initialize a hashmap of row number to number of tiles in that row
    Map<Integer, Integer> lengthOfRows = new HashMap<>();
    for (Point point : map.keySet()) {
      // for the current point's r value, add 1 to its current number of tiles
      lengthOfRows.put(point.y, lengthOfRows.getOrDefault(point.y, 0) + 1);
    }

    // return the minimum number of tiles in a row, which represents the board's side length
    return lengthOfRows.values().stream().min(Comparator.naturalOrder()).get();
  }
}
