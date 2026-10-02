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
        String[] lookupArray = new String[7];
        String[] cachedArrayContents = new String[4];
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays = cache.knownArrays;
        SegmentConstantPoolArrayCache.CachedArray cachedArray = cache.new CachedArray(cachedArrayContents);

        // Store a cache entry whose remembered size does not match the array key.
        knownArrays.put(lookupArray, cachedArray);

        assertEquals(4, cachedArray.lastKnownSize());
        boolean cached = cache.arrayIsCached(lookupArray);
        assertFalse(cached);
    }
}
