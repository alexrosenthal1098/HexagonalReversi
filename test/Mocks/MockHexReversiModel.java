package Mocks;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import Model.HexagonalReversi;
import Player.PlayerType;
import Player.ReversiPlayer;
import Tile.PointyTopHexagon;
import Tile.ReversiTile;

/**
 * A mock of a HexagonalReversi game used for testing purposes.
 */
public class MockHexReversiModel extends HexagonalReversi {

  /**
   * A constructor for a mock hexagonal reversi model.
   * @param player1 The player type of the player that moves first and uses the black discs.
   * @param player2 The player type of the player that moves first and uses the black discs.
   * @param sideLength The side length of the board.
   */
  public MockHexReversiModel(PlayerType player1, PlayerType player2, int sideLength) {
    super(player1, player2, sideLength);
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

    return mockTiles; // return them mock map
  }
}
