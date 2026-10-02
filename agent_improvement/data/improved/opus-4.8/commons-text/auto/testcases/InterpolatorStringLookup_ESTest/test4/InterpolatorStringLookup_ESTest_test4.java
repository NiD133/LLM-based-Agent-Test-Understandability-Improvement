package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test4 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When the key starts with the prefix separator ':', the prefix is empty and matches no
     * registered lookup, so the remaining name ("XML_DECODER") is resolved by the default lookup.
     * Here the default lookup is the XML decoder, which returns the name unchanged.
     */
    @Test(timeout = 4000)
    public void lookupWithEmptyPrefixFallsBackToDefaultLookup() throws Throwable {
        StringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(defaultLookup);

        String resolved = interpolator.lookup(":XML_DECODER");

        assertNotNull(resolved);
        assertEquals("XML_DECODER", resolved);
    }
}
