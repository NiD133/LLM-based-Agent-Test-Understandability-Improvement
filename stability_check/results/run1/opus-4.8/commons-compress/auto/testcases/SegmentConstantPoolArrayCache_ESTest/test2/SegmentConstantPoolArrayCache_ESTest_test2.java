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
     * arrayIsCached() must return false when the cached entry's recorded size
     * does not match the array's current length, even if an entry is present.
     *
     * Here a CachedArray built from a 4-element array (so lastKnownSize == 4) is
     * stored under a 7-element array key. Because 4 != 7, the cache is considered
     * stale and arrayIsCached() reports the array as not cached.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();

        String[] lookupKeyArray = new String[7];
        String[] cachedContentArray = new String[4];

        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays = cache.knownArrays;
        SegmentConstantPoolArrayCache.CachedArray cachedArray = cache.new CachedArray(cachedContentArray);

        // Map the 7-element key to a CachedArray whose recorded size is 4.
        knownArrays.put(lookupKeyArray, cachedArray);
        assertEquals(4, cachedArray.lastKnownSize());

        boolean cached = cache.arrayIsCached(lookupKeyArray);
        assertFalse(cached);
    }
}
