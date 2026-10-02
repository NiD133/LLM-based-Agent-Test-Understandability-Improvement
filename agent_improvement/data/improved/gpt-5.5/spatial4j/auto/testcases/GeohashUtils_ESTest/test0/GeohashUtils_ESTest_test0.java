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
public class GeohashUtils_ESTest_test0 extends GeohashUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void returnsPrecisionThreeForSevenDegreeWidthAndHeight() throws Throwable {
        double longitudeErrorDegrees = 7;
        double latitudeErrorDegrees = 7;

        int hashLength = GeohashUtils.lookupHashLenForWidthHeight(
                longitudeErrorDegrees,
                latitudeErrorDegrees);

        assertEquals(3, hashLength);
    }
}
