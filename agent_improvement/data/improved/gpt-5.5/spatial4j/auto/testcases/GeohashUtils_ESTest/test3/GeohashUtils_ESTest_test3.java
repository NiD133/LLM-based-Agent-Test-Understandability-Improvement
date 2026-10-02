package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test3 extends GeohashUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void decodesMixedCaseGeohashWithGeoContext() throws Throwable {
        SpatialContext geoContext = SpatialContext.GEO;

        Point decodedPoint = GeohashUtils.decode("dQ;Z", geoContext);

        assertEquals((-74.70703125), decodedPoint.getLon(), 0.01);
        assertEquals(37.880859375, decodedPoint.getLat(), 0.01);
    }
}
