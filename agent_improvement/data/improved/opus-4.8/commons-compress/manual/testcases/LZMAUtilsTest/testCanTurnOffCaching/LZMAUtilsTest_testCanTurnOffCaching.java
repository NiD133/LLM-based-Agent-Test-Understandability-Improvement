package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies that LZMA availability caching can be turned off via
 * {@link LZMAUtils#setCacheLZMAAvailablity(boolean)}.
 */
public class LZMAUtilsTest_testCanTurnOffCaching {

    @Test
    void testCanTurnOffCaching() {
        try {
            // Disable caching of the LZMA-availability check.
            LZMAUtils.setCacheLZMAAvailablity(false);

            // With caching off, the cached availability must report DONT_CACHE...
            assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE,
                    LZMAUtils.getCachedLZMAAvailability());

            // ...and the availability check is then evaluated live (LZMA is on the classpath).
            assertTrue(LZMAUtils.isLZMACompressionAvailable());
        } finally {
            // Restore the default behaviour so other tests are unaffected.
            LZMAUtils.setCacheLZMAAvailablity(true);
        }
    }
}
