package util;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import model.HexagonalReversi;
import model.ReversiModel;
import model.tile.ReversiTile;

/**
 * A class that contains tests for the HexReversiUtils class.
 */
public class HexReversiUtilsTest {

  // tests for getBoardSideLength
  @Test(expected = IllegalArgumentException.class)
  public void testGetBoardSideLengthNullMap() {
    HexReversiUtils.getBoardSideLength(null);
  }

  @Test
  public void testGetBoardSideLength2() {
    Map<Point, ReversiTile> fakeBoard = new HashMap<>();
    fakeBoard.put(new Point(0, 0), null);
    fakeBoard.put(new Point(0, -1), null);
    fakeBoard.put(new Point(1, -1), null);
    fakeBoard.put(new Point(1, 0), null);
    fakeBoard.put(new Point(0, 1), null);
    fakeBoard.put(new Point(-1, 1), null);
    fakeBoard.put(new Point(-1, 0), null);

    Assert.assertEquals(2, HexReversiUtils.getBoardSideLength(fakeBoard));
  }

  @Test
  public void testGetBoardSideLength6() {
    ReversiModel hexModel = new HexagonalReversi(6);
    hexModel.startGame();

    Assert.assertEquals(6, HexReversiUtils.getBoardSideLength(hexModel.getTiles()));
  }
}