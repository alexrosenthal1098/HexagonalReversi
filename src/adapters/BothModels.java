package adapters;

import model.ReversiModel;

/**
 * A marker interface that specifies a model that implements both our and the provider's interface.
 */
public interface BothModels extends ReversiModel, providers.model.board.ReversiModel {
}
