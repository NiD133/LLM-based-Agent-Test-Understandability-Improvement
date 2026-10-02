package org.locationtech.spatial4j.io;

import com.carrotsearch.randomizedtesting.RandomizedTest;
import org.junit.Before;
import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.jts.JtsGeometry;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JtsBinaryCodecTest_testPoly extends RandomizedTest {

    private JtsSpatialContext ctx;
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        ctx = (JtsSpatialContext) initContext();
        binaryCodec = ctx.getBinaryCodec();
    }

    public SpatialContext initContext() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory.newSpatialContext();
    }

    Geometry randomGeometry(int points) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory(ctx.getGeometryFactory());
        shapeFactory.setCentre(new Coordinate(0, 0));
        shapeFactory.setSize(180);
        shapeFactory.setNumPoints(points);
        return shapeFactory.createCircle();
    }

    protected void assertRoundTrip(Shape shape, boolean andEquals) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(output), shape);

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        assertEquals(shape, binaryCodec.readShape(new DataInputStream(input)));
    }

    public boolean shouldBeEqualAfterRoundTrip() {
        return true;
    }

    protected final void assertRoundTrip(Shape shape) throws IOException {
        assertRoundTrip(shape, shouldBeEqualAfterRoundTrip());
    }

    @Test
    public void testPoly() throws Exception {
        final JtsGeometry shape = ctx.makeShape(randomGeometry(randomIntBetween(3, 20)), false, false);
        assertRoundTrip(shape);
    }
}
