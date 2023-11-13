package strategy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

import mocks.MockHexReversiModel;
import mocks.MockWithLog;
import model.HexagonalReversi;
import model.ReversiModel;

/**
 * A class that holds tests for the HexCaptureMaxPieces strategy.
 */
public class HexCaptureMaxPiecesTest {
  HexCaptureMaxPieces captureMax;
  ReversiModel model;
  ReversiModel mockModel;
  MockWithLog mockWithLog;

  @Before
  public void setUp() {
    this.captureMax = new HexCaptureMaxPieces();
    this.model = new HexagonalReversi(6);
    this.mockModel = new MockHexReversiModel(6);
    this.mockWithLog = new MockWithLog(6);
  }

  // tests for chooseMove
  @Test(expected = IllegalArgumentException.class)
  public void testChooseMoveNullModel() {
    this.captureMax.chooseMove(null);
  }

  @Test
  public void testChooseMoveWithMock() {
    // the mock model will make the strategy think that the only possible move is (-1, 2)
    // so the strategy should return that move
    Assert.assertEquals(new Point(-1, 2), this.captureMax.chooseMove(this.mockModel));
  }

  @Test
  public void testChooseMoveTriesEveryMove() {
    this.captureMax.chooseMove(this.mockWithLog);
    for (Point point : this.mockWithLog.getTiles().keySet()) {
      Assert.assertTrue(this.mockWithLog.log.toString().contains(
              "(" + point.x + ", " + point.y + ")"));
    }
  }

  @Test
  public void testChooseMoveDoesntModifyModel() {
    Point move = this.captureMax.chooseMove(this.model);
    Assert.assertFalse(this.model.getTileAt(move.x, move.y).hasDisk());
  }

  @Test
  public void testChooseMoveBreaksTies() {
    Assert.assertEquals(new Point(1, -2), this.captureMax.chooseMove(this.model));
  }

  @Test
  public void testChooseMoveNotATie() {
    this.model.moveAt(2, -1);
    this.model.passTurn();
    this.model.moveAt(-1, 2);
    Assert.assertEquals(new Point(-1, 3), this.captureMax.chooseMove(this.model));
  }

  @Test(expected = IllegalStateException.class)
  public void testChooseMoveNoMovesLeft() {
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    this.captureMax.chooseMove(this.model);
  }



  // tests for numCapturedPieces
  @Test(expected = IllegalArgumentException.class)
  public void testNumCapturedPiecesNullPoint() {
    this.captureMax.numCapturedPieces(null, this.model);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNumCapturedPiecesNullModel() {
    this.captureMax.numCapturedPieces(new Point(0, 0), null);
  }

  @Test(expected = IllegalStateException.class)
  public void testNumCapturedPiecesMoveNotPossible() {
    this.captureMax.numCapturedPieces(new Point(0, 0), this.model);
  }

  @Test
  public void testNumCapturedPiecesOnePiece() {
    Assert.assertEquals(1,
            this.captureMax.numCapturedPieces(new Point(1, -2), this.model));
  }

  @Test
  public void testNumCapturedPieces2PiecesMultipleDirections() {
    this.model.moveAt(1, 1);
    this.model.moveAt(1, 2);
    Assert.assertEquals(2,
            this.captureMax.numCapturedPieces(new Point(2, -1), this.model));
  }

  @Test
  public void testNumCapturedPieces3OneDirection() {
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    this.model.moveAt(1, 2);
    this.model.passTurn();
    Assert.assertEquals(3,
            this.captureMax.numCapturedPieces(new Point(-3, 1), this.model));
  }



  // tests for upperLeftMostPoint
  @Test(expected = IllegalArgumentException.class)
  public void testUpperLeftMostNullPoint1() {
    this.captureMax.upperLeftMost(null, new Point(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testUpperLeftMostNullPoint2() {
    this.captureMax.upperLeftMost(new Point(0, 0), null);
  }

  @Test
  public void testUpperLeftMostPoint1MoreUpAndLeft() {
    Point point1 = new Point(-1, -1);
    Point point2 = new Point(0, 0);
    Assert.assertEquals(point1, this.captureMax.upperLeftMost(point1, point2));
  }

  @Test
  public void testUpperLeftMostPoint1MoreUpButNotLeft() {
    Point point1 = new Point(2, -1);
    Point point2 = new Point(0, 0);
    Assert.assertEquals(point1, this.captureMax.upperLeftMost(point1, point2));
  }

  @Test
  public void testUpperLeftMostPoint2MoreUpAndLeft() {
    Point point1 = new Point(-1, -1);
    Point point2 = new Point(-2, -2);
    Assert.assertEquals(point2, this.captureMax.upperLeftMost(point1, point2));
  }

  @Test
  public void testUpperLeftMostPoint2MoreUpButNotLeft() {
    Point point1 = new Point(-1, -1);
    Point point2 = new Point(3, -2);
    Assert.assertEquals(point2, this.captureMax.upperLeftMost(point1, point2));
  }

  @Test
  public void testUpperLeftMostPointSameRowButPoint1MoreLeft() {
    Point point1 = new Point(-3, 0);
    Point point2 = new Point(-1, 0);
    Assert.assertEquals(point1, this.captureMax.upperLeftMost(point1, point2));
  }

  @Test
  public void testUpperLeftMostPointSameRowButPoint2MoreLeft() {
    Point point1 = new Point(3, 0);
    Point point2 = new Point(-1, 0);
    Assert.assertEquals(point2, this.captureMax.upperLeftMost(point1, point2));
  }

  @Test
  public void testUpperLeftMostPointSamePoint() {
    Point point1 = new Point(3, 3);
    Point point2 = new Point(3, 3);
    Assert.assertEquals(point2, this.captureMax.upperLeftMost(point1, point2));
  }
}