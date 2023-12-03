package providers.view;


import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JFrame;
import javax.swing.JScrollPane;

import providers.controller.ViewListener;
import providers.model.board.ICell;
import providers.model.board.ReadonlyReversiModel;

/**
 * This class represents a view for the Reversi game.
 */
public class ReversiGraphicsView extends JFrame implements IView {
  private List<ViewListener> listeners = new ArrayList<>();
  private final ReversiPanel panel;

  private ICell current;

  /**
   * Constructs a ReversiGraphicsView.
   *
   * @param model is the model
   */
  public ReversiGraphicsView(ReadonlyReversiModel model) {
    this.setTitle("Reversi");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // setting up the layout/panel
    this.setLayout(new BorderLayout());
    panel = new ReversiPanel(model.getSize(), model);
    panel.setFocusable(true);
    panel.setPreferredSize(new Dimension(500, 500));
    JScrollPane scrollPanel = new JScrollPane(panel);
    this.add(scrollPanel, BorderLayout.CENTER);
    this.pack();

    // adding a key listener to the panel
    panel.addKeyListener(new KeyListener() {

      @Override
      public void keyTyped(KeyEvent e) {
        // do nothing
      }

      @Override
      public void keyPressed(KeyEvent e) {
        // do nothing
      }

      // preparing to add these methods when we make the controller
      @Override
      public void keyReleased(KeyEvent e) {
        System.out.println("pressed");
        if (e.getKeyChar() == 'p') {
          for (ViewListener listener : listeners) {
            listener.onViewEvent(null);
          }
        }
        if (e.getKeyChar() == 'e') {
          notifyListener(current);
        }
        refresh();
      }
    });

    // construcing the mouse listener for clicks on cells
    panel.addMouseListener(new MouseListener() {

      /**
       * Invoked when the mouse button has been clicked.
       * @param e the event to be processed
       */
      @Override
      public void mouseClicked(MouseEvent e) {
        // do nothing
      }

      /**
       * Invoked when a mouse button has been pressed on a component.
       * @param e the event to be processed
       */
      @Override
      public void mousePressed(MouseEvent e) {
        // do nothing
      }

      /**
       * Invoked when a mouse button has been released on a component.
       * @param e the event to be processed
       */
      @Override
      public void mouseReleased(MouseEvent e) {
        // calling the clickhelper within the panel
        ICell cell = panel.clickHelper(getMousePosition());
        if (cell == null) {
          current = null;
        }
        cell.setClicked(true);
        refresh();
        current = cell;
      }

      /**
       * Invoked when the mouse enters a component.
       * @param e the event to be processed
       */
      @Override
      public void mouseEntered(MouseEvent e) {
        // do nothing
      }

      /**
       * Invoked when the mouse exits a component.
       * @param e the event to be processed
       */
      @Override
      public void mouseExited(MouseEvent e) {
        // do nothing
      }
    });
  }

  /**
   * sets visible as true.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * shows the error message.
   *
   * @param error is the error message
   */
  @Override
  public void showErrorMessage(String error) {
    // do nothing
  }

  /**
   * refreshes the view.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * sets the board.
   *
   * @param board is the board
   */
  @Override
  public void setBoard(List<List<ICell>> board) {
    // do nothing
  }

  @Override
  public void addListener(ViewListener vl) {
    listeners.add(vl);
  }

  @Override
  public void notifyListener(ICell cell) {
    for (ViewListener listener : listeners) {
      listener.onViewEvent(cell);
    }
  }
}
