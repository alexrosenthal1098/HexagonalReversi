package view.gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.ReadOnlyReversiModel;
import model.tile.ReversiTile;
import util.HexReversiUtils;

/**
 * A GUI representation of a HexagonalReversi game board. Can be dynamically resized and
 * extended to alter the drawing of the tiles and disks.
 */
public class HexagonalBoard extends JPanel implements ReversiBoard {
  //          FIELDS
  //////////////////////////////////////////

  // all fields are declared protected so that subclasses can change how the board looks
  protected final Color BACKGROUND_COLOR = new Color(50, 50, 50); // the background color
  protected final Color TILE_COLOR = new Color(180, 180, 180); // the tile color
  protected final Color OUTLINE_COLOR = Color.BLACK; // the color of each tile's outline

  protected final ReadOnlyReversiModel model; // read-only version of the model this view represents

  protected final Map<Point, Shape> tiles; // a map of point on the board to tile shape
  protected final Map<Shape, Color> disks; // a map of disk shape to disk color



  //          CONSTRUCTORS
  //////////////////////////////////////////
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
    this.initBoard(); // initialize the key set of the tiles map

    setSize(new Dimension(width, height)); // set the size of the model
    setBackground(this.BACKGROUND_COLOR); // set the background color
  }



  //          OVERRIDE METHODS
  //////////////////////////////////////////
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g); // call super
    Graphics2D g2d = (Graphics2D) g; // cast graphics object to Graphics2D

    this.updateBoard(); // update the state of the board

    for (Shape tile : this.tiles.values()) { // iterate over all the tile shapes
      g2d.setColor(this.TILE_COLOR); // change color to the tile color
      g2d.fill(tile); // fill in the shape of the tile
      g2d.setColor(this.OUTLINE_COLOR); // change color to the outline color
      g2d.draw(tile); // draw the outline of the tile

    }
    for (Shape disk : this.disks.keySet()) { // iterate over all the disk shapes
      g2d.setColor(this.disks.get(disk)); // set the color to the disks color using a map
      g2d.fill(disk); // fill in the shape of the disk
    }
  }



  //          INTERFACE METHODS
  //////////////////////////////////////////
  @Override
  public void selectTile(Point tileLocation) throws IllegalArgumentException, IllegalStateException {

  }

  @Override
  public void deselectCurrentTile() {

  }

  @Override
  public Point getSelectedTile() throws IllegalStateException {
    return null;
  }

  @Override
  public void addFeature(BoardFeatures feature) {

  }


  //          HELPER METHODS
  //////////////////////////////////////////
  // all helpers are declared protected so that subclasses can change how the board is set up
  // and updated along with details of how the tiles and disks are drawn

  // initializes the keySet of the tiles map so that we only have to find all the tiles on the
  // board once. doing this allows the updateBoard method to already know what all the tile points
  // are, which makes it easier and more efficient to update the board.
  protected void initBoard() {
    for (Point tilePoint : this.model.getTiles().keySet()) { // iterate over all tile points
      this.tiles.put(tilePoint, null); // put the point in the map with null as a placeholder
    }
  }

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

    // todo try going back to the width < height thing but this time use diagonal to calculate height
    // with trigonometry
  }
}
