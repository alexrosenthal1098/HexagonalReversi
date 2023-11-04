package model.tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.awt.Color;
import java.awt.Polygon;
import java.util.Arrays;

/**
 * A class that holds tests for the PointyTopHexagon class.
 */
public class PointyTopHexagonTest {
  ReversiTile hexTile;

  @Before
  public void setUp() {
    this.hexTile = new PointyTopHexagon();
  }



  // tests for constructors
  @Test
  public void testEmptyConstructor() {
    ReversiTile tile = new PointyTopHexagon();
    Assert.assertFalse(tile.hasDisk());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCopyConstructorNullTile() {
    ReversiTile tile = new PointyTopHexagon(null);
  }

  @Test
  public void testCopyConstructorValidTile() {
    this.hexTile.placeDisk(Color.BLUE, Color.PINK);
    ReversiTile tile = new PointyTopHexagon(this.hexTile);
    Assert.assertTrue(tile.hasDisk());
    Assert.assertEquals(Color.BLUE, tile.getTopColor());
    tile.flipDisk();
    Assert.assertEquals(Color.PINK, tile.getTopColor());
  }



  // tests for hasDisk
  @Test
  public void testHasDiskBeforePlacingDisk() {
    Assert.assertFalse(this.hexTile.hasDisk());
  }

  @Test
  public void testHasDiskAfterPlacingDisk() {
    this.hexTile.placeDisk(Color.BLUE, Color.GREEN);
    Assert.assertTrue(this.hexTile.hasDisk());
  }

  @Test
  public void testHasDiskAfterFlippingDisk() {
    this.hexTile.placeDisk(Color.ORANGE, Color.RED);
    this.hexTile.flipDisk();
    Assert.assertTrue(this.hexTile.hasDisk());
  }



  // tests for placeDisk
  @Test(expected = IllegalArgumentException.class)
  public void testPlaceDiskNullTopColor() {
    this.hexTile.placeDisk(null, Color.BLACK);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlaceDiskNullBottomColor() {
    this.hexTile.placeDisk(Color.BLACK, null);
  }

  @Test(expected = IllegalStateException.class)
  public void testPlaceDiskAlreadyHasOne() {
    this.hexTile.placeDisk(Color.BLACK, Color.WHITE);
    this.hexTile.placeDisk(Color.YELLOW, Color.PINK);
  }

  @Test
  public void testPlaceDiskSameColorONEachSide() {
    this.hexTile.placeDisk(Color.BLUE, Color.BLUE);
    Assert.assertTrue(this.hexTile.hasDisk());
    Assert.assertEquals(Color.BLUE, this.hexTile.getTopColor());
    this.hexTile.flipDisk();
    Assert.assertEquals(Color.BLUE, this.hexTile.getTopColor());
  }

  @Test
  public void testPlaceDiskUpdatesHasDisk() {
    Assert.assertFalse(this.hexTile.hasDisk());
    this.hexTile.placeDisk(Color.CYAN, Color.LIGHT_GRAY);
    Assert.assertTrue(this.hexTile.hasDisk());
  }

  @Test
  public void testPlaceDiskUpdatesTopAndBottomColor() {
    this.hexTile.placeDisk(Color.BLACK, Color.WHITE);
    Assert.assertEquals(Color.BLACK, this.hexTile.getTopColor());
    this.hexTile.flipDisk();
    Assert.assertEquals(Color.WHITE, this.hexTile.getTopColor());
  }



  // tests for flipDisk
  @Test(expected = IllegalStateException.class)
  public void testFlipDiskNoDisk() {
    this.hexTile.flipDisk();
  }

  @Test
  public void testFlipDiskKeepsHasDiskTrue() {
    this.hexTile.placeDisk(Color.BLACK, Color.WHITE);
    this.hexTile.flipDisk();
    Assert.assertTrue(this.hexTile.hasDisk());
  }

  @Test
  public void testFlipDiskUpdatesColors() {
    this.hexTile.placeDisk(Color.MAGENTA, Color.DARK_GRAY);
    Assert.assertEquals(Color.MAGENTA, this.hexTile.getTopColor());
    this.hexTile.flipDisk();
    Assert.assertEquals(Color.DARK_GRAY, this.hexTile.getTopColor());
  }



  // tests for getTopColor
  @Test(expected = IllegalStateException.class)
  public void testGetTopColorNoDisk() {
    this.hexTile.getTopColor();
  }

  @Test
  public void testGetTopColorAfterPlacingDisk() {
    this.hexTile.placeDisk(Color.WHITE, Color.BLACK);
    Assert.assertEquals(Color.WHITE, this.hexTile.getTopColor());
  }

  @Test
  public void testGetTopColorAfterFlippingDisk() {
    this.hexTile.placeDisk(Color.WHITE, Color.BLACK);
    this.hexTile.flipDisk();
    Assert.assertEquals(Color.BLACK, this.hexTile.getTopColor());
  }
}