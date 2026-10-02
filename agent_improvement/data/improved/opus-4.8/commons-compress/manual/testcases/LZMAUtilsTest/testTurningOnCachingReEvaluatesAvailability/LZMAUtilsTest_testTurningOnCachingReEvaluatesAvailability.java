package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testTurningOnCachingReEvaluatesAvailability {

    /**
     * Verifies that switching caching back on forces the LZMA availability to be
     * re-evaluated and cached again.
     *
     * <p>While caching is off the cached state is {@code DONT_CACHE}. Once caching
     * is turned on, the availability is checked and, because the LZMA classes are
     * present on the test classpath, stored as {@code CACHED_AVAILABLE}.</p>
     */
    @Test
    void testTurningOnCachingReEvaluatesAvailability() {
        try {
            // Turning caching off resets the state to "don't cache".
            LZMAUtils.setCacheLZMAAvailablity(false);
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE,
                    LZMAUtils.getCachedLZMAAvailability());

            // Turning caching back on re-evaluates and caches the availability.
            LZMAUtils.setCacheLZMAAvailablity(true);
            assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE,
                    LZMAUtils.getCachedLZMAAvailability());
        } finally {
            // Restore caching so other tests see the default cached state.
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }
}
