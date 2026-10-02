package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies the default availability-caching behaviour of {@link LZMAUtils}.
 *
 * <p>Outside an OSGi environment the static initializer of {@code LZMAUtils}
 * caches the result of the LZMA availability check. When the LZMA classes are
 * present on the class path, the cached availability is expected to be
 * {@code CACHED_AVAILABLE} and {@code isLZMACompressionAvailable()} should
 * report {@code true}.</p>
 */
public class LZMAUtilsTest_testCachingIsEnabledByDefaultAndLZMAIsPresent {

    @Test
    void cachingIsEnabledByDefaultAndLzmaIsReportedAsPresent() {
        // By default (non-OSGi) the availability check result is cached as "available".
        assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE,
                LZMAUtils.getCachedLZMAAvailability());

        // The public availability accessor reflects that cached result.
        assertTrue(LZMAUtils.isLZMACompressionAvailable());
    }
}
