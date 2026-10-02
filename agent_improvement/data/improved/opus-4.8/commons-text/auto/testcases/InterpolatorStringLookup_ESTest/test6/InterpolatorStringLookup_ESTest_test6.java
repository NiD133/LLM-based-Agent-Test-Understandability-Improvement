package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test6 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * Verifies that toString() returns a non-null description even when the
     * lookup is built from an empty lookup map (with default lookups disabled).
     */
    @Test(timeout = 4000)
    public void toStringReturnsNonNullForEmptyLookupMap() throws Throwable {
        Map<String, StringLookup> emptyLookupMap = new HashMap<String, StringLookup>();
        StringLookup defaultLookup = new XmlEncoderStringLookup();
        boolean addDefaultLookups = false;

        InterpolatorStringLookup interpolatorLookup =
                new InterpolatorStringLookup(emptyLookupMap, defaultLookup, addDefaultLookups);

        String description = interpolatorLookup.toString();

        assertNotNull(description);
    }
}
