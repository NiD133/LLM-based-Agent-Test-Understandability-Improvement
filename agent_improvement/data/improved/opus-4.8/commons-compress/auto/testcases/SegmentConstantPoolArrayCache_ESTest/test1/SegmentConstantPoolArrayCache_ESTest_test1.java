package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentConstantPoolArrayCache_ESTest_test1 extends SegmentConstantPoolArrayCache_ESTest_scaffolding {

    /**
     * Caching the same array instance twice must fail: the cache already holds an
     * up-to-date entry for it, so the second {@code cacheArray} call rejects the
     * duplicate with an {@link IllegalArgumentException}.
     */
    @Test(timeout = 4000)
    public void cachingSameArrayTwiceThrowsIllegalArgumentException() throws Throwable {
        SegmentConstantPoolArrayCache cache = new SegmentConstantPoolArrayCache();
        String[] arrayToCache = new String[7];

        // First call succeeds and records the array in the cache.
        cache.cacheArray(arrayToCache);

        // Second call on the already-cached array is rejected.
        try {
            cache.cacheArray(arrayToCache);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Thrown with message "Trying to cache an array that already exists".
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentConstantPoolArrayCache", e);
        }
    }
}
