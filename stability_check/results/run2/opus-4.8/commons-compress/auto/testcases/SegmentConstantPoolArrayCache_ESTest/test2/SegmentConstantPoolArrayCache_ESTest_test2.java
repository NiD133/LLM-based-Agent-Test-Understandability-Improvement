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
     * arrayIsCached returns false when the cache entry stored for an array
     * describes a different-sized array. Here the entry is keyed by a
     * 7-element array but was built from a 4-element array, so the recorded
     * lastKnownSize (4) does not match the queried array's length (7).
     */
    @Test(timeout = 4000)
    public void arrayIsCachedReturnsFalseWhenCachedSizeDiffersFromArrayLength() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] queriedArray = new String[7];
        String[] cachedContentArray = new String[4];

        // Build a cache entry from the 4-element array, then deliberately store
        // it under the unrelated 7-element array as its key.
        SegmentConstantPoolArrayCache.CachedArray cachedArray = cache.new CachedArray(cachedContentArray);
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays = cache.knownArrays;
        knownArrays.put(queriedArray, cachedArray);

        // The entry remembers the size of the array it was built from.
        assertEquals(4, cachedArray.lastKnownSize());

        // Sizes disagree (4 recorded vs. 7 queried), so the cache is stale.
        boolean cached = cache.arrayIsCached(queriedArray);
        assertFalse(cached);
    }
}
