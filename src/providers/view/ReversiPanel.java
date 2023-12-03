package providers.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import providers.model.board.Fill;
import providers.model.board.Posn;
import providers.model.board.ReadonlyReversiModel;


/**
 * This class represents a view for the Reversi game.
 */
public class ReversiPanel extends JPanel {
  private final ReadonlyReversiModel model;
  private double a;

  /**
   * Constructs a ReversiGraphicsView.
   *
   * @param size  is the size of the board
   * @param model is the model
   */
  ReversiPanel(int size, ReadonlyReversiModel model) {
    this.model = model;
  }

  /**
   * gets the cell that has been clicked.
   *
   * @param xy is the point that has been clicked
   * @return the cell that has been clicked
   */
  protected Cell clickHelper(Point xy) {
    List<List<Cell>> board = model.getBoard();
    double dist = dist(board.get(0).get(0).getCenter(board.get(0).size(),
            this.getWidth()), new Posn(xy.x, xy.y - a));
    Cell closest = board.get(0).get(0);
    // goes through the board and sees which cells it is in the distance of
    for (int row = 0; row < board.size(); row++) {
      for (int column = 0; column < board.get(row).size(); column++) {
        Posn center = board.get(row).get(column).getCenter(board.get(0).size(), this.getWidth());
        if (dist(center, new Posn(xy.x, xy.y + -a)) < dist) {
          dist = dist(center, new Posn(xy.x, xy.y - a));
          closest = board.get(row).get(column);
        }
      }
    }
    // finds the cell that is closest to the clicked position
    if (closest.clicked()) {
      resetCells();
      closest.setClicked(false);
      return new Cell();
    }
    // makes sure this distance is less than the value of a
    if (dist < a) {
      resetCells();
      System.out.println("Coordinates of the cell clicked are: " + closest.getLogicalPosn());
      return closest;
    } else {
      // if its not then u didnt click on an adequate cell
      resetCells();
      System.out.println("You didnt click on a cell!");
      return new Cell();
    }
  }

  /**
   * resets the cells to not be clicked.
   */
  private void resetCells() {
    List<List<Cell>> board = model.getBoard();
    // resets the whole board to having no cells clicked
    for (int row = 0; row < board.size(); row++) {
      for (int column = 0; column < board.get(row).size(); column++) {
        board.get(row).get(column).setClicked(false);
      }
    }
  }

  /**
   * gets the distance between two points.
   *
   * @param point1 is the first point
   * @param point2 is the second point
   * @return the distance between the two points
   */
  private double dist(Posn point1, Posn point2) {
    // calculates the distance
    return Math.sqrt(Math.pow(point1.getX() - point2.getX(), 2)
            + Math.pow(point1.getY() - point2.getY(), 2));
  }

  /**
   * paints the board.
   *
   * @param g is the graphics
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponents(g);

    // paints the board
    Graphics2D g2d = (Graphics2D) g;
    List<List<Cell>> board = model.getBoard();

    String scoreText = "Player1 Score: " + model.getScore(board, "BLACK") + "    Player2 Score: "
            + model.getScore(board, "WHITE");
    g2d.drawString(scoreText, 0, this.getHeight() - (this.getWidth() / 10) + 15);

    a = board.get(0).get(0).getA(board.get(0).size(), this.getWidth());
    g2d.setColor(Color.BLACK);

    // translates and scales the board around the center of the top left cell
    g2d.translate(0, a);
    g2d.scale(1, -1);

    // initializes the first hexagon we will end up trasnforming (top left hexagon)
    CreateInitialHexagon result = getCreateInitialHexagon(board);

    // makes a new path from this result then draws it
    Path2D path = getPath2D(result);
    g2d.draw(path);

    int yDistFromCenter;
    for (int row = 0; row < board.size(); row++) {
      yDistFromCenter = Math.abs(((board.size() - 1) / 2) - row);
      // checks distance from center to see if its an even or odd row
      for (int column = 0; column < board.get(row).size(); column++) {
        Path2D newPath = (Path2D) path.clone();
        // transforms the original top left cell into the new position it should be at
        newPath.transform(AffineTransform.getTranslateInstance((a * Math.sqrt(3)
                        * (yDistFromCenter / 2.0)) + column * a * Math.sqrt(3) -
                        (a * Math.sqrt(3)) * (Math.abs(((board.size() - 1) / 4.0))),
                -1 * row * 1.5 * a));
        g2d.setColor(Color.BLUE);
        g2d.fill(newPath);
        g2d.setColor(Color.BLACK);
        g2d.draw(newPath);

        // if the hexagon is clicked then make it a certain color
        if (board.get(row).get(column).clicked()) {
          g2d.setColor(Color.PINK);
          g2d.fill(newPath);
          g2d.setColor(Color.BLACK);
        }

        // initializing the circles on top of cells if they have a placement there
        if (board.get(row).get(column).getColor() == Fill.FillColor.BLACK) {
          Posn center = board.get(row).get(column).getCenter(board.get(0).size(), this.getWidth());
          g2d.fillOval((int) center.getX() - 10, -1 * (int) (center.getY() - a) - 10, 20, 20);
        } else if (board.get(row).get(column).getColor() == Fill.FillColor.WHITE) {
          g2d.setColor(Color.WHITE);
          Posn center = board.get(row).get(column).getCenter(board.get(0).size(), this.getWidth());
          g2d.fillOval((int) center.getX() - 10, -1 * (int) (center.getY() - a) - 10, 20, 20);
          g2d.setColor(Color.BLACK);
        }


      }
    }




    if (model.getColor().equals("WHITE")) {
      g2d.setColor(Color.WHITE);
    }
    else {
      g2d.setColor(Color.BLACK);
    }
    g2d.fillRect(0, - this.getHeight() + 10, this.getWidth(), this.getWidth() / 10);
    g2d.setColor(Color.BLACK);
  }

  /**
   * gets the path of the hexagon.
   *
   * @param result is the result of the hexagon
   * @return the path of the hexagon
   */
  private static Path2D getPath2D(CreateInitialHexagon result) {
    Path2D path = new Path2D.Double();

    List<Double> lox = new ArrayList<Double>(List.of(result.UL.getX(),
            result.U.getX(), result.UR.getX(),
            result.LR.getX(), result.L.getX(), result.LL.getX()));
    List<Double> loy = new ArrayList<Double>(List.of(result.UL.getY(),
            result.U.getY(), result.UR.getY(),
            result.LR.getY(), result.L.getY(), result.LL.getY()));

    path.moveTo(lox.get(0), loy.get(0));
    for (int i = 1; i < lox.size(); ++i) {
      path.lineTo(lox.get(i), loy.get(i));
    }
    path.closePath();
    return path;
  }

  /**
   * gets the initial hexagon. (top left hexagon)
   *
   * @param board is the board
   * @return the initial hexagon
   */
  private CreateInitialHexagon getCreateInitialHexagon(List<List<Cell>> board) {
    int yDistFromCenter = Math.abs(((board.size() - 1) / 2));
    double center = a * Math.sqrt(3) * (yDistFromCenter / 2.0);
    double shifter = (a * Math.sqrt(3) / 2);

    Posn u = new Posn(center + shifter, a);
    Posn uL = new Posn(center + -1 * (Math.cos(Math.toRadians(30)) * a) + shifter,
            Math.sin(Math.toRadians(30)) * a);
    Posn uR = new Posn(center + Math.cos(Math.toRadians(30)) * a + shifter,
            Math.sin(Math.toRadians(30)) * a);
    Posn l = new Posn(center + shifter,
            -1 * a);
    Posn lL = new Posn(center + -1 * (Math.cos(Math.toRadians(30)) * a) + shifter,
            -1 * (Math.sin(Math.toRadians(30)) * a));
    Posn lR = new Posn(center + Math.cos(Math.toRadians(30)) * a + shifter,
            -1 * (Math.sin(Math.toRadians(30)) * a));
    CreateInitialHexagon result = new CreateInitialHexagon(u, uL, uR, l, lL, lR);
    return result;
  }

  /**
   * This class represents a hexagon (we use it to represent the initial).
   */
  private static class CreateInitialHexagon {
    public final Posn U;
    public final Posn UL;
    public final Posn UR;
    public final Posn L;
    public final Posn LL;
    public final Posn LR;

    /**
     * Constructs a CreateInitialHexagon.
     *
     * @param u  is the upper point
     * @param uL is the upper left point
     * @param uR is the upper right point
     * @param l  is the lower point
     * @param lL is the lower left point
     * @param lR is the lower right point
     */
    public CreateInitialHexagon(Posn u, Posn uL, Posn uR, Posn l, Posn lL, Posn lR) {
      this.U = u;
      this.UL = uL;
      this.UR = uR;
      this.L = l;
      this.LL = lL;
      this.LR = lR;
    }
  }
}
