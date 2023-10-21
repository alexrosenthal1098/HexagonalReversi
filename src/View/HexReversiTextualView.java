package View;

import java.util.Objects;

import Model.ReversiModel;

public class HexReversiTextualView implements TextView {
  private final ReversiModel model;

  public HexReversiTextualView(ReversiModel model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public String toString() {
    // TODO: implement
    return "";
  }
}
