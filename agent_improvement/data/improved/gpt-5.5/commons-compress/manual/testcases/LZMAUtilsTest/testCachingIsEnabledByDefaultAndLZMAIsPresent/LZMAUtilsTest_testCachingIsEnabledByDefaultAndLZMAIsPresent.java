package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testCachingIsEnabledByDefaultAndLZMAIsPresent {

    @Test
    void testCachingIsEnabledByDefaultAndLZMAIsPresent() {
        final LZMAUtils.CachedAvailability expectedCachedAvailability = LZMAUtils.CachedAvailability.CACHED_AVAILABLE;

        assertEquals(expectedCachedAvailability, LZMAUtils.getCachedLZMAAvailability());
        assertTrue(LZMAUtils.isLZMACompressionAvailable());
    }
}
