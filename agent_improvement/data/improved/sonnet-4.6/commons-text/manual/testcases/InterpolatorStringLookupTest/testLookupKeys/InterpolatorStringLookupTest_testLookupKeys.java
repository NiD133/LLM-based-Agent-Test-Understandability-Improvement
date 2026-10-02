package org.apache.commons.text.lookup;

import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Verifies that an {@link InterpolatorStringLookup} constructed with a {@code null} variable map
 * still registers all default lookup keys expected by the framework.
 */
public class InterpolatorStringLookupTest_testLookupKeys {

    @Test
    void testLookupKeys() {
        // A null variable map means no user-defined variables; the interpolator should
        // still populate its internal map with all built-in (default) lookup prefixes.
        final InterpolatorStringLookup lookup = new InterpolatorStringLookup((Map<String, Object>) null);
        final Map<String, StringLookup> stringLookupMap = lookup.getStringLookupMap();
        StringLookupFactoryTest.assertDefaultKeys(stringLookupMap);
    }
}
