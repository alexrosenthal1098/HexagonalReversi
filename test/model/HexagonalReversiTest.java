package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;
import java.util.Map;
import java.util.List;

import model.tile.ReversiTile;

/**
 * Tests for private/protected methods of HexagonalReversi.
 */
public class HexagonalReversiTest {
  HexagonalReversi model;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi();
  }


  // tests for makeBoard()
  @Test
  public void testMakeBoardCorrectSize() {
    Map<Point, ReversiTile> tiles = this.model.makeBoard(6);
    Assert.assertEquals(91, tiles.keySet().size());
    Assert.assertEquals(91, tiles.values().size());
  }

  @Test
  public void testMakeBoardCorrectSize2() {
    Map<Point, ReversiTile> tiles = this.model.makeBoard(4);
    Assert.assertEquals(37, tiles.keySet().size());
    Assert.assertEquals(37, tiles.values().size());
  }

  @Test
  public void testMakeBoardCorrectTileLocations() {
    Map<Point, ReversiTile> tiles = this.model.makeBoard(3);
    this.model = new HexagonalReversi(3);
    for (int q = -2; q <= 2; q++) {
      for (int r = Math.max(-2, -q - 2); r < Math.min(2, -q + 2); r++) {
        Assert.assertTrue(tiles.containsKey(new Point(q, r)));
      }
    }
  }

  @Test
  public void testMakeBoardHasStartingTiles() {
    Map<Point, ReversiTile> tiles = this.model.makeBoard(6);
    Assert.assertEquals(Color.BLACK, tiles.get(new Point(0, -1)).getTopColor());
    Assert.assertEquals(Color.WHITE, tiles.get(new Point(1, -1)).getTopColor());
    Assert.assertEquals(Color.BLACK, tiles.get(new Point(1, 0)).getTopColor());
    Assert.assertEquals(Color.WHITE, tiles.get(new Point(0, 1)).getTopColor());
    Assert.assertEquals(Color.BLACK, tiles.get(new Point(-1, 1)).getTopColor());
    Assert.assertEquals(Color.WHITE, tiles.get(new Point(-1, 0)).getTopColor());
  }



  // test otherPlayerColor
  @Test
  public void testOtherPlayerColorShouldBeWhite() {
    Assert.assertEquals(Color.WHITE, this.model.otherPlayerColor());
  }

  @Test
  public void testOtherPlayerColorShouldBeBlack() {
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.otherPlayerColor());
  }



  // test tilesWithColor
  @Test(expected = NullPointerException.class)
  public void testTilesWithColorNullColor() {
    this.model.tilesWithColor(null);
  }

  @Test
  public void testTilesWithColorNotOnTheBoard() {
    Assert.assertEquals(0, this.model.tilesWithColor(Color.CYAN));
  }

  @Test
  public void testTilesWithColorBlackAtStart() {
    Assert.assertEquals(3, this.model.tilesWithColor(Color.BLACK));
  }

  @Test
  public void testTilesWithColorWhiteAtStart() {
    Assert.assertEquals(3, this.model.tilesWithColor(Color.WHITE));
  }

  @Test
  public void testTilesWithColorBlackAfterPlayer1Move() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(5, this.model.tilesWithColor(Color.BLACK));
  }

  @Test
  public void testTilesWithColorWhiteAfterPlayer1Move() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(2, this.model.tilesWithColor(Color.WHITE));
  }

  @Test
  public void testTilesWithColorWhiteAfterPlayer2Move() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    Assert.assertEquals(5, this.model.tilesWithColor(Color.WHITE));
  }

  @Test
  public void testTilesWithColorBlackAfterPlayer2Move() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    Assert.assertEquals(2, this.model.tilesWithColor(Color.BLACK));
  }



  // test tilesToFlip
  @Test(expected = NullPointerException.class)
  public void testTilesToFlipNullStart() {
    this.model.tilesToFlip(null, 1, 1);
  }

  @Test
  public void testTilesToFlipDoesNOTReturnCopy() {
    List<ReversiTile> tilesToFlip1 = this.model.tilesToFlip(new Point(1, 1),
            -1, 0);
    List<ReversiTile> tilesToFlip2 = this.model.tilesToFlip(new Point(1, 1),
            -1, 0);
    Assert.assertSame(tilesToFlip1.get(0), tilesToFlip2.get(0));
  }

  @Test
  public void testTilesToFlip0Movement() {
    Assert.assertTrue(this.model.tilesToFlip(new Point(1, 1), 0, 0).isEmpty());
  }

  @Test
  public void testTilesToFlipCopiesStartingPoint() {
    Point startPoint = new Point(1, 0);
    this.model.tilesToFlip(startPoint, 2, 2);
    Assert.assertEquals(new Point(1, 0), startPoint);
  }

  @Test
  public void testTilesToFlipStartPointNotOnBoard() {
    Assert.assertTrue(this.model.tilesToFlip(new Point(10, 10), 0, 0).isEmpty());

  }

  @Test
  public void testTileToFlipFirstTileToLookAtEmpty() {
    Assert.assertTrue(this.model.tilesToFlip(new Point(1, 0), -1, 0).isEmpty());
  }

  @Test
  public void testTileToFlipPlayer1sMoveLookingInValidDirection() {
    List<ReversiTile> tilesToFlip = this.model.tilesToFlip(new Point(1, 1), -1, 0);
    Assert.assertEquals(1, tilesToFlip.size());
    Assert.assertEquals(Color.WHITE, tilesToFlip.get(0).getTopColor());
  }

  @Test
  public void testTileToFlipPlayer1sMoveLookingInEmptyDirection() {
    Assert.assertTrue(this.model.tilesToFlip(new Point(1, 1), 1, 0).isEmpty());
  }

  @Test
  public void testTileToFlipPlayer1sMoveLookingDirectionFacingBlackDisk() {
    Assert.assertTrue(this.model.tilesToFlip(new Point(1, 1), 0, -1).isEmpty());
  }

  @Test
  public void testTileToFlipPlayer2sMoveLookingInValidDirection() {
    this.model.passTurn();
    List<ReversiTile> tilesToFlip = this.model.tilesToFlip(new Point(-1, -1),
            1, 0);
    Assert.assertEquals(1, tilesToFlip.size());
    Assert.assertEquals(Color.BLACK, tilesToFlip.get(0).getTopColor());
  }

  @Test
  public void testTileToFlipPlayer2sMoveLookingInEmptyDirection() {
    this.model.passTurn();
    Assert.assertTrue(this.model.tilesToFlip(new Point(-1, -1), 1, -1).isEmpty());
  }

  @Test
  public void testTileToFlipPlayer2sMoveLookingDirectionFacingWhiteDisk() {
    Assert.assertTrue(this.model.tilesToFlip(new Point(1, 1), 1, 0).isEmpty());
  }

  @Test
  public void testTileToFlipMultipleDirectionsWithTilesToFlip() {
    this.model.moveAt(1, 1);
    this.model.moveAt(1, 2);
    List<ReversiTile> tilesToFlipLeft = this.model.tilesToFlip(new Point(2, -1),
            -1, 0);
    Assert.assertEquals(1, tilesToFlipLeft.size());
    Assert.assertEquals(Color.WHITE, tilesToFlipLeft.get(0).getTopColor());
    List<ReversiTile> tilesToFlipLeftAndDown = this.model.tilesToFlip(new Point(2, -1),
            -1, 1);
    Assert.assertEquals(1, tilesToFlipLeftAndDown.size());
    Assert.assertEquals(Color.WHITE, tilesToFlipLeftAndDown.get(0).getTopColor());
  }

  @Test
  public void testTileToFlipDirectionLeadsToEdgeOfBoard() {
    this.model = new HexagonalReversi(3);
    Assert.assertTrue(this.model.tilesToFlip(new Point(-2, 0), -1, 0).isEmpty());

  }



  // tests for getDirections
  @Test
  public void testGetDirectionsReturnsNewListEveryTime() {
    List<Point> directions1 = this.model.getDirections();
    List<Point> directions2 = this.model.getDirections();
    Assert.assertNotSame(directions1, directions2);
  }

  @Test
  public void testGetDirectionsCorrectSize() {
    Assert.assertEquals(6, this.model.getDirections().size());
  }

  @Test
  public void testGetDirectionsCorrectStepAmounts() {
    List<Point> directions = this.model.getDirections();
    Assert.assertTrue(directions.contains(new Point(-1, 0))); // left
    Assert.assertTrue(directions.contains(new Point(0, -1))); // up and left
    Assert.assertTrue(directions.contains(new Point(1, -1))); // up and right
    Assert.assertTrue(directions.contains(new Point(1, 0))); // right
    Assert.assertTrue(directions.contains(new Point(0, 1))); // down and right
    Assert.assertTrue(directions.contains(new Point(-1, 1))); // down and left
  }



  // tests for copyBoard
  @Test(expected = NullPointerException.class)
  public void testCopyBoardNullException() {
    this.model.copyBoard(null);
  }

  @Test
  public void testCopyBoardActuallyMakesCopy() {
    HexagonalReversi copyModel = new HexagonalReversi(3);
    Map<Point, ReversiTile> board1 = this.model.copyBoard(copyModel);
    Map<Point, ReversiTile> board2 = this.model.copyBoard(copyModel);
    Assert.assertNotSame(board1, board2);
  }

  @Test
  public void testCopyBoardHasCorrectTiles() {
    HexagonalReversi copyModel = new HexagonalReversi(3);
    copyModel.moveAt(1, 1);
    copyModel.moveAt(1, -2);
    Map<Point, ReversiTile> newBoard = this.model.copyBoard(copyModel);

    for (int q = -2; q <= 2; q++) {
      for (int r = -2; r <= 2; r++) {
        ReversiTile newTile = newBoard.get(new Point(q, r));
        if (newTile != null) {
          ReversiTile oldTile = copyModel.getTileAt(q, r);
          Assert.assertEquals(oldTile.hasDisk(), newTile.hasDisk());
          if (newTile.hasDisk()) {
            Assert.assertEquals(oldTile.getTopColor(), newTile.getTopColor());
            oldTile.flipDisk();
            newTile.flipDisk();
            Assert.assertEquals(oldTile.getTopColor(), newTile.getTopColor());
          }
        }
      }
    }
  }
}