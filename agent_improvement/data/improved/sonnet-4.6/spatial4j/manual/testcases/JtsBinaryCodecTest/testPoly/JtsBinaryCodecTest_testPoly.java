package org.locationtech.spatial4j.io;

import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.jts.JtsBinaryCodec;
import org.locationtech.spatial4j.shape.jts.JtsGeometry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Tests that a JTS polygon geometry survives a binary encode/decode round-trip
 * through JtsBinaryCodec when the context uses FLOATING_SINGLE precision.
 *
 * The codec's FLOATING_SINGLE path stores coordinates as floats rather than
 * doubles; this test verifies the WKB write/read cycle preserves the shape.
 */
public class JtsBinaryCodecTest_testPoly {

    // Fixed point count in the [3, 20] range used by the original test.
    private static final int CIRCLE_NUM_POINTS = 10;

    // Diameter of the test circle in degrees (matches the original helper).
    private static final double CIRCLE_DIAMETER = 180;

    /**
     * Creates a JtsSpatialContextFactory configured with FLOATING_SINGLE precision,
     * which causes JtsBinaryCodec to encode coordinate dimensions as floats.
     */
    private JtsSpatialContextFactory createFloatPrecisionFactory() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory;
    }

    /**
     * Builds a circular polygon approximated by {@code numPoints} vertices,
     * centred at (0, 0) with the configured diameter.
     */
    private Geometry buildCircleGeometry(JtsSpatialContext ctx, int numPoints) {
        GeometricShapeFactory gsf = new GeometricShapeFactory(ctx.getGeometryFactory());
        gsf.setCentre(new Coordinate(0, 0));
        gsf.setSize(CIRCLE_DIAMETER);
        gsf.setNumPoints(numPoints);
        return gsf.createCircle();
    }

    /**
     * Serialises {@code shape} to bytes via {@code codec}, deserialises those
     * bytes, and asserts the result equals the original shape.
     */
    private void assertRoundTrip(JtsBinaryCodec codec, JtsGeometry shape) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        codec.writeShape(new DataOutputStream(buffer), shape);

        ByteArrayInputStream encoded = new ByteArrayInputStream(buffer.toByteArray());
        assertEquals(shape, codec.readShape(new DataInputStream(encoded)));
    }

    @Test
    public void testPoly() throws Exception {
        JtsSpatialContextFactory factory = createFloatPrecisionFactory();
        JtsSpatialContext ctx = (JtsSpatialContext) factory.newSpatialContext();
        JtsBinaryCodec codec = new JtsBinaryCodec(ctx, factory);

        Geometry circleGeometry = buildCircleGeometry(ctx, CIRCLE_NUM_POINTS);
        // false, false: skip dateline-180 check and multi-polygon overlap check
        JtsGeometry shape = ctx.makeShape(circleGeometry, false, false);

        assertRoundTrip(codec, shape);
    }
}
