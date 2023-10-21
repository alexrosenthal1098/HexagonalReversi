package View;

import java.util.Objects;

import Model.ReversiModel;

public class HexReversiTextView implements TextView {
  private final ReversiModel model;

  public HexReversiTextView(ReversiModel model) {
    this.model = Objects.requireNonNull(model);
  }

  @Override
  public String toString() {
    // TODO: implement
    return "";
  }
}
