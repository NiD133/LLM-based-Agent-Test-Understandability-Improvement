package org.locationtech.spatial4j.io;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Verifies that JtsBinaryCodec can round-trip a rectangular envelope: a shape
 * written to binary and then read back must equal the original.
 *
 * The context uses a FLOATING_SINGLE precision model so that JtsBinaryCodec
 * serialises coordinates as floats rather than doubles — matching the
 * production configuration exercised by this test.
 */
public class JtsBinaryCodecTest_testRect {

    private BinaryCodec binaryCodec;
    private org.locationtech.spatial4j.context.SpatialContext ctx;

    @Before
    public void setUp() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        ctx = factory.newSpatialContext();
        binaryCodec = ctx.getBinaryCodec();
    }

    /**
     * A rectangular envelope should survive a binary round-trip unchanged.
     */
    @Test
    public void testRect() throws Exception {
        Shape rect = parseWkt("ENVELOPE(-10, 180, 42.3, 0)");
        assertRoundTrip(rect);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /** Parse a WKT string into a Shape using the current spatial context. */
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
     * Write {@code shape} to binary and read it back; assert the result equals
     * the original.
     */
    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(baos), shape);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Shape deserialized = binaryCodec.readShape(new DataInputStream(bais));

        assertEquals(shape, deserialized);
    }
}
