package mocks;

import model.HexagonalReversi;

/**
 * A mock model for HexagonalReversi that maintains a log of important method calls.
 */
public class MockWithLog extends HexagonalReversi {
  public StringBuilder log = new StringBuilder();

  /**
   * A constructor for a mock hexagonal reversi model.
   * @param sideLength The side length of the HexagonalBoard.
   */
  public MockWithLog(int sideLength) {
    super(sideLength);
  }

  @Override
  public boolean isMovePossible(int q, int r) {
    this.log.append("Trying a move at: (").append(q).append(", ").append(r).append(")\n");
    return super.isMovePossible(q, r);
  }
}
