package org.locationtech.spatial4j.io;

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
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JtsBinaryCodecTest_testRect {

    private final JtsSpatialContext context;
    private final BinaryCodec binaryCodec;

    public JtsBinaryCodecTest_testRect() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        context = (JtsSpatialContext) factory.newSpatialContext();
        binaryCodec = new JtsBinaryCodec(context, factory);
    }

    @Test
    public void testRect() throws Exception {
        assertRoundTrip(wkt("ENVELOPE(-10, 180, 42.3, 0)"));
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

    private void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(baos), shape);

        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        assertEquals(shape, binaryCodec.readShape(new DataInputStream(bais)));
    }
}
