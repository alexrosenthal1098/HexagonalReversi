package mocks;

import java.awt.Point;
import java.awt.Color;
import java.util.Map;

import model.HexagonalReversi;
import model.tile.PointyTopHexagon;
import model.tile.ReversiTile;

/**
 * A mock of a HexagonalReversi game used for testing purposes.
 */
public class MockHexReversiModel extends HexagonalReversi {

  /**
   * A constructor for a mock hexagonal reversi model.
   * @param sideLength The side length of the HexagonalBoard.
   */
  public MockHexReversiModel(int sideLength) {
    super(sideLength);
  }

  @Override
  public Color getColorAt(int x, int y) throws IllegalArgumentException, IllegalStateException {
    return Color.GREEN;
  }

  @Override
  public Map<Point, ReversiTile> getTiles() {
    Map<Point, ReversiTile> fakeBoard = super.getTiles();
    ReversiTile tile = new PointyTopHexagon();
    tile.placeDisk(Color.GREEN, Color.YELLOW);
    fakeBoard.put(new Point(0, 0), tile);

    return fakeBoard;
  }
}
