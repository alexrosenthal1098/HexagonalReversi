package view.gui;

import java.awt.Point;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JComponent;



import model.ReadOnlyReversiModel;
import model.tile.ReversiTile;
import util.HexReversiUtils;

/**
 * A GUI representation of a HexagonalReversi game board. Can be dynamically resized and
 * extended to alter the drawing of the tiles and disks.
 */
public class HexagonalBoard extends JPanel implements ReversiBoard, MouseListener {
  //          FIELDS
  //////////////////////////////////////////

  // all fields are declared protected so that subclasses can change how the board looks
  protected final Color BACKGROUND_COLOR = new Color(50, 50, 50); // the background color
  protected final Color TILE_COLOR = new Color(180, 180, 180); // the tile color
  protected final Color SELECTED_TILE_COLOR = Color.CYAN; // the tile color

  protected final Color OUTLINE_COLOR = Color.BLACK; // the color of each tile's outline

  protected final ReadOnlyReversiModel model; // read-only version of the model this view represents

  protected final Map<Point, Shape> tiles; // a map of point on the board to tile shape
  protected final Map<Shape, Color> disks; // a map of disk shape to disk color

  // the location of the tile that is currently selected, or an empty point if no tile is selected
  protected Point selectedTile;
  protected final List<BoardListener> listeners; // a list of listeners to this board


  /**
   * A constructor for a hexagonal board that takes in the boards width and height along with
   * a read-only model to construct the board from.
   * @param width The width of the board in pixels.
   * @param height The height of the board in pixels.
   * @param model The ReadOnlyReversiModel to create a board from.
   */
  public HexagonalBoard(int width, int height, ReadOnlyReversiModel model) {
    if (model == null) { // check if the model is null and throw exception if it is
      throw new IllegalArgumentException("Cannot create a board from a null model.");
    }
    if (width <= 0 || height <= 0) { // check if the width or height are not positive
      throw new IllegalArgumentException("Dimensions of the panel must be positive.");
    }
    this.model = model; // set the model
    this.tiles = new HashMap<>(); // initialize the tile map
    this.disks = new HashMap<>(); // initialize the disk map
    this.selectedTile = new EmptyPoint(); // set the selectedTile to an empty point
    this.listeners = new ArrayList<>(); // initialize the listeners list

    // initialize the keySet of the tiles map using the model
    for (Point tilePoint : this.model.getTiles().keySet()) { // iterate over all tile points
      this.tiles.put(tilePoint, null); // put the point in the map with null as a placeholder
    }

    // add actions for the 'm' and 'p' keys
    Action makeMove = new AbstractAction() { // define an action for making a move
      public void actionPerformed(ActionEvent e) {
        System.out.println("Move made.");
        for (BoardListener listener : listeners) {
          listener.moveMade(getSelectedTile()); // inform each listener that a move has been made
        }
      }
    };
    Action passTurn = new AbstractAction() { // define an action for passing the turn
      public void actionPerformed(ActionEvent e) {
        System.out.println("Turn passed.");
        for (BoardListener listener : listeners) {
          listener.turnPassed(); // inform each listener that the turn has been passed
        }
      }
    };
    // assign the makeMove action to the 'm' key and the passTurn action to the 'p' key
    this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke('m'), "makeMove");
    this.getActionMap().put("makeMove", makeMove);
    this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke('p'), "passTurn");
    this.getActionMap().put("passTurn", passTurn);

    addMouseListener(this); // add a mouse listener for this board

    setPreferredSize(new Dimension(width, height)); // set the size of the model
    setBackground(this.BACKGROUND_COLOR); // set the background color
  }



  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g); // call super
    Graphics2D g2d = (Graphics2D) g; // cast graphics object to Graphics2D

    this.updateBoard(); // update the state of the board

    for (Point tilePoint : this.tiles.keySet()) { // iterate over all the tile points
      g2d.setColor( // change color to the tile or selected tile color if this point is selected
              this.selectedTile.equals(tilePoint) ? this.SELECTED_TILE_COLOR : this.TILE_COLOR);
      g2d.fill(this.tiles.get(tilePoint)); // fill in the shape of the tile
      g2d.setColor(this.OUTLINE_COLOR); // change color to the outline color
      g2d.draw(this.tiles.get(tilePoint)); // draw the outline of the tile

    }
    for (Shape disk : this.disks.keySet()) { // iterate over all the disk shapes
      g2d.setColor(this.disks.get(disk)); // set the color to the disks color using a map
      g2d.fill(disk); // fill in the shape of the disk
    }
  }



  //          INTERFACE METHODS
  //////////////////////////////////////////
  @Override
  public void selectTile(Point tileLocation) throws IllegalArgumentException,
          IllegalStateException {
    if (tileLocation == null) { // check if the given point is null
      throw new IllegalArgumentException("Tile location cannot be null.");
    }
    if (!this.tiles.containsKey(tileLocation)) { // check if the given point is on the board
      throw new IllegalStateException("The given tile location is not on the board.");
    }
    if (!this.selectedTile.equals(new EmptyPoint())) { // check if there already is a tile selected
      throw new IllegalStateException("There is already a tile selected.");
    }

    // copy the given point and set our selected tile to it
    this.selectedTile = new Point(tileLocation.x, tileLocation.y);
  }

  @Override
  public void deselectCurrentTile() {
    this.selectedTile = new EmptyPoint(); // replace the currently selected tile with an empty point
  }

  @Override
  public Point getSelectedTile() throws IllegalStateException {
    if (this.selectedTile.equals(new EmptyPoint())) { // check if there is no selected tile
      throw new IllegalStateException("There is no tile currently selected.");
    }
    // return a copy of the selected tile point
    return new Point(this.selectedTile.x, this.selectedTile.y);
  }

  @Override
  public void addListener(BoardListener listener) {
    if (listener == null) { // check if listener and throw exception if it is
      throw new IllegalArgumentException("Cannot register a null listener.");
    }
    this.listeners.add(listener); // add the given listener to our list of listeners
  }


  //          HELPER METHODS
  //////////////////////////////////////////
  // all helpers are declared protected so that subclasses can change how the board is set up
  // and updated along with details of how the tiles and disks are drawn

  // updates the board by calculating the shape of each tile and disk depending on the
  // size of the window
  protected void updateBoard() {
    this.disks.clear(); // clear the current disks

    Map<Point, ReversiTile> board = this.model.getTiles(); // get the map of tiles from the model

    for (Point point : this.model.getTiles().keySet()) {  // iterate over all point on the board
      double tileSide = this.getTileSideLength(); // get the tile side length from a helper

      // calculate the coordinates of the center of the tile. this formula was adapted from the
      // "Hex to Pixel" section of the website linked in the README
      double centerX = ((double) this.getSize().width / 2) +
              (tileSide * (Math.sqrt(3) * point.x + Math.sqrt(3) / 2 * point.y));
      double centerY = ((double) this.getSize().height / 2) +
              (tileSide * (3.0 / 2 * point.y));

      // use a helper to create the tile and put it in the tiles map, which replaces the tile that
      // was previously in that location
      this.tiles.put(point, this.buildTile(centerX, centerY, tileSide));

      ReversiTile tile = board.get(point); // get the tile from the board

      if (tile.hasDisk()) { // if the tile has a disk
        // use a helper to create the disk and put it in the disks map along with the disk color
        this.disks.put(this.buildDisk(centerX, centerY, tileSide / 2), tile.getTopColor());
      }
    }
  }


  // creates the shape of the tile in double precision with the given length, in pixels, of each
  // side and centered at the given x and y position.
  protected Shape buildTile(double xPos, double yPos, double sideLength) {
    // ensure the side length is positive and given tile is not null
    if (sideLength <= 0) {
      throw new IllegalArgumentException("Side length must be positive and tile can't be null");
    }

    Path2D.Double hexagon = new Path2D.Double(); // create a new Path2D with double precision

    // iterate over the number of sides, an extra side is included so that the path pointer can
    // start and end at the same location
    for (int sideNum = 0; sideNum < 7; sideNum++) {
      // calculate x and y position by adding the x and y component of the side length to the center
      double centerX = xPos + sideLength * Math.cos((sideNum * Math.PI / 3) - (Math.PI / 6));
      double centerY = yPos + sideLength * Math.sin((sideNum * Math.PI / 3) - (Math.PI / 6));
      if (sideNum == 0) { // if side number is 0, move the pointer to that starting location
        hexagon.moveTo(centerX, centerY);
      }
      else { // for every other side number (of which there are 6),
        hexagon.lineTo(centerX, centerY); // draw the line for that side
      }
    }
    hexagon.closePath();
    return hexagon; // return the path of the hexagon
  }

  // creates a shape that represents a circular disk centered at the given x and y coordinates
  // and with the given radius, in pixels.
  protected Shape buildDisk(double centerX, double centerY, double radius) {
    double xPos = centerX - radius; // calculate the x position of the circle
    double yPos = centerY - radius; // calculate the y position of the circle
    // create and return the circle
    return new Ellipse2D.Double(xPos, yPos, radius * 2, radius * 2);
  }

  // use the size of the board to calculate the appropriate tile side length
  protected double getTileSideLength() {
    Dimension size = this.getSize(); // get the size of the panel
    double minSide = Math.min(size.width, size.height); // find the smallest dimension
    // calculate and return an appropriate side length for each individual tile's side length
    return minSide /
            (HexReversiUtils.getBoardSideLength(this.model.getTiles()) * 2 - 1) /
            Math.sqrt(3);
  }


  //          MOUSE LISTENER METHODS
  //////////////////////////////////////////
  @Override
  public void mouseClicked(MouseEvent e) {
    // event not used
  }

  @Override
  public void mousePressed(MouseEvent e) {
    boolean clickedOnBoard = false; // initialize boolean for whether the user clicked on the board
    for (Point tilePoint : this.tiles.keySet()) { // iterate through all tile points on the board
      // if the shape of the tile contains the position of the mouse click
      if (this.tiles.get(tilePoint).contains(e.getPoint())) {
        clickedOnBoard = true; // the board has been clicked on
        System.out.printf("User clicked on the tile at (%d, %d)\n", tilePoint.x, tilePoint.y);
        // only tiles without a disk can be selected, so
        // if the tile that was clicked does not have a disk
        if (!this.model.getTileAt(tilePoint.x, tilePoint.y).hasDisk()) {
          if (this.selectedTile.equals(tilePoint)) { // if the clicked tile is the selected one
            this.deselectCurrentTile(); // deselect the tile
          }
          else { // if the clicked tile is NOT the selected one
            this.deselectCurrentTile(); // deselect the current tile
            this.selectTile(tilePoint); // and select the new one
          }
        }
        break; // we know which tile has been clicked on, so break out of the loop for efficiency
      }
    }

    if (!clickedOnBoard) { // if the user did not click on the board
      System.out.println("User clicked off the board.");
      this.deselectCurrentTile(); // deselect the current tile
    }
    this.repaint(); // repaint the board to show the changes
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // event not used
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // event not used
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // event not used
  }
}
