package org.locationtech.spatial4j.io;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class GeohashUtils_ESTest_test2 extends GeohashUtils_ESTest_scaffolding {

    /**
     * getSubGeohashes appends each of the 32 base-32 characters to the given
     * base geohash, so the result should contain exactly 32 sub-geohashes.
     */
    @Test(timeout = 4000)
    public void getSubGeohashesReturnsOnePerBase32Character() throws Throwable {
        String baseGeohash = "7zzzzzzzzzzz";

        String[] subGeohashes = GeohashUtils.getSubGeohashes(baseGeohash);

        int expectedBase32CharacterCount = 32;
        assertEquals(expectedBase32CharacterCount, subGeohashes.length);
    }
}
