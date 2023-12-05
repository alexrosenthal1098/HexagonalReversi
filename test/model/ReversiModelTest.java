package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;
import java.util.Map;

import mocks.MockModelListener;
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
    this.model.startGame();
  }


  // tests for moveAt
  @Test(expected = IllegalStateException.class)
  public void testMoveAtGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.moveAt(1, 1);
  }

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
    Assert.assertEquals(Color.BLACK, this.model.getTileAt(1, 1).getTopColor());
  }

  @Test
  public void testMoveAtPlacesCorrectDiskAtGivenTilePlayer2() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.WHITE, this.model.getTileAt(1, 1).getTopColor());
  }

  @Test
  public void testMoveAtFlipsTilesToTheRight() {
    this.model.passTurn();
    this.model.moveAt(-1, -1);
    Assert.assertEquals(Color.WHITE, this.model.getTileAt(0, -1).getTopColor());
  }

  @Test
  public void testMoveAtFlipsTilesToTheLeft() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.BLACK, this.model.getTileAt(0, 1).getTopColor());
  }

  @Test
  public void testMoveAtFlipsTilesUpAndRight() {
    this.model.moveAt(-2, 1);
    Assert.assertEquals(Color.BLACK, this.model.getTileAt(0, -1).getTopColor());
  }

  @Test
  public void testMoveAtFlipsTilesDownAndRight() {
    this.model.moveAt(-1, -1);
    Assert.assertEquals(Color.BLACK, this.model.getTileAt(-1, 0).getTopColor());
  }

  @Test
  public void testMoveAtFlipsTilesUpAndLeft() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.WHITE, this.model.getTileAt(1, 0).getTopColor());
  }

  @Test
  public void testMoveAtFlipsTilesDownAndLeft() {
    this.model.passTurn();
    this.model.moveAt(2, -1);
    Assert.assertEquals(Color.WHITE, this.model.getTileAt(1, 0).getTopColor());
  }

  @Test
  public void testMoveAtChangesTurn() {
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
  }



  // tests for passTurn
  @Test(expected = IllegalStateException.class)
  public void testPassTurnGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.passTurn();
  }

  @Test
  public void testPassTurnBlackToWhite() {
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
  }

  @Test
  public void testPassTurnWhiteToBlack() {
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
  }



  // tests for isMovePossible
  @Test(expected = IllegalStateException.class)
  public void testIsMovePossibleGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.isMovePossible(1, 1);
  }

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
  @Test(expected = IllegalStateException.class)
  public void testAnyMovesGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.anyMoves();
  }

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
    this.model.startGame();
    this.model.moveAt(1, 1);
    this.model.moveAt(-1, 2);
    this.model.passTurn();
    this.model.moveAt(2, -1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
    Assert.assertTrue(this.model.anyMoves());
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
    Assert.assertFalse(this.model.anyMoves());

  }

  @Test
  public void testAnyMovesNoMovesForPlayer1ButMovesForPlayer2() {
    this.model = new HexagonalReversi(3);
    this.model.startGame();
    this.model.passTurn();
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(-1, 2);
    this.model.moveAt(2, -1);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
    Assert.assertTrue(this.model.anyMoves());
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
    Assert.assertFalse(this.model.anyMoves());
  }

  @Test
  public void testAnyMovesNoMovesForEitherPlayer() {
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
    Assert.assertFalse(this.model.anyMoves());
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
    Assert.assertFalse(this.model.anyMoves());
  }



  // tests for isGameOver
  @Test(expected = IllegalStateException.class)
  public void testIsGameOverGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.isGameOver();
  }

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



  // tests for getCurrentPlayerScore
  @Test(expected = IllegalStateException.class)
  public void testGetCurrentPlayerScoreGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.getCurrentPlayerScore();
  }

  @Test
  public void testGetCurrentPlayerScoreAfterStart() {
    Assert.assertEquals(3, this.model.getCurrentPlayerScore());
  }

  @Test
  public void testGetCurrentPlayerScoreIncreasesAfterCurrentPlayerMove() {
    int scoreBefore = this.model.getCurrentPlayerScore();
    this.model.moveAt(1, 1);
    this.model.passTurn();
    int scoreAfter = this.model.getCurrentPlayerScore();
    Assert.assertTrue(scoreAfter - scoreBefore > 0);
    Assert.assertEquals(5, scoreAfter);
  }

  @Test
  public void testGetCurrentPlayerScoreDecreasesAfterOtherPlayerMove() {
    int scoreBefore = this.model.getCurrentPlayerScore();
    this.model.passTurn();
    this.model.moveAt(1, 1);
    int scoreAfter = this.model.getCurrentPlayerScore();
    Assert.assertTrue(scoreAfter - scoreBefore < 0);
    Assert.assertEquals(2, scoreAfter);
  }

  @Test
  public void testCurrentPlayerScoreIs0() {
    this.model.passTurn();
    this.model.moveAt(1, 1);
    this.model.passTurn();
    this.model.moveAt(-2, 1);
    this.model.passTurn();
    this.model.moveAt(1, -2);
    Assert.assertEquals(0, this.model.getCurrentPlayerScore());
  }



  // tests for getOtherPlayerScore
  @Test(expected = IllegalStateException.class)
  public void testGetOtherPlayerScoreGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.getOtherPlayerScore();
  }

  @Test
  public void testGetOtherPlayerScoreAfterStart() {
    Assert.assertEquals(3, this.model.getOtherPlayerScore());
  }

  @Test
  public void testGetOtherPlayerScoreIncreasesAfterMove() {
    int scoreBefore = this.model.getOtherPlayerScore();
    this.model.passTurn();
    this.model.moveAt(1, 1);
    int scoreAfter = this.model.getOtherPlayerScore();
    Assert.assertTrue(scoreAfter - scoreBefore > 0);
    Assert.assertEquals(5, scoreAfter);
  }

  @Test
  public void testGetOtherPlayerScoreDecreasesAfterBlackMove() {
    int scoreBefore = this.model.getOtherPlayerScore();
    this.model.moveAt(1, 1);
    this.model.passTurn();
    int scoreAfter = this.model.getOtherPlayerScore();
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
    this.model.passTurn();
    Assert.assertEquals(0, this.model.getOtherPlayerScore());
  }



  // test currentPlayerColor() {
  @Test
  public void testCurrentPlayerColorBlack() {
    Assert.assertEquals(Color.BLACK, this.model.currentPlayerColor());
  }

  @Test
  public void testCurrentPlayerColorWhite() {
    this.model.passTurn();
    Assert.assertEquals(Color.WHITE, this.model.currentPlayerColor());
  }



  // test otherPlayerColor() {
  @Test
  public void testOtherPlayerColorWhite() {
    Assert.assertEquals(Color.WHITE, this.model.otherPlayerColor());
  }

  @Test
  public void testOtherPlayerColorBlack() {
    this.model.passTurn();
    Assert.assertEquals(Color.BLACK, this.model.otherPlayerColor());
  }



  // test getTileAt
  @Test(expected = IllegalArgumentException.class)
  public void testGetTileAtIllegalCoordinatesTooHigh() {
    this.model.moveAt(20, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTileAtIllegalCoordinatesTooLow() {
    this.model.moveAt(-3, -9);
  }

  @Test
  public void testGetTileAtReturnsCopy() {
    ReversiTile tile1 = this.model.getTileAt(0, 1);
    ReversiTile tile2 = this.model.getTileAt(0, 1);
    Assert.assertNotSame(tile1, tile2);
  }

  @Test
  public void testGetTileAtBlackDisk() {
    Assert.assertEquals(Color.BLACK, this.model.getTileAt(0, -1).getTopColor());
  }

  @Test
  public void testGetTileAtWhiteDisk() {
    Assert.assertEquals(Color.WHITE, this.model.getTileAt(1, -1).getTopColor());
  }

  @Test
  public void testGetTileAtNoDisk() {
    Assert.assertFalse(this.model.getTileAt(1, 1).hasDisk());
  }

  @Test
  public void testGetTileAtTileAfterItsFlipped() {
    this.model.moveAt(1, 1);
    Assert.assertEquals(Color.BLACK, this.model.getTileAt(0, 1).getTopColor());
  }



  // tests for getTiles
  @Test
  public void testGetTilesMakesACopy() {
    Map<Point, ReversiTile> firstCall = this.model.getTiles();
    Map<Point, ReversiTile> secondCall = this.model.getTiles();
    Assert.assertNotSame(firstCall, secondCall);
  }

  @Test
  public void testGetTilesCorrectSize() {
    Assert.assertEquals(91, this.model.getTiles().keySet().size());
    Assert.assertEquals(91, this.model.getTiles().values().size());
  }

  @Test
  public void testGetTilesCorrectSize2() {
    this.model = new HexagonalReversi(4);
    this.model.startGame();
    Assert.assertEquals(37, this.model.getTiles().keySet().size());
    Assert.assertEquals(37, this.model.getTiles().values().size());
  }

  @Test
  public void testGetTilesCorrectTileLocations() {
    Map<Point, ReversiTile> tiles = this.model.getTiles();
    this.model = new HexagonalReversi(3);
    for (int q = -2; q <= 2; q++) {
      for (int r = Math.max(-2, -q - 2); r < Math.min(2, -q + 2); r++) {
        Assert.assertTrue(tiles.containsKey(new Point(q, r)));
      }
    }
  }

  @Test
  public void testGetTilesHasStartingDisks() {
    Map<Point, ReversiTile> tiles = this.model.getTiles();
    Assert.assertEquals(Color.BLACK, tiles.get(new Point(0, -1)).getTopColor());
    Assert.assertEquals(Color.WHITE, tiles.get(new Point(1, -1)).getTopColor());
    Assert.assertEquals(Color.BLACK, tiles.get(new Point(1, 0)).getTopColor());
    Assert.assertEquals(Color.WHITE, tiles.get(new Point(0, 1)).getTopColor());
    Assert.assertEquals(Color.BLACK, tiles.get(new Point(-1, 1)).getTopColor());
    Assert.assertEquals(Color.WHITE, tiles.get(new Point(-1, 0)).getTopColor());
  }



  // tests for addListener()
  @Test(expected = IllegalStateException.class)
  public void testAddListenerAfterGameStarted() {
    this.model.addListener(new MockModelListener(), false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddListenerNull() {
    this.model = new HexagonalReversi();
    this.model.addListener(null, false);
  }

  @Test
  public void testAddListenerBeforeGameStarted() {
    this.model = new HexagonalReversi();
    this.model.addListener(new MockModelListener(), false);
    Assert.assertTrue(true);
  }

  @Test
  public void testAddListenerDoesntAddDuplicates() {
    this.model = new HexagonalReversi();
    MockModelListener mockListener = new MockModelListener();
    this.model.addListener(mockListener, true);
    this.model.addListener(mockListener, true);
    this.model.startGame();
    Assert.assertFalse(mockListener.log.toString().contains("Your turn\nYour turn"));
    Assert.assertTrue(mockListener.log.toString().contains("Your turn"));
  }



  // test for addReadOnlyListener
  @Test(expected = IllegalArgumentException.class)
  public void testAddReadOnlyListenerNull() {
    this.model.addReadOnlyListener(null);
  }

  @Test
  public void testAddReadOnlyListenerBeforeGameStart() {
    this.model = new HexagonalReversi();
    MockModelListener listener = new MockModelListener();
    this.model.addReadOnlyListener(listener);
    Assert.assertTrue(true);
  }

  @Test
  public void testAddReadOnlyListenerAfterGameStart() {
    MockModelListener listener = new MockModelListener();
    this.model.addReadOnlyListener(listener);
    Assert.assertTrue(true);
  }

  @Test
  public void testAddReadOnlyListenerDoesntAddDuplicates() {
    MockModelListener mockListener = new MockModelListener();
    this.model.addReadOnlyListener(mockListener);
    this.model.addReadOnlyListener(mockListener);
    this.model.passTurn();
    Assert.assertFalse(mockListener.log.toString().contains("Model changed\nModel changed"));
    Assert.assertTrue(mockListener.log.toString().contains("Model changed"));
  }



  // tests for startGame
  @Test(expected = IllegalStateException.class)
  public void testStartGameAlreadyStarted() {
    this.model.startGame();
  }

  @Test
  public void testStartGameNotStarted() {
    this.model = new HexagonalReversi();
    this.model.startGame();
    Assert.assertTrue(true);
  }



  // tests for copyModel
  @Test
  public void testCopyModelIsACopy() {
    Assert.assertNotSame(this.model, this.model.copyModel());
  }

  @Test(expected = IllegalStateException.class)
  public void testCopyModelHasNotStarted() {
    ReversiModel copy = this.model.copyModel();
    copy.passTurn();
  }
}