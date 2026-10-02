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
public class SegmentConstantPoolArrayCache_ESTest_test3 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    private static final String CACHED_ARRAY_CLASS_NAME =
            "org.apache.commons.compress.harmony.unpack200.SegmentConstantPoolArrayCache$CachedArray";

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] cachedValues = new String[7];
        cachedValues[0] = CACHED_ARRAY_CLASS_NAME;

        SegmentConstantPoolArrayCache.CachedArray cachedArray = cache.new CachedArray(cachedValues);
        List<Integer> indexes = cachedArray.indexesForKey(CACHED_ARRAY_CLASS_NAME);

        assertFalse(indexes.isEmpty());
    }
}
