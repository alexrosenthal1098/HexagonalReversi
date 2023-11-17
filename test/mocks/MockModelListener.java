package mocks;

import model.ModelListener;

/**
 * A mock class for a ModelListener that keeps track of method calls.
 */
public class MockModelListener implements ModelListener {
  public final StringBuilder log = new StringBuilder();
  @Override
  public void yourTurn() {
    log.append("Your turn\n");
  }
}
