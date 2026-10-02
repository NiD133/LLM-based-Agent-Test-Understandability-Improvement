package org.locationtech.spatial4j.io;

import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.spatial4j.context.SpatialContext;
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

public class JtsBinaryCodecTest_testCircle {

    private SpatialContext ctx;
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        JtsSpatialContextFactory factory = singlePrecisionJtsFactory();
        ctx = factory.newSpatialContext();
        binaryCodec = new JtsBinaryCodec((JtsSpatialContext) ctx, factory);
    }

    private JtsSpatialContextFactory singlePrecisionJtsFactory() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory;
    }

    protected Shape wkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void assertRoundTrip(Shape shape) throws IOException {
        ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(outputBytes), shape);

        ByteArrayInputStream inputBytes = new ByteArrayInputStream(outputBytes.toByteArray());
        Shape decodedShape = binaryCodec.readShape(new DataInputStream(inputBytes));

        assertEquals(shape, decodedShape);
    }

    @Test
    public void testCircle() throws Exception {
        assertRoundTrip(wkt("BUFFER(POINT(-10 30), 5.2)"));
    }
}
