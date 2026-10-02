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
     * Verifies that {@code arrayIsCached} reports {@code false} when the cache entry
     * is stale, i.e. the cached array's last known size differs from the length of
     * the array used as the lookup key.
     *
     * <p>To set this up, a {@link SegmentConstantPoolArrayCache.CachedArray} is built
     * from a 4-element array (so its last known size is 4) but is registered in the
     * cache under a 7-element key array. Because the recorded size (4) no longer
     * matches the key array's length (7), the cache must be considered outdated.</p>
     */
    @Test(timeout = 4000)
    public void arrayIsCachedReturnsFalseWhenCachedSizeDiffersFromArrayLength() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();

        // Key array (length 7) and the array actually cached (length 4) deliberately differ in size.
        String[] keyArray = new String[7];
        String[] cachedContentArray = new String[4];

        // Manually register a CachedArray under a key array whose length does not match it.
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays = cache.knownArrays;
        SegmentConstantPoolArrayCache.CachedArray cachedArray = cache.new CachedArray(cachedContentArray);
        knownArrays.put(keyArray, cachedArray);

        // The CachedArray remembers the size of the array it was built from (4).
        assertEquals(4, cachedArray.lastKnownSize());

        // The key array's length (7) no longer matches the cached size (4), so the cache is stale.
        boolean cached = cache.arrayIsCached(keyArray);
        assertFalse(cached);
    }
}
