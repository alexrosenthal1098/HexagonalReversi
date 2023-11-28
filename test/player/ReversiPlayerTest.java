package player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mocks.MockPlayerActionListener;
import model.HexagonalReversi;
import model.ReversiModel;
import strategy.HexCaptureMaxPieces;

/**
 * A class that holds tests for a ReversiPlayer.
 */
public class ReversiPlayerTest {
  ReversiPlayer humanPlayer;
  ReversiPlayer aiPlayer;
  ReversiModel model;
  MockPlayerActionListener listener;

  @Before
  public void setUp() {
    this.humanPlayer = new HumanPlayer();
    this.model = new HexagonalReversi();
    this.aiPlayer = new ReversiAI(this.model, new HexCaptureMaxPieces());
    this.listener = new MockPlayerActionListener();
    this.model.startGame();
  }



  // tests for addListener
  @Test(expected = IllegalArgumentException.class)
  public void testAddListenerHumanNull() {
    this.humanPlayer.addListener(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddListenerAINull() {
    this.aiPlayer.addListener(null);
  }

  @Test
  public void testAddListenerNonNull() {
    this.humanPlayer.addListener(this.listener);
    this.aiPlayer.addListener(this.listener);
    Assert.assertTrue(true);
  }

  @Test
  public void testAddListenerAINoDuplicates() {
    this.aiPlayer.addListener(this.listener);
    this.aiPlayer.addListener(this.listener);
    this.aiPlayer.makeMove();
    Assert.assertFalse(this.listener.log.toString().contains("Move made\nMove made\n"));
    Assert.assertTrue(this.listener.log.toString().contains("Move made"));
  }
}