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
public class GeohashUtils_ESTest_test1 extends GeohashUtils_ESTest_scaffolding {

    private static final double LON_ERROR_DEGREES = 3147.26;
    private static final double LAT_ERROR_DEGREES = 0.0;
    private static final int MAX_HASH_LENGTH = 24;

    @Test(timeout = 4000)
    public void lookupHashLenReturnsMaximumPrecisionWhenLatitudeErrorIsZero() throws Throwable {
        int hashLength = GeohashUtils.lookupHashLenForWidthHeight(LON_ERROR_DEGREES, LAT_ERROR_DEGREES);

        assertEquals(MAX_HASH_LENGTH, hashLength);
    }
}
