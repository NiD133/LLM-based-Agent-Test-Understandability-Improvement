package org.locationtech.spatial4j.io;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.io.jts.JtsBinaryCodec;
import org.locationtech.spatial4j.shape.Shape;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Tests that JtsBinaryCodec can round-trip shapes through binary serialization.
 * Uses FLOATING_SINGLE precision to exercise the float-based write/read path.
 */
public class JtsBinaryCodecTest_testPoint {

    private JtsSpatialContext ctx;
    private JtsBinaryCodec binaryCodec;

    @Before
    public void setUp() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        ctx = (JtsSpatialContext) factory.newSpatialContext();
        binaryCodec = (JtsBinaryCodec) ctx.getBinaryCodec();
    }

    private Shape parseWkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes a shape to bytes and reads it back, asserting the decoded shape equals the original.
     */
    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(baos), shape);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        Shape decoded = binaryCodec.readShape(new DataInputStream(bais));

        assertEquals(shape, decoded);
    }

    @Test
    public void testPoint() throws Exception {
        Shape point = parseWkt("POINT(-10 80.3)");
        assertRoundTrip(point);
    }
}
