package org.locationtech.spatial4j.io;

import org.junit.Test;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link BinaryCodec} can serialize a rectangle to bytes and read
 * an equal rectangle back (a "round trip"), using a JTS-backed context with
 * single-precision (float) coordinates.
 */
public class JtsBinaryCodecTest_testRect {

    private final SpatialContext ctx = createSingleFloatPrecisionContext();
    private final BinaryCodec binaryCodec = ctx.getBinaryCodec();

    /**
     * Builds a JTS spatial context that stores coordinates as 32-bit floats
     * (FLOATING_SINGLE), matching the precision the round trip is exercised at.
     */
    private static SpatialContext createSingleFloatPrecisionContext() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory.newSpatialContext();
    }

    /** Parses a WKT string into a shape using the context's WKT reader. */
    private Shape wkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes the shape to bytes via the codec, reads it back, and asserts the
     * decoded shape equals the original.
     */
    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(out), shape);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Shape decoded = binaryCodec.readShape(new DataInputStream(in));

        assertEquals(shape, decoded);
    }

    @Test
    public void testRect() throws Exception {
        assertRoundTrip(wkt("ENVELOPE(-10, 180, 42.3, 0)"));
    }
}
