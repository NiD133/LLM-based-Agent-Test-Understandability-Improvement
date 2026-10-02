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

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] arrayStoredAsCacheKey = new String[7];
        String[] arrayUsedToCreateCachedMetadata = new String[4];

        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays = cache.knownArrays;
        SegmentConstantPoolArrayCache.CachedArray cachedMetadata =
                cache.new CachedArray(arrayUsedToCreateCachedMetadata);

        knownArrays.put(arrayStoredAsCacheKey, cachedMetadata);

        assertEquals(4, cachedMetadata.lastKnownSize());
        boolean cacheEntryMatchesArraySize = cache.arrayIsCached(arrayStoredAsCacheKey);
        assertFalse(cacheEntryMatchesArraySize);
    }
}
