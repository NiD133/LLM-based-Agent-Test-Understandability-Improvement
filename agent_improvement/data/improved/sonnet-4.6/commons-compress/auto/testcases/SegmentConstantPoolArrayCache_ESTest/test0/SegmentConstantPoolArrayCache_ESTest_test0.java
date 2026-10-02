package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentConstantPoolArrayCache_ESTest_test0 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    /**
     * Verifies that searching for an empty string in an array of null elements returns an empty list.
     *
     * The array contains 8 null elements (no element equals ""), so no indexes should be found.
     * The first call populates the internal cache; the second call exercises the repeated-lookup
     * shortcut (the cache-within-a-cache that skips re-scanning when the same array+key is queried
     * consecutively). Both calls must agree on the result: an empty list.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();

        // An array of 8 null elements — none of them equal the search key ""
        String[] arrayOfNulls = new String[8];

        // First call: caches the array internally and performs the lookup
        cache.indexesForArrayKey(arrayOfNulls, "");

        // Second call: exercises the repeated-lookup shortcut built into the cache
        List<Integer> matchingIndexes = cache.indexesForArrayKey(arrayOfNulls, "");

        // No element in the null-filled array matches "", so the result must be empty
        assertEquals(0, matchingIndexes.size());
    }
}
