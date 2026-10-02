package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test1 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When the lookup key starts with ':' the prefix is the empty string "".
     * An XmlEncoderStringLookup registered under "" is selected, and the
     * remainder of the key ("'Rk ^bnj|([Bw~^W") is passed to it.
     * The apostrophe is XML-encoded to "&apos;", producing the expected result.
     */
    @Test(timeout = 4000)
    public void test1() throws Throwable {
        // Register an XmlEncoderStringLookup under the empty-string prefix
        HashMap<String, StringLookup> lookupMap = new HashMap<String, StringLookup>();
        XmlEncoderStringLookup xmlEncoderLookup = new XmlEncoderStringLookup();
        lookupMap.put("", xmlEncoderLookup);

        // Build an interpolator with no built-in default lookups;
        // xmlEncoderLookup also serves as the fallback default
        InterpolatorStringLookup interpolator =
                new InterpolatorStringLookup(lookupMap, xmlEncoderLookup, false);

        // Key ":'Rk ^bnj|([Bw~^W" → prefix="" (before ':'), name="'Rk ^bnj|([Bw~^W" (after ':')
        // The registered XmlEncoderStringLookup encodes the leading apostrophe as &apos;
        String result = interpolator.lookup(":'Rk ^bnj|([Bw~^W");

        assertEquals("&apos;Rk ^bnj|([Bw~^W", result);
    }
}
