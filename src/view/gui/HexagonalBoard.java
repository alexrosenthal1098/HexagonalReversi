package view.gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.ReadOnlyReversiModel;
import model.tile.ReversiTile;

/**
 * A GUI representation of a HexagonalReversi game board. Can be dynamically resized and
 * extended to alter the drawing of the tiles and disks.
 */
public class HexagonalBoard extends JPanel implements ReversiBoard {
  //          FIELDS
  //////////////////////////////////////////
  protected final Color BACKGROUND_COLOR = new Color(50, 50, 50); // the background color
  protected final Color TILE_COLOR = new Color(180, 180, 180); // the tile color
  protected final Color OUTLINE_COLOR = Color.BLACK; // the color of each tile's outline


  private final ReadOnlyReversiModel model; // read-only version of the model this view represents
  // a map of point in the model to tiles that represents the board tiles
  protected final Map<Point, Shape> tiles;
  protected final Map<Shape, Color> disks; // a map of disk shape to disk color



  //          CONSTRUCTORS
  //////////////////////////////////////////
  public HexagonalBoard(int width, int height, ReadOnlyReversiModel model) {
    if (model == null) { // check if the model is null and throw exception if it is
      throw new IllegalArgumentException("Cannot create a board from a null model.");
    }
    this.model = model; // set the model
    this.tiles = new HashMap<>(); // initialize the tile map
    this.disks = new HashMap<>(); // initialize the disk map
    this.initBoard(); // initialize the key set of the tiles map

    setSize(new Dimension(width, height));
    setBackground(this.BACKGROUND_COLOR); // set the background color
    System.out.println(this.getSize());
  }



  //          OVERRIDE METHODS
  //////////////////////////////////////////
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    this.updateBoard();

    for (Shape tile : this.tiles.values()) {
      g2d.setColor(this.TILE_COLOR);
      g2d.fill(tile);
      g2d.setColor(this.OUTLINE_COLOR);
      g2d.draw(tile);

    }
    for (Shape disk : this.disks.keySet()) {
      g2d.setColor(this.disks.get(disk));
      g2d.fill(disk);
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



  //          HELPER METHODS
  //////////////////////////////////////////
  // initializes the keySet of the tiles map so that we only have to find all the tiles on the
  // board once. doing this ets the updateBoard method already know what all the tile points are.
  protected void initBoard() {
    int boardSide = this.model.getBoardSideLength();
    for (int r = -boardSide + 1; r <= boardSide - 1; r++) {
      for (int q = Math.min(-boardSide + 1, -boardSide + 1 - r);
           q <= Math.min(boardSide - 1, boardSide - 1 - r); q++) {
        try {
          this.model.getTileAt(q, r); // try to get the current tiles
        }
        catch (IllegalArgumentException ignored) {
          continue; // if there is no tile at the current point, continue to the next point
        }
        // if there is a tile at the current point, initialize a key in the tiles map
        this.tiles.put(new Point(q, r), new Path2D.Double());
      }
    }
  }

  // updates the board by calculating the shape of each tile and disk depending on the
  // size of the window
  protected void updateBoard() {
    this.disks.clear();

    for (Point point : this.tiles.keySet()) {
      ReversiTile tile = this.model.getTileAt(point.x, point.y);

      double tileSide = this.getTileSideLength();
      double centerX = ((double) this.getSize().width / 2) +
              (tileSide * (Math.sqrt(3) * point.x + Math.sqrt(3) / 2 * point.y));
      double centerY = ((double) this.getSize().height / 2) +
              (tileSide * (3.0 / 2 * point.y));
      this.tiles.put(point, this.buildTile(centerX, centerY, tileSide));

      if (tile.hasDisk()) {
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
    double diameter = radius * 2;
    double xPos = centerX - radius;
    double yPos = centerY - radius;
    Ellipse2D.Double circle = new Ellipse2D.Double(xPos, yPos, diameter, diameter);
    return circle;
  }

  // use the size of the board to calculate the appropriate tile side length
  protected double getTileSideLength() {
    Dimension size = this.getSize();
    int boardSize = this.model.getBoardSideLength();
    double minSide = Math.min(size.width, size.height);
    return minSide / (boardSize * 2 - 1) / Math.sqrt(3);
  }
}
