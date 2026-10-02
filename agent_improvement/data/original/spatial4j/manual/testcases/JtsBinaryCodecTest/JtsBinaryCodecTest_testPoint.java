package org.locationtech.spatial4j.io;

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
import org.locationtech.spatial4j.shape.ShapeCollection;
import java.io.*;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import com.carrotsearch.randomizedtesting.RandomizedTest;

public class JtsBinaryCodecTest_testPoint {

    public SpatialContext initContext() {
        JtsSpatialContextFactory factory = new JtsSpatialContextFactory();
        factory.precisionModel = new PrecisionModel(PrecisionModel.FLOATING_SINGLE);
        return factory.newSpatialContext();
    }

    protected Shape randomShape() {
        if (randomInt(3) == 0) {
            JtsSpatialContext ctx = (JtsSpatialContext) super.ctx;
            return ctx.makeShape(randomGeometry(randomIntBetween(3, 20)), false, false);
        } else {
            return __super_randomShape();
        }
    }

    Geometry randomGeometry(int points) {
        //a circle
        JtsSpatialContext ctx = (JtsSpatialContext) super.ctx;
        GeometricShapeFactory gsf = new GeometricShapeFactory(ctx.getGeometryFactory());
        gsf.setCentre(new Coordinate(0, 0));
        //diameter
        gsf.setSize(180);
        gsf.setNumPoints(points);
        return gsf.createCircle();
    }

    private SpatialContext __super_initContext() {
        return SpatialContext.GEO;
    }

    protected void assertRoundTrip(Shape shape, boolean andEquals) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        binaryCodec.writeShape(new DataOutputStream(baos), shape);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        assertEquals(shape, binaryCodec.readShape(new DataInputStream(bais)));
    }

    protected T ctx;

    protected BinaryCodec binaryCodec;

    private T __super_initContext();

    public boolean shouldBeEqualAfterRoundTrip() {
        return true;
    }

    /**
     * Convenience to read static data.
     */
    protected Shape wkt(String wkt) {
        try {
            return ctx.getFormats().getWktReader().read(wkt);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Shape __super_randomShape() {
        switch(//inclusive
        randomInt(2)) {
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

    protected final void assertRoundTrip(Shape shape) throws Exception {
        assertRoundTrip(shape, shouldBeEqualAfterRoundTrip());
    }

    private void __super_assertRoundTrip(Shape shape, boolean andEquals) throws Exception;

    @Test
    public void testPoint() throws Exception {
        assertRoundTrip(wkt("POINT(-10 80.3)"));
    }
}
