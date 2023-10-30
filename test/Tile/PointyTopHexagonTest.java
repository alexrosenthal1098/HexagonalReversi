package Tile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
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

  // tests for buildTile
  @Test(expected = IllegalArgumentException.class)
  public void testBuildTileNullCenter() {
    this.hexTile.buildTile(null, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuildTile0SideLength() {
    this.hexTile.buildTile(new Point(100, 100), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBuildTileNegativeSideLength() {
    this.hexTile.buildTile(new Point(100, 100), -20);
  }

  @Test
  public void testBuildTileSideLength10() {
    Polygon hexagon = this.hexTile.buildTile(new Point(100, 100), 10);
    int[] xPoints = {108, 100, 91, 91, 100, 108};
    int[] yPoints = {105, 110, 105, 95, 90, 95};

    Assert.assertArrayEquals(xPoints, Arrays.copyOfRange(hexagon.xpoints, 0, 6));
    Assert.assertArrayEquals(yPoints, Arrays.copyOfRange(hexagon.ypoints, 0, 6));
  }

  @Test
  public void testBuildTileSideLength50() {
    Polygon hexagon = this.hexTile.buildTile(new Point(200, 200), 50);
    int[] xPoints = {243, 200, 156, 156, 200, 243};
    int[] yPoints = {225, 250, 225, 175, 150, 174};

    Assert.assertArrayEquals(xPoints, Arrays.copyOfRange(hexagon.xpoints, 0, 6));
    Assert.assertArrayEquals(yPoints, Arrays.copyOfRange(hexagon.ypoints, 0, 6));
  }

  @Test
  public void testBuildTileNegativeCenter() {
    Polygon hexagon = this.hexTile.buildTile(new Point(-50, -50), 15);
    int[] xPoints = {-37, -50, -62, -62, -50, -37};
    int[] yPoints = {-42, -35, -42, -57, -65, -57};

    Assert.assertArrayEquals(xPoints, Arrays.copyOfRange(hexagon.xpoints, 0, 6));
    Assert.assertArrayEquals(yPoints, Arrays.copyOfRange(hexagon.ypoints, 0, 6));
  }

  @Test
  public void testBuildTileContainsCenter() {
    Polygon hexagon = this.hexTile.buildTile(new Point(75, 80), 10);
    Assert.assertTrue(hexagon.contains(new Point(75, 80)));
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