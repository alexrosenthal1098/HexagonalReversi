package adapters;

import java.awt.*;

import providers.model.board.ICell;

/**
 * A class that provides utility functions useful for adapting provider's code to ours.
 */
public class AdapterUtils {

  /**
   * Converts the given cell's coordinates to the ones used for our model.
   * @param cell The cell to convert coordinates from.
   * @param sideLength The side length, in tiles, of the board.
   * @return A point representing the given cell but for our model.
   */
  public static Point toOurCoordinates(ICell cell, int sideLength) {
    int ourX;
    if (cell.getY() < sideLength) {
      ourX = cell.getX() - cell.getY();
    }
    else {
      ourX = cell.getX() - sideLength + 1;

    }
    int ourY = cell.getY() - sideLength + 1;
    return new Point(ourX, ourY);
  }

  /**
   * Converts the given point's coordinates to the ones used for the provider's model.
   * @param tilePoint The tile point to convert coordinates from.
   * @param sideLength The side length, in tiles, of the board.
   * @return A point representing the given tile point but for the provider's model.
   */
  public static Point toTheirCoordinates(Point tilePoint, int sideLength) {
    int theirY = tilePoint.y + sideLength - 1;
    int theirX;
    if (tilePoint.y > 0) {
      theirX = tilePoint.x + sideLength - 1;
    }
    else {
      theirX = tilePoint.x + theirY;
    }

    return new Point(theirX, theirY);
  }
}
