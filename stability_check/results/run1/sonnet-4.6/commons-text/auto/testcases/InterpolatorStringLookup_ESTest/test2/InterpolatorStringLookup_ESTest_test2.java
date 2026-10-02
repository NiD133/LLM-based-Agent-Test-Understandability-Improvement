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
     * Verifies that when the input string contains no "${prefix:key}" interpolation markers,
     * the InterpolatorStringLookup delegates to the default lookup and returns the input unchanged.
     */
    @Test(timeout = 4000)
    public void testApplyReturnsInputUnchangedWhenNoInterpolationMarkerPresent() throws Throwable {
        // Use XmlDecoderStringLookup as the default fallback for unrecognised keys
        XmlDecoderStringLookup xmlDecoder = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(xmlDecoder);

        // A plain string with no "${prefix:key}" pattern — interpolation should be a no-op
        String result = interpolator.apply("gr^,<$;(-Ld>~");

        assertEquals("gr^,<$;(-Ld>~", result);
        assertNotNull(result);
    }
}
