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
     * A key that contains no prefix separator (':') cannot be routed to any
     * prefixed lookup, so the interpolator falls back to its default lookup.
     * Here the default is the XML decoder, which returns non-XML text unchanged,
     * so {@code apply} echoes the input key back verbatim.
     */
    @Test(timeout = 4000)
    public void applyWithoutPrefixFallsBackToDefaultLookupAndEchoesInput() throws Throwable {
        String keyWithoutPrefix = "gr^,<$;(-Ld>~";
        InterpolatorStringLookup interpolator =
                new InterpolatorStringLookup(XmlDecoderStringLookup.INSTANCE);

        String result = interpolator.apply(keyWithoutPrefix);

        assertNotNull(result);
        assertEquals(keyWithoutPrefix, result);
    }
}
