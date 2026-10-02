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
import org.locationtech.spatial4j.io.jts.JtsBinaryCodec;
import org.locationtech.spatial4j.shape.Shape;
import org.locationtech.spatial4j.shape.ShapeCollection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JtsBinaryCodecTest_testCollection extends RandomizedTest {

    private JtsSpatialContext ctx;
    private BinaryCodec binaryCodec;

    @Before
    public void setUp() {
        ctx = initContext();
        binaryCodec = new JtsBinaryCodec(ctx, createSinglePrecisionFactory());
    }

    private JtsSpatialContext initContext() {
        return (JtsSpatialContext) createSinglePrecisionFactory().newSpatialContext();
    }

    private JtsSpatialContextFactory createSinglePrecisionFactory() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory;
    }

    protected Shape randomShape() {
        if (randomInt(3) == 0) {
            return ctx.makeShape(randomGeometry(randomIntBetween(3, 20)), false, false);
        }
        return randomNonJtsShape();
    }

    private Shape randomNonJtsShape() {
        switch (randomInt(2)) {
            case 0:
                return wkt("POINT(-10 80.3)");
            case 1:
                return wkt("ENVELOPE(-10, 180, 42.3, 0)");
            case 2:
                return wkt("BUFFER(POINT(-10 30), 5.2)");
            default:
                throw new Error();
        }
    }

    private Geometry randomGeometry(int points) {
        GeometricShapeFactory shapeFactory = new GeometricShapeFactory(ctx.getGeometryFactory());
        shapeFactory.setCentre(new Coordinate(0, 0));
        shapeFactory.setSize(180);
        shapeFactory.setNumPoints(points);
        return shapeFactory.createCircle();
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

    protected void assertRoundTrip(Shape shape, boolean andEquals) throws IOException {
        ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(outputBuffer), shape);

        ByteArrayInputStream inputBuffer = new ByteArrayInputStream(outputBuffer.toByteArray());
        assertEquals(shape, binaryCodec.readShape(new DataInputStream(inputBuffer)));
    }

    protected final void assertRoundTrip(Shape shape) throws Exception {
        assertRoundTrip(shape, shouldBeEqualAfterRoundTrip());
    }

    public boolean shouldBeEqualAfterRoundTrip() {
        return true;
    }

    @Test
    public void testCollection() throws Exception {
        ShapeCollection<Shape> collection = ctx.makeCollection(Arrays.asList(
                randomShape(),
                randomShape(),
                randomShape()));

        assertRoundTrip(collection);
    }
}
