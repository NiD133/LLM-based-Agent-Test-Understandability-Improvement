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
     * Verifies that arrayIsCached returns false when the CachedArray stored for an array key
     * was built from a different-sized array, causing a lastKnownSize mismatch.
     *
     * The cache considers an array stale when knownArrays contains an entry for it but
     * cachedArray.lastKnownSize() != array.length. Here we deliberately engineer that
     * situation by inserting a CachedArray (built from a 4-element array) under the key
     * of a 7-element array, so the size check fails and arrayIsCached must return false.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();

        // The array used as the lookup key has 7 elements.
        String[] sevenElementArray = new String[7];

        // The CachedArray will record lastKnownSize = 4 (the length of this array).
        String[] fourElementArray = new String[4];

        // Access the internal map directly to set up the mismatched state.
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays =
                cache.knownArrays;

        // Build a CachedArray that remembers size 4, then store it under the 7-element key.
        SegmentConstantPoolArrayCache.CachedArray cachedArrayForFour =
                cache.new CachedArray(fourElementArray);
        knownArrays.put(sevenElementArray, cachedArrayForFour);

        // Sanity-check: the CachedArray does report size 4.
        assertEquals(4, cachedArrayForFour.lastKnownSize());

        // Because lastKnownSize (4) != sevenElementArray.length (7), the cache entry is
        // considered stale and arrayIsCached must return false.
        boolean isCached = cache.arrayIsCached(sevenElementArray);
        assertFalse(isCached);
    }
}
