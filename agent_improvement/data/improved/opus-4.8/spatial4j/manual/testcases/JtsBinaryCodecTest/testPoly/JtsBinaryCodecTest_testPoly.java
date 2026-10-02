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
import org.locationtech.spatial4j.shape.jts.JtsGeometry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link JtsBinaryCodec} can write a JTS polygon shape to a binary
 * stream and read it back unchanged (a "round trip").
 *
 * <p>The polygon under test is a circle approximated by a random number of points,
 * which exercises the codec's WKB-based handling of arbitrary JTS geometries.
 */
public class JtsBinaryCodecTest_testPoly extends RandomizedTest {

    /** Spatial context configured for single-precision floating point coordinates. */
    private JtsSpatialContext ctx;

    /** Codec under test, obtained from the context. */
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        ctx = (JtsSpatialContext) factory.newSpatialContext();
        binaryCodec = ctx.getBinaryCodec();
    }

    @Test
    public void testPoly() throws Exception {
        // A circle approximated by a random number of points (between 3 and 20, inclusive).
        JtsGeometry circle = ctx.makeShape(randomCircle(randomIntBetween(3, 20)), false, false);
        assertRoundTrip(circle);
    }

    /** Builds a circle centred at the origin with the given number of boundary points. */
    private Geometry randomCircle(int numPoints) {
        GeometricShapeFactory gsf = new GeometricShapeFactory(ctx.getGeometryFactory());
        gsf.setCentre(new Coordinate(0, 0));
        gsf.setSize(180); // diameter
        gsf.setNumPoints(numPoints);
        return gsf.createCircle();
    }

    /** Writes the shape to bytes, reads it back, and asserts the result equals the original. */
    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(out), shape);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Shape readBack = binaryCodec.readShape(new DataInputStream(in));

        assertEquals(shape, readBack);
    }
}
