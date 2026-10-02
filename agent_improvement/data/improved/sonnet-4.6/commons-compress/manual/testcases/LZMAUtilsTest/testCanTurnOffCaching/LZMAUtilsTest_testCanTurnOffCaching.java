package org.apache.commons.compress.compressors.lzma;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class LZMAUtilsTest_testCanTurnOffCaching {

    @AfterEach
    void restoreCachingToDefault() {
        // Re-enable caching after the test so other tests are not affected by the changed global state
        LZMAUtils.setCacheLZMAAvailablity(true);
    }

    @Test
    void testCanTurnOffCaching() {
        // Turn off caching so that availability is re-evaluated on every call instead of being stored
        LZMAUtils.setCacheLZMAAvailablity(false);

        // Confirm that the internal cache marker reflects the "no caching" setting
        assertEquals(LZMAUtils.CachedAvailability.DONT_CACHE, LZMAUtils.getCachedLZMAAvailability(),
                "Cached availability should be DONT_CACHE after disabling caching");

        // Confirm that LZMA compression is still reported as available even without caching
        assertTrue(LZMAUtils.isLZMACompressionAvailable(),
                "LZMA compression should remain available when caching is disabled");
    }
}
