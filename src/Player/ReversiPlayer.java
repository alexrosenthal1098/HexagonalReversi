package Player;

import java.awt.*;

/**
 * An interface that represents player types for the game Reversi.
 */
public interface ReversiPlayer {
  /**
   * Gets the color of disc this player is using.
   * @return The color.
   */
  Color getColor();

  // TODO something simple like int[] next move with 2 ints in it?
}
