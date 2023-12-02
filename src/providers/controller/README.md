# Pt.3 Changes 

The Contoller is solving the issues of providing a way for the user to interact with the game. This code is reliant on two different prerequisites, a model of the overall game that is being displayed and a view that is being used to display the game. In the case of this game the controller is using the model to get the data that it needs to represent the game board and then using the view to display the game board. However, this code is not reliant on the direct actions of the players themselves, meaning that you do not need to have a player class or interface to have this code working.<br>

Design choices:<br>
When presenting the board we made it such that if its two humans then the game plays like normal where each player has to enter e to confirm thier choice of cell or p to pass thier turn, when it is a human and a computer then the computer will automctaically play its move and then the human will have to choose, then for computer vs computer we made t so that in each phase(one players turn then the other players turn) that the computers will each execute thier moves automtically but that a key must be pressed inbetween to activate the next phase, this wasdone so we could keep track of what the machines were doing and make sure it is working proeply and so that whoever is using the game can also be able to see what moves are bing made instead of teh game rapidly ending becasue the machines isntaly played agaisnt each other at a rapid pace. Additionally, we have the score siplyed of each player at the bottom and the bar at the bottom displays whos turn it uccrnetly is. The two screesn pop up ontop of each otehr the screen on top is the WHITE players screena dn the one on teh bottom is the BLACK players board. Lastly, the game can be played using the arhs in the main when going to the edit configuation when trying to play it and inutting which arguments you want to use. The arguments are human for a human player, this does not iclude a strategy. You can also put computer and if you choose computer then you must input what strategy you want to use. The options are strategy1 for MaxNumOfCells, Strategy2 for AvoidCellsSurroundingCorners, Strategy3 for GoForCorners, and Strategy4 for MiniMax. 


Here is an example indicating how the end result o this code would be used:
public static void main(String[] args) {<br>
ReversiModel model = new ABSTReversi();<br>
IView view = new ReversiGraphicsView(model);<br>
IView view2 = new ReversiGraphicsView(model);<br>
PlayerActions player = ArgsHandler.getPlayer1(args);<br>
IStrategies strat = ArgsHandler.getStrat1(args);<br>
player.addStrategy(strat);<br>
PlayerActions player2 = ArgsHandler.getPlayer2(args);<br>
IStrategies strat2 = ArgsHandler.getStrat2(args);<br>
player2.addStrategy(strat2);<br>
ControllerOne controller = new ControllerOne(player, view, model);<br>
ControllerTwo controller2 = new ControllerTwo(player2, view2, model);<br>
model.startGame();<br>
model.makeBoard(5);<br>
view.makeVisible();<br>
view.refresh();<br>
view2.makeVisible();<br>
view2.refresh();<br>
}<br>
<br>

## Key Components:
ControllerOne class- this class is used to describe the actions that are going to be taken when the mouse is clicked on the game board or when certain keys are pressed. This class is driving the control flow as it is using the model to get the data that it needs to represent the game board. Inside of the class we also have it so that if the keys p is pressed then the player will pass their turn however this is not fully implemented as we don't have the controller yet<br>
ControllerTwo class- this class is used to describe the actions that are going to be taken when the mouse is clicked on the game boardor when certain keys are pressed. This class is driving the control flow as it is using the model to get the data that it needs to represent the game board. Inside of the class we also have it so that if the keys p is pressed then the player will pass their turn however this is not fully implemented as we don't have the controller yet<br>
ArgsHandler class- This class is used to get the arguments that are being passed in from the command line and then using those arguments to create the players and strategies that are going to be used in the game<br>
IEvent interface- this interface is used to describe the actions that are going to be taken when the mouse is clicked on the game board or when certain keys are pressed. This class is driving the control flow as it is using the model to get the data that it needs to represent the game board. Inside of the class we also have it so that if the keys p is pressed then the player will pass their turn and when e is pressed and a cell is highlighted by a previous click then the player will make a move to that cell if the cell is valid and the only two options of these moves from IEvent would be the move or pass. 
IModelListener interface- this interface is used to descibe what model listeners are going to need to function in its entirity. 
IViewListener interface- this interface is used to descibe what view listeners are going to need to function in its entirity.
Move class- this class is used to represnst one of the diffrent types of IEvents the player can enact.
Pass class- this class is used to represnst one of the diffrent types of IEvents the player can enact.
ViewListener Interface- this interface is used to descibe what view listeners are going to need to function in its entirity.

## Key Subcomponents:

# ArgsHandler Class Documentation

## Overview

The `ArgsHandler` class is responsible for parsing and handling command-line arguments related to configuring a game system. It is part of the `controller` package.

## Key Components

### 1. `ArgsHandler` Class

The `ArgsHandler` class consists of methods for extracting player actions and strategies based on the provided command-line arguments.

### 2. `getPlayer1` Method

- **Description:** Determines the action for Player 1 based on command-line arguments.
- **Input:** An array of command-line arguments (`args`).
- **Output:** Returns a `PlayerActions` object representing the action for Player 1.

### 3. `getStrat1` Method

- **Description:** Determines the strategy for Player 1 based on command-line arguments.
- **Input:** An array of command-line arguments (`args`).
- **Output:** Returns an `IStrategies` object representing the strategy for Player 1.

### 4. `getPlayer2` Method

- **Description:** Determines the action for Player 2 based on command-line arguments.
- **Input:** An array of command-line arguments (`args`).
- **Output:** Returns a `PlayerActions` object representing the action for Player 2.

### 5. `getStrat2` Method

- **Description:** Determines the strategy for Player 2 based on command-line arguments.
- **Input:** An array of command-line arguments (`args`).
- **Output:** Returns an `IStrategies` object representing the strategy for Player 2.

## Control Flow

The control flow in the system is driven by the method calls within the `ArgsHandler` class:

- The methods `getPlayer1`, `getStrat1`, `getPlayer2`, and `getStrat2` handle different aspects of player actions and strategies.
- Control flow is influenced by the number and values of command-line arguments.
- Input validation is performed to ensure the correctness of the provided arguments.

# ChangePlayer Class Documentation

## Overview

The `ChangePlayer` class is an implementation of the `IEvent` interface and is part of the `controller` package. It represents an event related to changing the player in a game.

## Key Components

### 1. `ChangePlayer` Class

The `ChangePlayer` class implements the `IEvent` interface and includes the following component:

- **`color` Attribute:**
    - **Description:** A string attribute representing the color associated with the player change event.

### 2. `getColor` Method

- **Description:** A method that retrieves the color associated with the player change event.
- **Output:** Returns a string representing the color.

### 3. `getCell` Method (from `IEvent` Interface)

- **Description:** Overrides the `getCell` method from the `IEvent` interface.
- **Output:** Always returns `null` since this event is not associated with a specific game cell.



# ControllerOne Class Documentation

## Overview

The `ControllerOne` class serves as a controller in a game system, connecting the user interface (`IView`) and the game model (`ReversiModel`). It implements the `ViewListener` and `ModelListener` interfaces to handle events from the view and model, respectively.

## Key Components

### 1. `ControllerOne` Class

The `ControllerOne` class includes the following components:

- **`player` Attribute:**
    - **Description:** Represents the player actions associated with this controller.

- **`view` Attribute:**
    - **Description:** Represents the user interface (`IView`) associated with this controller.

- **`model` Attribute:**
    - **Description:** Represents the game model (`ReversiModel`) associated with this controller.

- **`color` Attribute:**
    - **Description:** Stores the color associated with the controller.

- **`played` Attribute:**
    - **Description:** A static boolean flag indicating whether a move has been made.

- **`passed` Attribute:**
    - **Description:** A static boolean flag indicating whether a player has passed their turn.

### 2. Constructor

- **Description:** Initializes the `ControllerOne` object with the specified player, view, and model. Also adds itself as a listener to both the view and the model.

### 3. `onModelEvent` Method (from `ModelListener` Interface)

- **Description:** Overrides the `onModelEvent` method to handle events from the game model. If the player is a `MachinePlayer`, it attempts to make a move using the player's strategy. Displays error messages for invalid moves or if the game is over.

### 4. `onViewEvent` Method (from `ViewListener` Interface)

- **Description:** Overrides the `onViewEvent` method to handle events from the user interface (`IView`). Calls the `makeMove` method when a move event is received and updates the model when a pass event is received.

### 5. `makeMove` Method

- **Description:** Makes a move in the game model based on the received event. Displays error messages for invalid moves or if the game is over.

### 6. `getPlayed` Method

- **Description:** Retrieves the value of the `played` attribute.

# ControllerTwo Class Documentation

## Overview

The `ControllerTwo` class is a controller in a game system, similar to `ControllerOne`. It implements the `ViewListener` and `ModelListener` interfaces to handle events from the view and model, respectively.

## Key Components

### 1. `ControllerTwo` Class

The `ControllerTwo` class includes the following components:

- **`player` Attribute:**
    - **Description:** Represents the player actions associated with this controller.

- **`view` Attribute:**
    - **Description:** Represents the user interface (`IView`) associated with this controller.

- **`model` Attribute:**
    - **Description:** Represents the game model (`ReversiModel`) associated with this controller.

- **`color` Attribute:**
    - **Description:** Stores the color associated with the controller.

### 2. Constructor

- **Description:** Initializes the `ControllerTwo` object with the specified player, view, and model. Also adds itself as a listener to both the view and the model.

### 3. `onModelEvent` Method (from `ModelListener` Interface)

- **Description:** Overrides the `onModelEvent` method to handle events from the game model. If the player is a `MachinePlayer` and `ControllerOne` has not played, it attempts to make a move using the player's strategy. Displays error messages for invalid moves or if the game is over.

### 4. `onViewEvent` Method (from `ViewListener` Interface)

- **Description:** Overrides the `onViewEvent` method to handle events from the user interface (`IView`). Calls the `makeMove` method when a move event is received and updates the model when a pass event is received.

### 5. `makeMove` Method

- **Description:** Makes a move in the game model based on the received event. Displays error messages for invalid moves or if the game is over.

# Move Class Documentation

## Overview

The `Move` class implements the `IEvent` interface and is part of the `controller` package. It represents an event related to a player making a move on the game board.

## Key Components

### 1. `Move` Class

The `Move` class includes the following components:

- **`cell` Attribute:**
    - **Description:** Represents the game cell where the move is made.

### 2. Constructor

- **Description:** Initializes the `Move` object with the specified game cell.

### 3. `getCell` Method

- **Description:** Retrieves the game cell associated with the move.
- **Output:** Returns an `ICell` object representing the game cell.

# Pass Class Documentation

## Overview

The `Pass` class implements the `IEvent` interface and is part of the `controller` package. It represents an event related to a player passing their turn in the game.

## Key Components

### 1. `Pass` Class

The `Pass` class includes the following components:

- **No Additional Attributes**

### 2. `getCell` Method

- **Description:** Overrides the `getCell` method from the `IEvent` interface.
- **Output:** Always returns `null` since this event is not associated with a specific game cell.


### Source Organization:
The sorce code is split up into three sections, the model, the controller and the view. The model is split up into two sections, the
board and the players. Thr board is made up of the Reversi Intrface, the Cell Class the BasicReversi class, the
ABSTreversi class and the fill class. The player section is made up of the player interface and the player class.
The other section, the view, is made up of the textualView class and the ReversiGraphicsView class.
The controller is made up of the controllerOne class, the controllerTwo class, the ArgsHandler class, the IEvent interface, the IModelListener 
interface, the IViewListener interface, the Move class and the Pass class. These classes and interfaces are used to describe the actions that are 
going to be taken when the mouse is clicked on the game board or when certain keys are pressed. This class is driving the control flow as it is 
using the model to get the data that it needs to represent the game board. <br>


### Class Invariant:
#### The board:
In the assignment we have declared the board to be the primary class invariant. The reason behind this would be that
the board does not change in size or its contents. Throughout th entire program the board will always be the same
size, it does not change, and it will have the same number of cells in the same x y coordinate plane regardless of
what actions occur. The only thing that changes is the state of the cells themselves, but the cells themselves do
not change or the board itself does not change.


