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
public class InterpolatorStringLookup_ESTest_test2 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * Verifies that a string containing no interpolation prefix (no ':') is passed
     * unchanged through the default XmlDecoderStringLookup and returned as-is.
     */
    @Test(timeout = 4000)
    public void testApplyWithNoPrefixReturnsInputUnchanged() throws Throwable {
        // Use XmlDecoderStringLookup as the default fallback lookup
        XmlDecoderStringLookup xmlDecoder = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(xmlDecoder);

        // Input has no ':' prefix, so no prefix-based lookup is attempted;
        // the default lookup returns the string unchanged.
        String input = "gr^,<$;(-Ld>~";
        String result = interpolator.apply(input);

        assertNotNull(result);
        assertEquals(input, result);
    }
}
