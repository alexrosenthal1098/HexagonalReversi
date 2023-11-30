package controller;

import org.junit.Before;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;

import mocks.MockView;
import model.HexagonalReversi;
import model.ReversiModel;
import player.ReversiAI;
import player.ReversiPlayer;
import strategy.CaptureMaxPieces;

/**
 * A class that holds tests for a ReversiController.
 */
public class ReversiControllerTest {
  ReversiController controller;
  ReversiModel model;
  ReversiPlayer aiPlayer;
  MockView view;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi();
    this.aiPlayer = new ReversiAI(this.model, new CaptureMaxPieces());
    this.view = new MockView();
    this.controller = new ReversiController(this.model, this.aiPlayer, this.view, true);
  }



  // tests for constructors
  @Test(expected = IllegalArgumentException.class)
  public void testReversiControllerNullModel() {
    this.controller = new ReversiController(null, this.aiPlayer, this.view, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReversiControllerNullPlayer() {
    this.controller = new ReversiController(this.model, null, this.view, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testReversiControllerNullView() {
    this.controller = new ReversiController(this.model, this.aiPlayer, null, true);
  }



  // tests for yourTurn
  @Test
  public void testYourTurnPromptsPlayerToMove() {
    this.model.startGame();
    int scoreBefore = this.model.getCurrentPlayerScore();
    this.controller.yourTurn();
    Assert.assertNotEquals(scoreBefore, this.model.getOtherPlayerScore());
  }

  @Test
  public void testYourTurnBeforeGameStarted() {
    this.controller.yourTurn();
    Assert.assertEquals("Game has not started.\n", this.view.log.toString());
  }



  // tests for modelChanged
  @Test
  public void testModelChangedUpdatesView() {
    this.controller.modelChanged();
    Assert.assertEquals("cleared\nupdated\n", this.view.log.toString());
  }



  // tests for moveMade
  @Test
  public void testMoveMadeInvalidMoveDisplaysError() {
    this.model.startGame();
    this.controller.moveMade(new Point(0, 0));
    Assert.assertTrue(this.view.log.toString().contains("This move is not possible."));
  }

  @Test
  public void testMoveMadeInvalidMoveAsksForAnotherMove() {
    this.model.startGame();
    int scoreBefore = this.model.getCurrentPlayerScore();
    this.controller.moveMade(new Point(0, 0));
    // the controller should have asked the ai player to make another move, which would
    // lead to the move being played on the model
    Assert.assertNotEquals(scoreBefore, this.model.getOtherPlayerScore());
  }

  @Test
  public void testMoveMadeValidMove() {
    this.model.startGame();
    this.controller.moveMade(new Point(2, -1));
    Assert.assertTrue(this.model.getTileAt(2, -1).hasDisk());
  }



  // tests for turnPassed
  @Test
  public void testTurnPassedExceptionDisplaysError() {
    try {
      // the game has not started yet, so this throws an error which is caught by the controller
      // and sent to the view
      this.controller.turnPassed();
    }
    catch (Exception e) {
      // however, the controller then asks the ai player to make another move, which throws another
      // error, so we catch it
      Assert.assertTrue(this.view.log.toString().contains("Game has not started."));
      return;
    }
    Assert.fail();
  }

  @Test
  public void testTurnPassedExceptionAsksForAnotherMove() {
    this.model.startGame();
    // starting the game prompts the controller to ask the ai to play a move, which passes the turn
    // to the other player
    int scoreBefore = this.model.getOtherPlayerScore();

    // when the controller tries to pass when it is not their player's turn, it will throw an error
    // and then prompt the ai to play another move, which makes the scoreAfter higher than
    // scoreBefore
    this.controller.turnPassed();

    Assert.assertNotEquals(scoreBefore, this.model.getOtherPlayerScore());
  }

  @Test
  public void testTurnPassedValidMove() {
    this.model.startGame();
    this.model.passTurn();
    Color playerBeforePassing = this.model.currentPlayerColor();
    this.controller.turnPassed();
    Color playerAfterPassing = this.model.otherPlayerColor();
    Assert.assertNotEquals(playerBeforePassing, playerAfterPassing);
  }


  // tests for errorOccurred
  @Test
  public void testErrorOccurred() {
    this.controller.errorOccurred("something bad happened");
    Assert.assertEquals("something bad happened\n", this.view.log.toString());
  }
}