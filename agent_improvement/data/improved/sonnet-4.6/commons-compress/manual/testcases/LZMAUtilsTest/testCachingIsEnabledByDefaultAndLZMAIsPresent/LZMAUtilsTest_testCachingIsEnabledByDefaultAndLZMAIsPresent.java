package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testCachingIsEnabledByDefaultAndLZMAIsPresent {

    /**
     * Verifies that, in a non-OSGi environment, the LZMA availability result is
     * cached by default (state is CACHED_AVAILABLE rather than DONT_CACHE or
     * CACHED_UNAVAILABLE) and that the LZMA compression library is on the classpath.
     */
    @Test
    void testCachingIsEnabledByDefaultAndLZMAIsPresent() {
        // In a standard (non-OSGi) environment, setCacheLZMAAvailablity(true) is called
        // during static initialisation, so the cached state should reflect that LZMA is
        // available — not the transient DONT_CACHE sentinel.
        assertEquals(
            LZMAUtils.CachedAvailability.CACHED_AVAILABLE,
            LZMAUtils.getCachedLZMAAvailability(),
            "Caching should be enabled by default and LZMA should be marked as available in the cache"
        );

        // Confirm the public API also reports LZMA as usable (reads from the cache set above).
        assertTrue(
            LZMAUtils.isLZMACompressionAvailable(),
            "LZMA compression must be available on the classpath for this test environment"
        );
    }
}
