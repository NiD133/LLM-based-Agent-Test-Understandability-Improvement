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
public class GeohashUtils_ESTest_test2 extends GeohashUtils_ESTest_scaffolding {

    /**
     * getSubGeohashes appends each of the 32 base-32 characters to the given
     * base geohash, so the returned array should always contain 32 entries.
     */
    @Test(timeout = 4000)
    public void getSubGeohashesReturnsOneEntryPerBase32Character() throws Throwable {
        String baseGeohash = "7zzzzzzzzzzz";

        String[] subGeohashes = GeohashUtils.getSubGeohashes(baseGeohash);

        int expectedBase32AlphabetSize = 32;
        assertEquals(expectedBase32AlphabetSize, subGeohashes.length);
    }
}
