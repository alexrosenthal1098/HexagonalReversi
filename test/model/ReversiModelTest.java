package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import model.tile.ReversiTile;


/**
 * Tests for all public methods of the ReversiModel interface
 * and the classes that implement this interface.
 */
public class ReversiModelTest {
  ReversiModel model;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi(6);
  }


  // tests for moveAt
  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtIllegalCoordinatesTooHigh() {
    this.model.moveAt(10, 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMoveAtIllegalCoordinatesTooLow() {
    this.model.moveAt(-5, -5);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveAtInvalidMove() {
    this.model.moveAt(0, 0);
  }

  @Test(expected = IllegalStateException.class)
  public void testMoveAtOccupiedTile() {
    this.model.moveAt(1, 0);
  }

  @Test
  public void testMoveAtPlacesCorrectDiskAtGivenTilePlayer1() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(1, 1));
  }

  @Test
  public void testMoveAtPlacesCorrectDiskAtGivenTilePlayer2() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.WHITE, this.model.getColorAt(1, 1));
  }


  @Test
  public void testMoveAtFlipsTilesToTheRight() {
    this.model.passTurn();
    this.model.moveAt(-1, -1);
    Assert.assertEquals(Color.WHITE, this.model.getColorAt(0, -1));
  }

  @Test
  public void testMoveAtFlipsTilesToTheLeft() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(0, 1));
  }

  @Test
  public void testMoveAtFlipsTilesUpAndRight() {
    this.model.moveAt(-2, 1);
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(0, -1));
  }

  @Test
  public void testMoveAtFlipsTilesDownAndRight() {
    this.model.moveAt(-1, -1);
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(-1, 0));
  }

  @Test
  public void testMoveAtFlipsTilesUpAndLeft() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.WHITE, this.model.getColorAt(1, 0));
  }

  @Test
  public void testMoveAtFlipsTilesDownAndLeft() {
    this.model.passTurn();
    this.model.moveAt(2, -1);
    Assert.assertEquals(Color.WHITE, this.model.getColorAt(1, 0));
  }

  @Test
  public void testMoveAtChangesTurn() {
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
  }



  // tests for passTurn
  @Test
  public void testPassTurnBlackToWhite() {
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
  }

  @Test
  public void testPassTurnWhiteToBlack() {
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
  }



  // tests for isMovePossible
  @Test(expected = IllegalArgumentException.class)
  public void testIsMovePossibleCoordinatesTooHigh() {
    this.model.isMovePossible(8, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsMovePossibleCoordinatesTooLow() {
    this.model.isMovePossible(-4, -19);
  }

  @Test
  public void testIsMovePossibleOccupiedTile() {
    Assert.assertFalse(this.model.isMovePossible(-1, 0));
  }

  @Test
  public void testIsMovePossibleMultipleValidDirections() {
    Assert.assertTrue(this.model.isMovePossible(1, 1));
  }

  @Test
  public void testIsMovePossibleOneValidDirections() {
    this.model.moveAt(1, 1);
    Assert.assertTrue(this.model.isMovePossible(1, -2));
  }

  @Test
  public void testIsMovePossibleTileNotTouchingAndTilesWithDisk() {
    Assert.assertFalse(this.model.isMovePossible(3, 2));
  }

  @Test
  public void testIsMovePossibleTileTouchingOtherColorButNotValid() {
    Assert.assertFalse(this.model.isMovePossible(2, -2));
  }

  @Test
  public void testIsMovePossibleTileTouchingOwnColorNotValid() {
    Assert.assertFalse(this.model.isMovePossible(2, 0));
  }



  // tests for anyMoves()
  @Test
  public void testAnyMovesYesForPlayer1() {
    Assert.assertTrue(this.model.anyMoves());
  }

  // tests for anyMoves()
  @Test
  public void testAnyMovesYesForPlayer2() {
    this.model.passTurn();
    Assert.assertTrue(this.model.anyMoves());
  }

  @Test
  public void testAnyMovesNoMovesForPlayer2ButMovesForPlayer1() {
    this.model = new HexagonalReversi(3);
    this.model.moveAt(1, 1);
    this.model.moveAt(-1, 2);
    this.model.passTurn();
    this.model.moveAt(2, -1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
    Assert.assertTrue(this.model.anyMoves());
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
    Assert.assertFalse(this.model.anyMoves());

  }

  @Test
  public void testAnyMovesNoMovesForPlayer1ButMovesForPlayer2() {
    this.model = new HexagonalReversi(3);
    this.model.passTurn();
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(-1, 2);
    this.model.moveAt(2, -1);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
    Assert.assertTrue(this.model.anyMoves());
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
    Assert.assertFalse(this.model.anyMoves());
  }

  @Test
  public void testAnyMovesNoMovesForEitherPlayer() {
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
    Assert.assertFalse(this.model.anyMoves());
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
    Assert.assertFalse(this.model.anyMoves());
  }



  // tests for isGameOver
  @Test
  public void testIsGameOverJustStarted() {
    Assert.assertFalse(this.model.isGameOver());
  }

  @Test
  public void testIsGameOverAfterMoves() {
    this.model.moveAt(1, -2);
    this.model.moveAt(-1, 2);
    Assert.assertFalse(this.model.isGameOver());
  }

  @Test
  public void testIsGameOverYes() {
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    Assert.assertTrue(this.model.isGameOver());
  }



  // tests for getPlayer1Score
  @Test
  public void testGetPlayer1ScoreAtStart() {
    Assert.assertEquals(3, this.model.getPlayer1Score());
  }

  @Test
  public void testGetPlayer1ScoreIncreasesAfterBlackMove() {
    int scoreBefore = this.model.getPlayer1Score();
    this.model.moveAt(1, 1);
    int scoreAfter = this.model.getPlayer1Score();
    Assert.assertTrue(scoreAfter - scoreBefore > 0);
    Assert.assertEquals(5, scoreAfter);
  }

  @Test
  public void testGetPlayer1ScoreDecreasesAfterWhiteMove() {
    int scoreBefore = this.model.getPlayer1Score();
    this.model.passTurn();
    this.model.moveAt(1, 1);
    int scoreAfter = this.model.getPlayer1Score();
    Assert.assertTrue(scoreAfter - scoreBefore < 0);
    Assert.assertEquals(2, scoreAfter);
  }

  @Test
  public void testGetPlayer1ScoreIs0() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    Assert.assertEquals(0, this.model.getPlayer1Score());
  }



  // tests for getPlayer2Score
  @Test
  public void testGetPlayer2ScoreAtStart() {
    Assert.assertEquals(3, this.model.getPlayer2Score());
  }

  @Test
  public void testGetPlayer2ScoreIncreasesAfterWhiteMove() {
    int scoreBefore = this.model.getPlayer2Score();
    this.model.passTurn();
    this.model.moveAt(1, 1);
    int scoreAfter = this.model.getPlayer2Score();
    Assert.assertTrue(scoreAfter - scoreBefore > 0);
    Assert.assertEquals(5, scoreAfter);
  }

  @Test
  public void testGetPlayer2ScoreDecreasesAfterBlackMove() {
    int scoreBefore = this.model.getPlayer2Score();
    this.model.moveAt(1, 1);
    int scoreAfter = this.model.getPlayer2Score();
    Assert.assertTrue(scoreAfter - scoreBefore < 0);
    Assert.assertEquals(2, scoreAfter);
  }

  @Test
  public void testGetPlayer2ScoreIs0() {
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    Assert.assertEquals(0, this.model.getPlayer2Score());
  }



  // test getCurrentPlayer() {
  @Test
  public void testGetCurrentPlayerMakesACopy() {
    Color firstCall = this.model.getCurrentPlayer();
    Color secondCall = this.model.getCurrentPlayer();
    Assert.assertNotSame(firstCall, secondCall);
  }

  @Test
  public void testGetCurrentPlayerBlack() {
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer());
  }

  @Test
  public void testGetCurrentPlayerWHITE() {
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer());
  }



  // test getColorAt()
  @Test(expected = IllegalArgumentException.class)
  public void testGetColorAtIllegalCoordinatesTooHigh() {
    this.model.moveAt(20, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetColorAtIllegalCoordinatesTooLow() {
    this.model.moveAt(-3, -9);
  }

  @Test(expected = IllegalStateException.class)
  public void testGetColorAtTileWithNoDisk() {
    this.model.getColorAt(0, 0);
  }

  @Test
  public void testGetColorAtBlack() {
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(0, -1));
  }

  @Test
  public void testGetColorAtWhite() {
    Assert.assertEquals(Color.WHITE, this.model.getColorAt(1, -1));
  }

  @Test
  public void testGetColorAtNewTilePlaced() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(1, 1));
  }

  @Test
  public void testGetColorAtTileAfterItsFlipped() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(0, 1));
  }



  // tests for getTileAt
  @Test(expected = IllegalArgumentException.class)
  public void testGetTileAtIllegalCoordinatesTooHigh() {
    this.model.getTileAt(20, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTileAtIllegalCoordinatesTooLow() {
    this.model.getTileAt(-3, -9);
  }

  @Test
  public void testGetTileAtMakesCopy() {
    ReversiTile tile1 = this.model.getTileAt(0, 0);
    ReversiTile tile2 = this.model.getTileAt(0, 0);
    Assert.assertNotSame(tile1, tile2);
  }

  @Test
  public void testGetTileAtNoDisk() {
    ReversiTile tile = this.model.getTileAt(0, 0);
    Assert.assertFalse(tile.hasDisk());
  }

  @Test
  public void testGetTileAtBlackDisk() {
    ReversiTile tile = this.model.getTileAt(1, 0);
    Assert.assertEquals(Color.BLACK, tile.getTopColor());
  }

  @Test
  public void testGetTileAtWhiteDisk() {
    ReversiTile tile = this.model.getTileAt(0, 1);
    Assert.assertEquals(Color.WHITE, tile.getTopColor());
  }



  // test getBoardSideLength
  @Test
  public void testGetBoardSideLengthMinimumSize() {
    this.model = new HexagonalReversi(3);
    Assert.assertEquals(3, this.model.getBoardSideLength());
  }

  @Test
  public void testGetBoardSideLengthDefaultSize() {
    this.model = new HexagonalReversi();
    Assert.assertEquals(6, this.model.getBoardSideLength());
  }

  @Test
  public void testGetBoardSideLengthLargeSize() {
    this.model = new HexagonalReversi(100);
    Assert.assertEquals(100, this.model.getBoardSideLength());
  }
}