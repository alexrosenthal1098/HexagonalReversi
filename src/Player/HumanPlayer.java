package Player;

import java.awt.*;
import java.util.Objects;

/**
 * A human player for the game Reversi.
 */
public class HumanPlayer implements ReversiPlayer{
  private final Color color;

  /**
   * A constructor that accepts this human player's disk color.
   * @param color The color disc this player uses.
   */
  public HumanPlayer(Color color) {
    this.color = Objects.requireNonNull(color);
  }

  @Override
  public Color getColor() {
    return new Color(this.color.getRGB());
  }
}
