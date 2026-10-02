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

    @Test(timeout = 4000)
    public void testConstructorWithStringLookupRegistersAllDefaultLookups() throws Throwable {
        XmlDecoderStringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(defaultLookup);
        Map<String, StringLookup> lookupMap = interpolator.getStringLookupMap();
        // The single-argument StringLookup constructor always adds all 18 default prefix lookups
        assertEquals(18, lookupMap.size());
    }
}
