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
        String[] arrayReportedAsCached = new String[7];
        String[] shorterArrayUsedForCachedMetadata = new String[4];
        IdentityHashMap<String[], SegmentConstantPoolArrayCache.CachedArray> knownArrays = cache.knownArrays;
        SegmentConstantPoolArrayCache.CachedArray cachedMetadataForShorterArray =
                cache.new CachedArray(shorterArrayUsedForCachedMetadata);

        knownArrays.put(arrayReportedAsCached, cachedMetadataForShorterArray);

        assertEquals(4, cachedMetadataForShorterArray.lastKnownSize());
        boolean cachedWithCurrentLength = cache.arrayIsCached(arrayReportedAsCached);
        assertFalse(cachedWithCurrentLength);
    }
}
