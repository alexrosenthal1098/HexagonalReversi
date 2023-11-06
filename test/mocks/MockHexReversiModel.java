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
  public boolean isMovePossible(int q, int r) {
    // used to ensure that the captureMaxPieces strategy actually chooses the move that
    // captures the most amount of pieces
    // make sure the strategy thinks a move at (2, 2) is possible and no other moves are possible
    if (q == -1 && r == 2) {
      return true;
    }
    else {
      return false;
    }
  }


  @Override
  public ReversiTile getTileAt(int q, int r) throws IllegalArgumentException {
    // used to test that a ReversiTextView cannot display a board with unfamiliar colors
    // (not black and white)
    if (q == 0 && r == 0) {
      new PointyTopHexagon(Color.GREEN, Color.YELLOW);
    }
    return super.getTileAt(q, r);
  }
}
