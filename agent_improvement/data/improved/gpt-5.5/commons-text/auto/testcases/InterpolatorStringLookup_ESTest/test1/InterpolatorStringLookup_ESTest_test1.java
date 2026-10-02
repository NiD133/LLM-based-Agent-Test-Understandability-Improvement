package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test1 extends InterpolatorStringLookup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        final String emptyPrefix = "";
        final String lookupKey = ":'Rk ^bnj|([Bw~^W";
        final String encodedLookupValue = "&apos;Rk ^bnj|([Bw~^W";

        HashMap<String, StringLookup> lookupByPrefix = new HashMap<String, StringLookup>();
        XmlEncoderStringLookup xmlEncoderLookup = new XmlEncoderStringLookup();
        lookupByPrefix.put(emptyPrefix, xmlEncoderLookup);

        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(lookupByPrefix, xmlEncoderLookup, false);
        String actualLookupValue = interpolator.lookup(lookupKey);

        assertEquals(encodedLookupValue, actualLookupValue);
    }
}
