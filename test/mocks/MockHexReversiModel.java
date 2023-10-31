package mocks;

import java.awt.Point;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import model.HexagonalReversi;
import tile.PointyTopHexagon;
import tile.ReversiTile;

/**
 * A mock of a HexagonalReversi game used for testing purposes.
 */
public class MockHexReversiModel extends HexagonalReversi {

  /**
   * A constructor for a mock hexagonal reversi model.
   * @param sideLength The side length of the board.
   */
  public MockHexReversiModel(int sideLength) {
    super(sideLength);
  }

  @Override
  public Color getColorAt(int x, int y) throws IllegalArgumentException {
    return Color.GREEN;
  }

  @Override
  public Map<Point, ReversiTile> getTiles() {
    // create a mock tile map
    Map<Point, ReversiTile> mockTiles = new HashMap<Point, ReversiTile>();

    // add random hexagons to the map
    mockTiles.put(new Point(1, 1), new PointyTopHexagon());
    mockTiles.put(new Point(2, 2), new PointyTopHexagon());
    mockTiles.put(new Point(3, 3), new PointyTopHexagon());

    // add a disk with unsupported colors to a tile
    PointyTopHexagon tile = new PointyTopHexagon();
    tile.placeDisk(Color.YELLOW, Color.CYAN);
    mockTiles.put(new Point(0, 0), tile);

    return mockTiles; // return them mock map
  }
}
