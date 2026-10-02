package org.locationtech.spatial4j.io;

import org.junit.Before;
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

public class JtsBinaryCodecTest_testPoint {

    private SpatialContext context;
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        context = newSinglePrecisionJtsContext();
        binaryCodec = context.getBinaryCodec();
    }

    private SpatialContext newSinglePrecisionJtsContext() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory.newSpatialContext();
    }

    private Shape wkt(String wkt) {
        try {
            return context.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void assertRoundTrip(Shape shape) throws Exception {
        assertRoundTrip(shape, true);
    }

    private void assertRoundTrip(Shape shape, boolean andEquals) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(output), shape);

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        Shape restoredShape = binaryCodec.readShape(new DataInputStream(input));

        assertEquals(shape, restoredShape);
    }

    @Test
    public void testPoint() throws Exception {
        assertRoundTrip(wkt("POINT(-10 80.3)"));
    }
}
