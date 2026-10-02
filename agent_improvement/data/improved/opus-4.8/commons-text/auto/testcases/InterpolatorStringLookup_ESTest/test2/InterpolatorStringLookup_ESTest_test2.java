package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test2 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When the input contains no "prefix:" separator, the InterpolatorStringLookup
     * delegates the whole key to its default lookup. Here the default lookup is the
     * XmlDecoderStringLookup, which returns the input unchanged for a non-XML string.
     */
    @Test(timeout = 4000)
    public void applyWithoutPrefixReturnsInputUnchanged() throws Throwable {
        StringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorLookup = new InterpolatorStringLookup(defaultLookup);

        String inputWithoutPrefix = "gr^,<$;(-Ld>~";
        String result = interpolatorLookup.apply(inputWithoutPrefix);

        assertNotNull(result);
        assertEquals(inputWithoutPrefix, result);
    }
}
