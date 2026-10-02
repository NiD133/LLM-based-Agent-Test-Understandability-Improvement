package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test7 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When an InterpolatorStringLookup is created with a custom default lookup,
     * its lookup map should still be populated with the 18 default string lookups.
     */
    @Test(timeout = 4000)
    public void lookupMapContainsAllDefaultLookups() throws Throwable {
        StringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorStringLookup =
                new InterpolatorStringLookup(defaultLookup);

        Map<String, StringLookup> lookupMap = interpolatorStringLookup.getStringLookupMap();

        int expectedDefaultLookupCount = 18;
        assertEquals(expectedDefaultLookupCount, lookupMap.size());
    }
}
