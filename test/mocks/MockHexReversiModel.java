package mocks;

import java.awt.Color;

import model.HexagonalReversi;
import model.tile.PointyTopHexagon;
import model.tile.ReversiTile;

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
  public Color getColorAt(int x, int y) throws IllegalArgumentException, IllegalStateException {
    return Color.GREEN;
  }

  @Override
  public ReversiTile getTileAt(int q, int r) throws IllegalArgumentException {
    ReversiTile tile = new PointyTopHexagon();
    tile.placeDisk(Color.GREEN, Color.YELLOW);
    return tile;
  }
}
