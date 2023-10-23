package View;

import org.junit.Assert;
import org.junit.Test;

import Mocks.MockHexReversiModel;
import Model.HexagonalReversi;
import Model.ReversiModel;
import Player.PlayerType;

public class HexReversiTextViewTest {

  // tests for the constructor
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullModel() {
    TextView view = new HexReversiTextView(null);
  }

  @Test
  public void testConstructorValidModel() {
    HexagonalReversi model = new HexagonalReversi(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 6);
    TextView view = new HexReversiTextView(model);
  }



  // tests for toString
  @Test
  public void testToStringStartingBoardOfLength6() {
    HexagonalReversi model = new HexagonalReversi(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 6);
    TextView view = new HexReversiTextView(model);
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
            "     _ _ _ _ _ _ \n", view.toString());
  }

  @Test
  public void testToStringStartingBoardOfLength3() {
    HexagonalReversi model = new HexagonalReversi(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 3);
    TextView view = new HexReversiTextView(model);
    Assert.assertEquals("" +
            "  _ _ _ \n" +
            " _ X O _ \n" +
            "_ O _ X _ \n" +
            " _ X O _ \n" +
            "  _ _ _ \n", view.toString());
  }

  @Test
  public void testToStringStartingBoardOfLength15() {
    HexagonalReversi model = new HexagonalReversi(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 10);
    TextView view = new HexReversiTextView(model);
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
            "         _ _ _ _ _ _ _ _ _ _ \n", view.toString());
  }

  // TODO: add more tests once model functionality has been implemented



  // test getModelSideLength
  @Test
  public void testGetModelSideLengthInvalidModel() {
    HexagonalReversi mockModel = new MockHexReversiModel(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 6);
    HexReversiTextView view = new HexReversiTextView(mockModel);
    Assert.assertThrows(IllegalStateException.class, () -> view.getModelSideLength());
  }

  @Test
  public void testGetModelSideLengthOfArea5() {
    HexagonalReversi model = new HexagonalReversi(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 4);
    HexReversiTextView view = new HexReversiTextView(model);
    Assert.assertEquals(4, view.getModelSideLength());
  }

  @Test
  public void testGetModelSideLength6() {
    HexagonalReversi model = new HexagonalReversi(PlayerType.PLAYER_HUMAN,
            PlayerType.PLAYER_HUMAN, 6);
    HexReversiTextView view = new HexReversiTextView(model);
    Assert.assertEquals(6, view.getModelSideLength());
  }



  // test tileToString
  @Test(expected = IllegalArgumentException.class)
  public void testTileToStringNullTile() {

  }

}