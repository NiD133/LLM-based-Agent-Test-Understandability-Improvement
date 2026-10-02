package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.IdentityHashMap;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentConstantPoolArrayCache_ESTest_test2 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    /**
     * Verifies that arrayIsCached() returns false when the cached entry's lastKnownSize
     * does not match the actual length of the array being looked up.
     *
     * The scenario: a CachedArray is built from a 4-element array but manually inserted
     * into knownArrays under the key of a 7-element array. When arrayIsCached() checks
     * the 7-element array, it finds a CachedArray whose lastKnownSize is 4, which does
     * not equal 7, so the cache is considered stale and the method returns false.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Arrange: create a cache and two differently-sized arrays
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] sevenElementArray = new String[7];
        String[] fourElementArray  = new String[4];

        // Build a CachedArray from the 4-element array (lastKnownSize == 4)
        SegmentConstantPoolArrayCache.CachedArray cachedArrayForFour =
                cache.new CachedArray(fourElementArray);

        // Sanity-check the cached size before injecting the mismatch
        assertEquals(4, cachedArrayForFour.lastKnownSize());

        // Manually insert a stale/mismatched entry: key is the 7-element array,
        // but the CachedArray records size 4
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays =
                cache.knownArrays;
        knownArrays.put(sevenElementArray, cachedArrayForFour);

        // Act: check whether the 7-element array is considered correctly cached
        boolean isCached = cache.arrayIsCached(sevenElementArray);

        // Assert: the size mismatch (4 != 7) makes the cache entry stale, so false
        assertFalse(isCached);
    }
}
