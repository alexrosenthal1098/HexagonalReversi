import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import Model.HexagonalReversi;
import Model.ReversiModel;
import View.HexReversiTextView;

/**
 * A class that shows examples of how to play the game Reversi using this codebase.
 */
public class ReversiExamples {
  ReversiModel model;
  HexReversiTextView view;

  @Before
  public void setUp() {
    this.model = new HexagonalReversi(6);
  }

  @Test
  public void testView() {
    HexagonalReversi hexModel = new HexagonalReversi(6);
    this.view = new HexReversiTextView(hexModel);
    Assert.assertEquals("", this.view.toString());
  }
}
