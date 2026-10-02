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
     * getSubGeohashes should return every child geohash one level below the given
     * base geohash. Because geohashes use a base-32 alphabet, there are exactly
     * 32 children, one per possible next character.
     */
    @Test(timeout = 4000)
    public void getSubGeohashesReturnsOneChildPerBase32Character() throws Throwable {
        String baseGeohash = "7zzzzzzzzzzz";

        String[] childGeohashes = GeohashUtils.getSubGeohashes(baseGeohash);

        int expectedChildCount = 32;
        assertEquals(expectedChildCount, childGeohashes.length);
    }
}
