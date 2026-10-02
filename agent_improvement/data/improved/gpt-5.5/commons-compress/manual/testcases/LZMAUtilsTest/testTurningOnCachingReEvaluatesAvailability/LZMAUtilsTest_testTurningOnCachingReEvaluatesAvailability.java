package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testTurningOnCachingReEvaluatesAvailability {

    @Test
    void testTurningOnCachingReEvaluatesAvailability() {
        try {
            LZMAUtils.setCacheLZMAAvailablity(false);
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability());

            LZMAUtils.setCacheLZMAAvailablity(true);
            assertEquals(LZMAUtils.CachedAvailability.CACHED_AVAILABLE, LZMAUtils.getCachedLZMAAvailability());
        } finally {
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }
}
