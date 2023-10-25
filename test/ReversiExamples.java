import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import Model.HexagonalReversi;
import Model.ReversiModel;
import Player.PlayerType;
import View.HexReversiTextView;

public class ReversiExamples {
  ReversiModel model;
  HexReversiTextView view;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi(PlayerType.PLAYER_HUMAN, PlayerType.PLAYER_HUMAN, 6);
  }

  @Test
  public void testView() {
    HexagonalReversi hexModel = new HexagonalReversi(PlayerType.PLAYER_HUMAN, PlayerType.PLAYER_HUMAN, 6);
    this.view = new HexReversiTextView(hexModel);
    Assert.assertEquals("", this.view.toString());
  }
}
