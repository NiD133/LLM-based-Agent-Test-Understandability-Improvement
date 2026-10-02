package org.locationtech.spatial4j.context;

import org.locationtech.spatial4j.context.jts.DatelineRule;
import org.locationtech.spatial4j.context.jts.JtsSpatialContext;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.context.jts.ValidationRule;
import org.locationtech.spatial4j.distance.CartesianDistCalc;
import org.locationtech.spatial4j.distance.GeodesicSphereDistCalc;
import org.locationtech.spatial4j.io.ShapeIO;
import org.locationtech.spatial4j.io.WKTReader;
import org.locationtech.spatial4j.shape.impl.RectangleImpl;
import org.junit.After;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SpatialContextFactoryTest_testFormatsConfig {

    public static final String PROP = "SpatialContextFactory";

    @After
    public void tearDown() {
        System.getProperties().remove(PROP);
    }

    private SpatialContext call(String... argsStr) {
        Map<String, String> args = new HashMap<>();
        for (int i = 0; i < argsStr.length; i += 2) {
            String key = argsStr[i];
            String val = argsStr[i + 1];
            args.put(key, val);
        }
        return SpatialContextFactory.makeSpatialContext(args, getClass().getClassLoader());
    }

    @Test
    public void testFormatsConfig() {
        JtsSpatialContext ctx = (JtsSpatialContext) call("spatialContextFactory", JtsSpatialContextFactory.class.getName(), "readers", CustomWktShapeParser.class.getName());
        assertTrue(ctx.getFormats().getReader(ShapeIO.WKT) instanceof CustomWktShapeParser);
    }
}
