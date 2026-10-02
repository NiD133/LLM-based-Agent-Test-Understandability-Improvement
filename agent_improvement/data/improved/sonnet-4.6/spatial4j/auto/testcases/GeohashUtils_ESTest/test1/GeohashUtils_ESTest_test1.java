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

    /**
     * When latErr is 0.0 no hash length can satisfy the height constraint
     * (all lat-heights are positive), so the method falls through to return
     * MAX_PRECISION (24) as a sentinel "best effort" value.
     */
    @Test(timeout = 4000)
    public void lookupHashLenForWidthHeight_returnsMaxPrecision_whenLatErrIsZero() throws Throwable {
        double lonErr = 3147.26;
        double latErr = 0.0;

        int hashLen = GeohashUtils.lookupHashLenForWidthHeight(lonErr, latErr);

        assertEquals(GeohashUtils.MAX_PRECISION, hashLen);
    }
}
