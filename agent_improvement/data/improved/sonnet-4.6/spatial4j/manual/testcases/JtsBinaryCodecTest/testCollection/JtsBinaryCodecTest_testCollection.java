package org.locationtech.spatial4j.io;

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
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Tests that a ShapeCollection containing JTS geometry shapes and standard
 * spatial shapes survives a binary codec round-trip (write then read) unchanged.
 */
public class JtsBinaryCodecTest_testCollection {

    private JtsSpatialContext ctx;
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        ctx = (JtsSpatialContext) factory.newSpatialContext();
        binaryCodec = ctx.getBinaryCodec();
    }

    /**
     * Creates a circle JTS geometry shape with the given number of approximation points,
     * centred at the origin with diameter 180.
     */
    private Shape makeCircleGeometryShape(int numPoints) {
        GeometricShapeFactory gsf = new GeometricShapeFactory(ctx.getGeometryFactory());
        gsf.setCentre(new Coordinate(0, 0));
        gsf.setSize(180);
        gsf.setNumPoints(numPoints);
        Geometry circle = gsf.createCircle();
        return ctx.makeShape(circle, false, false);
    }

    /** Parses a WKT string into a Shape using the context's WKT reader. */
    private Shape parseWkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Serialises {@code shape} to bytes and deserialises it, asserting the result equals the original. */
    private void assertRoundTrip(Shape shape) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(baos), shape);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Shape deserialized = binaryCodec.readShape(new DataInputStream(bais));

        assertEquals(shape, deserialized);
    }

    /**
     * Verifies that a ShapeCollection containing a mix of shape types (JTS geometry
     * circle, a point, and an envelope) survives a binary codec round-trip unchanged.
     */
    @Test
    public void testCollection() throws Exception {
        Shape circleShape = makeCircleGeometryShape(10);
        Shape pointShape = parseWkt("POINT(-10 80.3)");
        Shape envelopeShape = parseWkt("ENVELOPE(-10, 180, 42.3, 0)");

        ShapeCollection<Shape> collection = ctx.makeCollection(
            Arrays.asList(circleShape, pointShape, envelopeShape)
        );

        assertRoundTrip(collection);
    }
}
