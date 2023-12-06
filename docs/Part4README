We were able to adapt our model to work with the provider's view implementation, and we
adapted the provider's view implementation to work with our controller. This ensures that we only
have to instantiate one model, one of each type of view, and one type of controller (two
controllers, one for each player, but they're the same class).

The only feature of our view that could not be adapted to their view is showing an error message.
While this method is part of the provider's view interface, it is left blank in their view
implementation for some reason.

The human player's work flawlessly (from what we can tell). All of the provider's AI strategies
have been adapted to work with our controller and they can play a complete game. However, after
playing multiple games with our AI against the provider's, I have noticed that their strategies
seem to be non-deterministic. The TryStrat strategy also has a weird bug where the provider's view
renders the board as having an extra color disk on certain tiles that should be blank. The model is
not affected by this, because either player can move on said tile and override the false disk.

Overall, every feature of the assignment is successfully integrated, with a few bugs present
in the provider's AI strategies.

To run the program, it takes in 2 arguments. The first specifies our strategy, the second specifies
the provider's strategy.
Our strategies:
 "human"
 "capture-max"
Provider strategies:
 "human"
 "max-cells"
 "corners"
 "avoid-near-corners"
 "minimax"
 "try-all"