import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

import model.HexagonalReversi;
import view.textview.HexReversiTextView;

/**
 * A class that shows examples of how to play the game Reversi using this codebase.
 */
public class ReversiExamples {
  HexagonalReversi model;
  HexReversiTextView view;

  @Before
  public void setUp() {
    // A HexagonalReversi model allows you to specify the side length of the board in tiles
    this.model = new HexagonalReversi(6);

    // a HexReversiTextView only accepts the type HexagonalReversi, not just any ReversiModel,
    // because that is the only type of board it knows how to draw
    this.view = new HexReversiTextView(this.model);
  }

  @Test
  public void testStartingViewWith6Sides() {
    // The board is arranged into a hexagon. 6 disks, 3 for each player,
    // are placed to start off.

    Assert.assertEquals("" +
            "     _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _ \n" +
            "_ _ _ _ O _ X _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "     _ _ _ _ _ _ \n", this.view.toString());
  }

  @Test
  public void testAxialCoordinates() {
    // The location of a tile is represented using axial coordinates where the
    // first argument is the q value and second argument is the r value
    // the tile in the center of the board (the empty tile surrounded by disks) is at
    // the point (0, 0).

    // player one can move at the tile represented by (1, 1)
    Assert.assertTrue(this.model.isMovePossible(1, 1));
    this.model.moveAt(1, 1); // player one makes the move

    Assert.assertEquals("" +
            "     _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ X O _ _ _ _ \n" +
            "_ _ _ _ O _ X _ _ _ _ \n" +
            " _ _ _ _ X X X _ _ _ \n" +  // notice how a disk was placed at (1, 1) and the tile
            "  _ _ _ _ _ _ _ _ _ \n" +   // to its left had its disk flipped to reveal
            "   _ _ _ _ _ _ _ _ \n" +    // player one's color
            "    _ _ _ _ _ _ _ \n" +
            "     _ _ _ _ _ _ \n", this.view.toString());

    // we can get the color of the disk that was placed using the axial coordinates of the tile
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(1, 1));

    // we can also check that a disk was flipped
    Assert.assertEquals(Color.BLACK, this.model.getColorAt(0, 1));
  }

  @Test
  public void testAutomaticAndManualPassingOfTurns() {
    // The player using black pieces always goes first, and since each player
    // can only move once, after a valid move has been played by either player,
    // the move automatically moves to the other one. Alternatively, a player
    // can choose to pass their turn.

    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer()); // black moves next
    this.model.moveAt(2, -1); // black plays a move
    Assert.assertEquals(Color.WHITE, this.model.getCurrentPlayer()); // the turn is passed to white
    this.model.passTurn(); // white passes their turn
    Assert.assertEquals(Color.BLACK, this.model.getCurrentPlayer()); // the turn is back to black
  }

  @Test
  public void testGameState() {
    // We can learn about the overall state of the game by asking whether the current
    // player has any moves or if the game is over. We can also ask for each player's score.

    Assert.assertTrue(this.model.anyMoves()); // black has plenty of moves to play
    Assert.assertTrue(this.model.anyMoves()); // so does white
    Assert.assertFalse(this.model.isGameOver()); // therefore the game is not over yet

    // player one starts off with a score of 3
    Assert.assertEquals(3, this.model.getPlayer1Score());

    // player two does as well
    Assert.assertEquals(3, this.model.getPlayer2Score());

    this.model.moveAt(1, 1); // after black plays a move
    Assert.assertEquals(5, this.model.getPlayer1Score()); // player 1 has more points
    Assert.assertEquals(2, this.model.getPlayer2Score()); // and player 2 has less
  }

  @Test
  public void testPlayingAFullGame() {
    // Here is an example of a full game being played on a smaller board
    this.model = new HexagonalReversi(3);
    this.view = new HexReversiTextView(this.model);

    // The board looks like this at the start:
    Assert.assertEquals("" +
            "  _ _ _ \n" +
            " _ X O _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", this.view.toString());

    this.model.moveAt(1, 1);
    this.model.moveAt(1, -2);
    this.model.moveAt(-1, -1);
    this.model.moveAt(-2, 1);
    try {
      this.model.moveAt(0, 0);
    }
    catch (IllegalStateException e) {
      Assert.assertTrue("That move is not allowed!", true);
    }
    this.model.moveAt(2, -1); // the last move was not valid, so it is still black's turn
    this.model.moveAt(-1, 2);
    Assert.assertFalse(this.model.anyMoves()); // black has no moves remaining!
    this.model.passTurn(); // they have no option other than to pass their turn
    Assert.assertFalse(this.model.anyMoves()); // white also has no moves remaining!
    Assert.assertTrue(this.model.isGameOver()); // therefore the game is over

    // The board now looks like this:
    Assert.assertEquals("" +
            "  _ O _ \n" +
            " X X X X \n" +
            "_ O _ X _ \n" +
            " O O X X \n" +
            "  _ O _ \n", this.view.toString());

    // The final score is player 1: 7, player 2: 5
    Assert.assertEquals(7, this.model.getPlayer1Score());
    Assert.assertEquals(5, this.model.getPlayer2Score());
  }


}
