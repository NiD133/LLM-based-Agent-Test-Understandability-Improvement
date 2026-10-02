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
 * Verifies that {@link BinaryCodec} can serialize a JTS-backed circle (a buffered
 * point) to bytes and read it back unchanged.
 */
public class JtsBinaryCodecTest_testCircle {

    /** JTS-backed context using single-precision floats, matching the codec under test. */
    private final SpatialContext ctx = createJtsContext();

    /** The binary codec provided by the context; performs the shape (de)serialization. */
    private final BinaryCodec binaryCodec = ctx.getBinaryCodec();

    private static SpatialContext createJtsContext() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory.newSpatialContext();
    }

    /** Parses a WKT string into a shape using the context's WKT reader. */
    private Shape parseWkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Writes the shape to bytes and reads it back, asserting the result equals the original. */
    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(out), shape);

        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        Shape decoded = binaryCodec.readShape(new DataInputStream(in));

        assertEquals(shape, decoded);
    }

    @Test
    public void testCircle() throws Exception {
        assertRoundTrip(parseWkt("BUFFER(POINT(-10 30), 5.2)"));
    }
}
