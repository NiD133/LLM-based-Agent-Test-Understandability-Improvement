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
     * Verifies that arrayIsCached returns false when the CachedArray's lastKnownSize
     * does not match the actual length of the array used as the cache key.
     *
     * This simulates a stale/mismatched cache entry: the cache maps a 7-element array
     * to a CachedArray that was built from a different 4-element array. Since the sizes
     * differ, arrayIsCached must treat the entry as invalid and return false.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();

        // The array that will be used as the cache key (7 elements)
        String[] sevenElementArray = new String[7];

        // A different, smaller array used to create the CachedArray (4 elements)
        String[] fourElementArray = new String[4];

        // Build a CachedArray from the 4-element array; its lastKnownSize will be 4
        SegmentConstantPoolArrayCache.CachedArray cachedArrayForFourElements =
                cache.new CachedArray(fourElementArray);

        // Confirm the CachedArray records the size of its source array (4)
        assertEquals(4, cachedArrayForFourElements.lastKnownSize());

        // Manually inject a mismatched entry: key is the 7-element array, but the
        // CachedArray was created from the 4-element array (sizes don't match)
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays =
                cache.knownArrays;
        knownArrays.put(sevenElementArray, cachedArrayForFourElements);

        // arrayIsCached should return false because lastKnownSize (4) != array.length (7)
        boolean isCached = cache.arrayIsCached(sevenElementArray);
        assertFalse(isCached);
    }
}
