package model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mocks.MockModelListener;

/**
 * A class that holds tests for a ModelListener.
 */
public class ModelListenerTest {
  ReversiModel model;
  MockModelListener listener;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi();
    this.listener = new MockModelListener();
  }



  // tests for yourTurn
  @Test
  public void testYourTurnPlayer1AfterStartGame() {
    this.model.addListener(this.listener, true);
    this.model.startGame();
    Assert.assertTrue(this.listener.log.toString().contains("Your turn"));
  }

  @Test
  public void testYourTurnPlayer2AfterStartGame() {
    this.model.addListener(this.listener, false);
    this.model.startGame();
    Assert.assertFalse(this.listener.log.toString().contains("Your turn"));
  }

  @Test
  public void testYourTurnPlayer2AfterMove() {
    this.model.addListener(this.listener, false);
    this.model.startGame();
    this.model.passTurn();
    Assert.assertTrue(this.listener.log.toString().contains("Your turn"));
  }

  @Test
  public void testYourTurnPlayer1After2Moves() {
    this.model.addListener(this.listener, true);
    this.model.startGame();
    this.model.passTurn();
    this.model.passTurn();
    Assert.assertTrue(this.listener.log.toString().contains("Your turn\nYour turn"));
  }



  // tests for modelChanged
  @Test
  public void testModelChangedAfterPassTurn() {
    this.model.addReadOnlyListener(this.listener);
    this.model.startGame();
    this.model.passTurn();
    Assert.assertTrue(this.listener.log.toString().contains("Model changed"));
  }

  @Test
  public void testModelChangedAfterValidMove() {
    this.model.addReadOnlyListener(this.listener);
    this.model.startGame();
    this.model.moveAt(1, 1);
    Assert.assertTrue(this.listener.log.toString().contains("Model changed"));
  }

  @Test
  public void testModelChangedAfterInvalidMove() {
    this.model.addReadOnlyListener(this.listener);
    try {
      this.model.moveAt(0, 0);
    }
    catch (Exception ignored) { }
    Assert.assertFalse(this.listener.log.toString().contains("Model changed"));
  }

  @Test
  public void testModelChangedAfterNonMutationMethod() {
    this.model.addReadOnlyListener(this.listener);
    this.model.startGame();
    this.model.isMovePossible(0, 0);
    this.model.anyMoves();
    this.model.isGameOver();
    this.model.getOtherPlayerScore();
    this.model.getCurrentPlayerScore();
    this.model.currentPlayerColor();
    this.model.otherPlayerColor();
    this.model.getTileAt(0, 0);
    this.model.getTiles();
    Assert.assertFalse(this.listener.log.toString().contains("Model changed"));
  }
}
