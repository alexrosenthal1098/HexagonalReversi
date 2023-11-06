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
  public ReversiTile getTileAt(int x, int y) throws IllegalArgumentException {
    return new PointyTopHexagon(Color.GREEN, Color.YELLOW);
  }
}
