package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Verifies that turning caching ON (after it was OFF) causes LZMAUtils to
 * re-evaluate LZMA availability and store the result in the cache.
 */
public class LZMAUtilsTest_testTurningOnCachingReEvaluatesAvailability {

    /** Restore caching after each test so other tests are not affected. */
    @AfterEach
    void restoreCachingEnabled() {
        LZMAUtils.setCacheLZMAAvailablity(true);
    }

    @Test
    void testTurningOnCachingReEvaluatesAvailability() {
        // Disable caching: the availability state should switch to DONT_CACHE
        LZMAUtils.setCacheLZMAAvailablity(false);
        assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE,
                LZMAUtils.getCachedLZMAAvailability(),
                "Disabling caching should set the cached state to DONT_CACHE");

        // Re-enable caching: LZMAUtils must re-evaluate availability and cache the result
        LZMAUtils.setCacheLZMAAvailablity(true);
        assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE,
                LZMAUtils.getCachedLZMAAvailability(),
                "Enabling caching should trigger re-evaluation and cache LZMA as CACHED_AVAILABLE");
    }
}
