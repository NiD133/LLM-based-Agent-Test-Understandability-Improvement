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
     * lookupDegreesSizeForHashLen indexes into fixed-size lookup tables sized for
     * hash lengths 0..MAX_PRECISION (24). A hash length far beyond that range, such
     * as 116, indexes past the end of those arrays and must raise
     * ArrayIndexOutOfBoundsException rather than returning a bogus size.
     */
    @Test(timeout = 4000)
    public void lookupDegreesSizeForHashLenRejectsLengthBeyondMaxPrecision() throws Throwable {
        int hashLenBeyondTable = 116;
        try {
            GeohashUtils.lookupDegreesSizeForHashLen(hashLenBeyondTable);
            fail("Expected ArrayIndexOutOfBoundsException for hash length " + hashLenBeyondTable);
        } catch (ArrayIndexOutOfBoundsException e) {
            // The out-of-bounds access happens inside GeohashUtils' lookup tables.
            verifyException("org.locationtech.spatial4j.io.GeohashUtils", e);
        }
    }
}
