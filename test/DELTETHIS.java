import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;

import adapters.BothModels;
import adapters.ToProviderModel;
import providers.model.strategies.IStrategies;
import providers.model.strategies.MaxNumOfCells;

public class DELTETHIS {

  @Test
  public void testSomething() {
    IStrategies strat = new MaxNumOfCells();
    BothModels model = new ToProviderModel(3);
    Assert.assertEquals("", strat.strategicMove(model, model.getBoard(), model.getColor()));
  }
}
