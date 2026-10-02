package org.locationtech.spatial4j.io;

import com.carrotsearch.randomizedtesting.RandomizedTest;
import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.ShapeCollection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that a {@link ShapeCollection} survives a write/read round trip through the
 * JTS-aware {@link BinaryCodec}: encoding a shape to bytes and decoding it again must
 * yield an equal shape.
 */
public class JtsBinaryCodecTest_testCollection extends RandomizedTest {

  /** Diameter, in degrees, of the circle used as a random JTS geometry. */
  private static final int CIRCLE_DIAMETER_DEGREES = 180;

  private JtsSpatialContext ctx;
  private BinaryCodec binaryCodec;

  @Before
  public void setUp() {
    ctx = createSingleFloatPrecisionContext();
    binaryCodec = ctx.getBinaryCodec();
  }

  @Test
  public void testCollection() throws Exception {
    ShapeCollection<Shape> collection =
        ctx.makeCollection(Arrays.asList(randomShape(), randomShape(), randomShape()));

    assertRoundTrip(collection);
  }

  /** A JTS spatial context that stores coordinates as single-precision floats. */
  private JtsSpatialContext createSingleFloatPrecisionContext() {
    JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
    factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
    return factory.newSpatialContext();
  }

  /**
   * Picks a shape at random: roughly one time in three a JTS circle geometry, otherwise one
   * of a few fixed WKT shapes.
   */
  private Shape randomShape() {
    if (randomInt(3) == 0) {
      Geometry circle = randomCircle(randomIntBetween(3, 20));
      return ctx.makeShape(circle, false, false);
    }
    return randomWktShape();
  }

  /** Builds a circle geometry centred at the origin with the given number of vertices. */
  private Geometry randomCircle(int numPoints) {
    GeometricShapeFactory gsf = new GeometricShapeFactory(ctx.getGeometryFactory());
    gsf.setCentre(new Coordinate(0, 0));
    gsf.setSize(CIRCLE_DIAMETER_DEGREES);
    gsf.setNumPoints(numPoints);
    return gsf.createCircle();
  }

  /** Returns one of the predefined WKT shapes, chosen at random. */
  private Shape randomWktShape() {
    switch (randomInt(2)) { // randomInt's bound is inclusive, so this yields 0, 1, or 2
      case 0:
        return wkt("POINT(-10 80.3)");
      case 1:
        return wkt("ENVELOPE(-10, 180, 42.3, 0)");
      case 2:
        return wkt("BUFFER(POINT(-10 30), 5.2)");
      default:
        throw new Error();
    }
  }

  /** Parses a WKT string into a shape, wrapping checked failures as runtime exceptions. */
  private Shape wkt(String wkt) {
    try {
      return ctx.getFormats().getWktReader().read(wkt);
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /** Asserts that writing the shape and reading it back produces an equal shape. */
  private void assertRoundTrip(Shape shape) throws IOException {
    ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
    binaryCodec.writeShape(new DataOutputStream(bytesOut), shape);

    ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytesOut.toByteArray());
    assertEquals(shape, binaryCodec.readShape(new DataInputStream(bytesIn)));
  }
}
