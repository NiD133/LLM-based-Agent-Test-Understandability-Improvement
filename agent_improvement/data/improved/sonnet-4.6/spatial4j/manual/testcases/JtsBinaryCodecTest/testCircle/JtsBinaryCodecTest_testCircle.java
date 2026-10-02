package org.locationtech.spatial4j.io;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.jts.JtsBinaryCodec;
import org.locationtech.spatial4j.shape.Shape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that a circle (BUFFER shape) survives a binary encode/decode round-trip
 * using JtsBinaryCodec configured with single-precision floating-point storage.
 */
public class JtsBinaryCodecTest_testCircle {

    private JtsSpatialContext ctx;
    private JtsBinaryCodec binaryCodec;

    @Before
    public void setUp() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        ctx = (JtsSpatialContext) factory.newSpatialContext();
        binaryCodec = new JtsBinaryCodec(ctx, factory);
    }

    /** Parses a WKT/extended-WKT string into a Shape using the configured context. */
    private Shape parseWkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes {@code shape} to a byte buffer with the binary codec, reads it back,
     * and asserts that the decoded shape equals the original.
     */
    private void assertRoundTrip(Shape shape) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(baos), shape);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Shape decoded = binaryCodec.readShape(new DataInputStream(bais));

        assertEquals(shape, decoded);
    }

    @Test
    public void testCircle() throws Exception {
        Shape circle = parseWkt("BUFFER(POINT(-10 30), 5.2)");
        assertRoundTrip(circle);
    }
}
