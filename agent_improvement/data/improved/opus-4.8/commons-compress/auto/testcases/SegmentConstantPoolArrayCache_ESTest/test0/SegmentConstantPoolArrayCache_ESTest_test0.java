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
public class SegmentConstantPoolArrayCache_ESTest_test0 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    /**
     * Looking up a key that is absent from the array should yield an empty list of indexes.
     *
     * The array holds 8 null entries, so the empty-string key "" never matches. The lookup is
     * performed twice on the same (array, key) pair to exercise the cache's "repeat lookup" path,
     * which should still return an empty result.
     */
    @Test(timeout = 4000)
    public void absentKeyReturnsEmptyIndexList() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] arrayOfNulls = new String[8];

        // First lookup populates the cache for this array.
        cache.indexesForArrayKey(arrayOfNulls, "");
        // Second identical lookup returns the cached (empty) result.
        List<Integer> indexesForMissingKey = cache.indexesForArrayKey(arrayOfNulls, "");

        assertEquals(0, indexesForMissingKey.size());
    }
}
