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

    /**
     * Verifies that {@link SegmentConstantPoolArrayCache.CachedArray#indexesForKey(String)}
     * returns a non-empty list when the requested key actually occurs in the cached array.
     */
    @Test(timeout = 4000)
    public void indexesForKey_returnsNonEmptyList_whenKeyIsPresentInArray() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();

        // Build an array whose first element is the key we will later look up;
        // the remaining six slots are left null and are irrelevant to this test.
        String presentKey = "org.apache.commons.compress.harmony.unpack200.SegmentConstantPoolArrayCache$CachedArray";
        String[] arrayContents = new String[7];
        arrayContents[0] = presentKey;

        // Caching the array records, for every value, the indexes at which it appears.
        SegmentConstantPoolArrayCache.CachedArray cachedArray = cache.new CachedArray(arrayContents);

        // The key is present at index 0, so the returned index list must not be empty.
        List<Integer> indexesForKey = cachedArray.indexesForKey(presentKey);
        assertFalse(indexesForKey.isEmpty());
    }
}
