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
public class GeohashUtils_ESTest_test4 extends GeohashUtils_ESTest_scaffolding {

    /**
     * Verifies that lookupDegreesSizeForHashLen throws ArrayIndexOutOfBoundsException
     * when given a hash length that exceeds the maximum supported precision (MAX_PRECISION = 24).
     * The internal lookup arrays are sized MAX_PRECISION+1, so index 116 is well out of bounds.
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // Hash length 116 far exceeds the internal array size (MAX_PRECISION+1 = 25),
        // so direct array access at index 116 must throw ArrayIndexOutOfBoundsException.
        try {
            GeohashUtils.lookupDegreesSizeForHashLen(116);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            verifyException("org.locationtech.spatial4j.io.GeohashUtils", e);
        }
    }
}
