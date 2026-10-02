package org.locationtech.spatial4j.io;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that {@link org.locationtech.spatial4j.io.jts.JtsBinaryCodec} can serialize a
 * shape to bytes and read it back unchanged (a "round trip").
 */
public class JtsBinaryCodecTest_testPoint {

    private JtsSpatialContext ctx;
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        // A JTS-backed context using single-precision floats, matching the codec's expectations.
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        ctx = factory.newSpatialContext();
        binaryCodec = ctx.getBinaryCodec();
    }

    @Test
    public void testPoint() throws Exception {
        assertRoundTrip(parseWkt("POINT(-10 80.3)"));
    }

    /** Parses a shape from its Well-Known Text representation. */
    private Shape parseWkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /** Writes the shape to bytes, reads it back, and asserts the result equals the original. */
    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(bytesOut), shape);

        ByteArrayInputStream bytesIn = new ByteArrayInputStream(bytesOut.toByteArray());
        Shape readBack = binaryCodec.readShape(new DataInputStream(bytesIn));

        assertEquals(shape, readBack);
    }
}
