package mocks;

import java.awt.Point;

import player.PlayerActionListener;

/**
 * A mock class for a PlayerActionListener.
 */
public class MockPlayerActionListener implements PlayerActionListener {
  public StringBuilder log = new StringBuilder();

  @Override
  public void moveMade(Point tile) throws IllegalArgumentException {
    this.log.append("Move made\n");
  }

  @Override
  public void turnPassed() {
    this.log.append("Turn passed\n");
  }

  @Override
  public void errorOccurred(String message) {
    this.log.append("Error occurred\n");
  }
}
