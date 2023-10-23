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
    this.view = new HexReversiTextView(this.model);
  }

  @Test
  public void testView() {
    Assert.assertEquals("", this.view.toString());
  }
}
