## Changes for Part 2:


The view is solving the issues of providing a visualization for the game reversi, in this case a hexagonal version of the game reversi. This code is reliant on two different prerequisites, a model of the overall game that is being displayed and a cell class that has representations of different states that a cell can be in. In the case of this game the cells can be either occupied by the black player, occupied by the white player or empty. However, this code is not reliant on the direct actions of the players themselves, meaning that you do not need to have a player class or interface to have this code working.

Here is an example indicating how the end result o this code would be used:
public static void main(String[] args) {<br>
ReversiModel model = new ABSTReversi();<br>
model.makeBoard(5);<br>
IView view = new ReversiGaphicsView(model);<br>
view.makeVisible();<br>
view.refresh();<br>
<br>
}<br>
* as you can see here the model has to be instantiated first and then have the board be created before calling the render method from view that will then produce a string that shows a basic state of how the game board is being represented in that current state

This would be an example of how the outcome would look like using the textualView:
<br> &nbsp; &nbsp; &nbsp;  _ _ _ _<br>
&nbsp; &nbsp;  _ _ _ _ _ <br>
&nbsp; _ _ X O _ _  
_ _ O _ X _ _ <br>
&nbsp; _ _ X O _ _  <br>
&nbsp; &nbsp;   _ _ _ _ _<br>
&nbsp; &nbsp; &nbsp;  _ _ _ _<br>

## Key Components:
IView Interface- this interface provides the basis of what the general view compnonets are going to need to function in its entirity. Th interface is needed becasue if we want to create diffrent types of views, like textual view or the full GUI then it streamlines to have an interface for all fiffrent types of view<br>
MouseHandler class- this class is used to describe the actions that are going to be taken when the mouse is clicked on the game board<br>
ReversiGraphicsView class- this class is used to describe how the game board is being represented in a graphical manner. This class is driving the control flow as it is using the model to get the data that it needs to represent the game board. Inside of the class we also ahve it so that if the keys p is pressed then the player will pass thier turn however this is not fully implemented as we dont have the controller yet<br>
ReversiPanel class- this class is used to describe the panel that is being used to represent the game board. This class is driving the control flow as it is using the model to get the data that it needs to represent the game board<br>
TextualView class- this class is used to describe how the game board is being represented in a textual manner. This class is driving the control flow as it is using the model to get the data that it needs to represent the game board<br>




## Key Subcomponents:
### MouseHandler -
###### Attributes
1. `x` (Attribute)
   - An integer attribute that stores the x-coordinate of the mouse cursor.

2. `y` (Attribute)
   - An integer attribute that stores the y-coordinate of the mouse cursor.

###### Other Classes
3. `MouseEvent` (Class)
   - A class representing a mouse event, which contains information about the event, such as the position and the button clicked.

4. `MouseInputAdapter` (Class)
   - A class that provides a set of methods for handling mouse events, which `MouseHandler` extends.

###### Methods
5. `getX()` (Method)
   - A public method that returns the current x-coordinate of the mouse cursor.

6. `getY()` (Method)
   - A public method that returns the current y-coordinate of the mouse cursor.

7. `mouseClicked(MouseEvent mouseEvent)` (Method)
   - An overridden method that handles the mouse click event and prints information about the event, including the button clicked and coordinates.

8. `mousePressed(MouseEvent mouseEvent)` (Method)
   - An overridden method that handles the mouse button press event and prints information about the event, including the button clicked and coordinates.

9. `mouseReleased(MouseEvent mouseEvent)` (Method)
   - An overridden method that handles the mouse button release event and prints information about the event, including the button clicked and coordinates.

10. `mouseEntered(MouseEvent mouseEvent)` (Method)
   - An overridden method that handles the mouse enter event and prints information about the event, including the button clicked and coordinates.

11. `mouseExited(MouseEvent mouseEvent)` (Method)
   - An overridden method that handles the mouse exit event and prints information about the event, including the button clicked and coordinates.

These nouns represent the main components and attributes within the `MouseHandler` class and play a significant role in handling and providing information about mouse events in a GUI application.




### ReversiGraphicsView -

###### Attributes
1. `scrollPanel` (Attribute)
   - A JScrollPane that provides a scrollable view for the game board.

2. `buttonPanel` (Attribute)
   - A JPanel used for displaying buttons and user interface controls.

3. `input` (Attribute)
   - A JTextField for user input.

4. `commandCallback` (Attribute)
   - A Consumer<String> function used to handle user commands.

5. `panel` (Attribute)
   - An instance of the `ReversiPanel` class used for displaying the game board.

###### Constructor
6. `ReversiGraphicsView(ReversiModel model)` (Constructor)
   - Initializes the graphics view for the Reversi game.
   - Sets up the GUI components, including the title, size, layout, and scroll panel for the game board.
   - Registers a mouse listener for handling mouse events on the game board.

###### Methods
7. `makeVisible()` (Method)
   - Makes the graphics view visible to the user.

8. `showErrorMessage(String error)` (Method)
   - Displays an error message to the user.

9. `refresh()` (Method)
   - Refreshes the graphics view by repainting it.

10. `setBoard(List<List<Cell>> board)` (Method)
   - Sets the game board with the provided list of cells.

### ReversiPanel -
###### Attributes
1. `size` (Attribute)
   - An integer representing the size of the game board.

2. `model` (Attribute)
   - An instance of the `ReversiModel` class, representing the Reversi game model.

3. `a` (Attribute)
   - A double value used for geometric calculations.

###### Constructor
4. `ReversiPanel(int size, ReversiModel model)` (Constructor)
   - Initializes the Reversi panel with the specified size and game model.

###### Methods
5. `clickHelper(Point xy)` (Method)
   - Helper method to determine which game cell was clicked based on the provided coordinates.
   - Calculates the closest game cell to the clicked point and returns it.

6. `resetCells()` (Method)
   - Resets the clicked state of all game cells.

7. `dist(Posn point1, Posn point2)` (Method)
   - Calculates the distance between two `Posn` points.

8. `paintComponent(Graphics g)` (Method)
   - Overrides the `paintComponent` method to render the Reversi game board.
   - Uses `Graphics2D` for drawing the game board, cells, and pieces.

9. `getPath2D(CreateInitialHexagon result)` (Method)
   - Creates a `Path2D` object based on the provided `CreateInitialHexagon` result.

10. `getCreateInitialHexagon(List<List<Cell>> board)` (Method)
   - Calculates and returns an initial hexagon structure based on the game board size.
   - Uses geometric calculations to determine the positions of hexagon vertices.

### TextualView -
###### Attributes
1. `model` (Attribute)
   - An instance of the `ReversiModel` class to be represented in the textual view.

###### Constructor
2. `TextualView(ReversiModel model)` (Constructor)
   - Initializes a textual view for the Reversi game with the provided model.

###### Methods
3. `toString()` (Method)
   - Overrides the `toString()` method to generate a textual representation of the Reversi game board.
   - Constructs a String representing the game board using characters such as '_', 'X', and 'O' to represent cells with different FillColor values.

4. `makeVisible()` (Method)
   - A method that does nothing. (No specific implementation)

5. `showErrorMessage(String error)` (Method)
   - A method that does nothing. (No specific implementation)

6. `refresh()` (Method)
   - A method that does nothing. (No specific implementation)

7. `setBoard(List<List<Cell>> board)` (Method)
   - A method that does nothing. (No specific implementation)



### Source Organization:
The sorce code is split up into two sections, the model and the view. The model is split up into two sections, the
board and the players. Thr board is made up of the Reversi Intrface, the Cell Class the BasicReversi class, the
ABSTreversi class and the fill class. The player section is made up of the player interface and the player class.
The other section, the view, is made up of the textualView class.


### Class Invariant:
#### The board:
In the assignment we have declared the board to be the primary class invariant. The reason behind this would be that
the board does not change in size or its contents. Throughout th entire program the board will always be the same
size, it does not change, and it will have the same number of cells in the same x y coordinate plane regardless of
what actions occur. The only thing that changes is the state of the cells themselves, but the cells themselves do
not change or the board itself does not change.


