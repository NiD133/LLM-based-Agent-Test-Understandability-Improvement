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
     * When a key contains no prefix separator (':'), the interpolator delegates
     * to its default lookup. Here the default lookup is the XML decoder, which
     * returns the input unchanged for a value it cannot decode.
     */
    @Test(timeout = 4000)
    public void applyKeyWithoutPrefixReturnsValueFromDefaultLookup() throws Throwable {
        XmlDecoderStringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(defaultLookup);

        String prefixlessKey = "gr^,<$;(-Ld>~";
        String result = interpolator.apply(prefixlessKey);

        assertNotNull(result);
        assertEquals("gr^,<$;(-Ld>~", result);
    }
}
