package view.textview;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;

import mocks.MockHexReversiModel;
import model.HexagonalReversi;

/**
 * A class that holds tests for the HexReversiTextView class.
 */
public class HexReversiTextViewTest {
  HexagonalReversi model;
  HexReversiTextView view;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi(6);
    this.view = new HexReversiTextView(this.model);
  }


  // tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    TextView view = new HexReversiTextView(null);
  }

  @Test
  public void testConstructorValidModel() {
    TextView view = new HexReversiTextView(this.model);
    Assert.assertTrue(true);
  }



  // tests for toString
  @Test
  public void testToStringStartingBoardOfLength6() {
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
  public void testToStringStartingBoardOfLength3() {
    this.model = new HexagonalReversi(3);
    this.view = new HexReversiTextView(this.model);
    Assert.assertEquals("" +
            "  _ _ _ \n" +
            " _ X O _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", this.view.toString());
  }

  @Test
  public void testToStringStartingBoardOfLength15() {
    HexagonalReversi model = new HexagonalReversi(10);
    this.view = new HexReversiTextView(model);
    Assert.assertEquals("" +
            "         _ _ _ _ _ _ _ _ _ _ \n" +
            "        _ _ _ _ _ _ _ _ _ _ _ \n" +
            "       _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "      _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ _ _ _ _ X O _ _ _ _ _ _ _ _ \n" +
            "_ _ _ _ _ _ _ _ O _ X _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ _ _ _ _ X O _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "     _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "      _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "       _ _ _ _ _ _ _ _ _ _ _ _ \n" +
            "        _ _ _ _ _ _ _ _ _ _ _ \n" +
            "         _ _ _ _ _ _ _ _ _ _ \n", this.view.toString());
  }

  @Test
  public void testToStringAfterMoves() {
    this.model.moveAt(1, 1);
    this.model.moveAt(1, 2);
    this.model.moveAt(2, -1);
    this.model.passTurn();
    this.model.moveAt(1, 3);
    Assert.assertEquals("" +
            "     _ _ _ _ _ _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "   _ _ _ _ _ _ _ _ \n" +
            "  _ _ _ _ _ _ _ _ _ \n" +
            " _ _ _ _ X X X _ _ _ \n" +
            "_ _ _ _ O _ X _ _ _ _ \n" +
            " _ _ _ _ X X X _ _ _ \n" +
            "  _ _ _ _ _ _ X _ _ \n" +
            "   _ _ _ _ _ _ X _ \n" +
            "    _ _ _ _ _ _ _ \n" +
            "     _ _ _ _ _ _ \n", this.view.toString());
  }



  // test tileToString
  @Test(expected = IllegalArgumentException.class)
  public void testTileToStringNullTile() {
    this.view.tileToString(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testTileToStringUnrecognizedColor() {
    HexagonalReversi mock = new MockHexReversiModel(6);
    this.view = new HexReversiTextView(mock);
    this.view.tileToString(mock.getTiles().get(new Point(0, 0)));
  }

  @Test
  public void testTileToStringTileWithoutDisk() {
    Assert.assertEquals("_", this.view.tileToString(this.model.getTiles().get(new Point(2, 0))));
  }

  @Test
  public void testTileToStringBlack() {
    Assert.assertEquals("X", this.view.tileToString(this.model.getTiles().get(new Point(0, -1))));
  }

  @Test
  public void testTileToStringWhit() {
    Assert.assertEquals("O", this.view.tileToString(this.model.getTiles().get(new Point(1, -1))));
  }
}