package org.locationtech.spatial4j.context;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory;
import org.locationtech.spatial4j.shape.impl.ShapeFactoryImpl;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SpatialContextFactory_ESTest_test20 extends SpatialContextFactory_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        HashMap<String, String> emptyConfiguration = new HashMap<String, String>();
        SpatialContext defaultSpatialContext = SpatialContextFactory.makeSpatialContext(emptyConfiguration, (ClassLoader) null);
        JtsSpatialContextFactory jtsFactory = new JtsSpatialContextFactory();

        // Building the default JTS formats is expected to fail for the same constructor lookup path.
        try {
            jtsFactory.makeFormats(defaultSpatialContext);
            fail("Expecting exception: RuntimeException");
        } catch (RuntimeException expectedException) {
            //
            // org.evosuite.runtime.mock.java.lang.MockThrowable: class org.locationtech.spatial4j.io.jts.JtsGeoJSONWriter needs a constructor that takes: [SpatialContext{geo=true, calculator=Haversine, worldBounds=Rect(minX=-180.0,maxX=180.0,minY=-90.0,maxY=90.0)}, org.locationtech.spatial4j.context.jts.JtsSpatialContextFactory@10]
            //
            verifyException("org.locationtech.spatial4j.context.SpatialContextFactory", expectedException);
        }
    }
}
