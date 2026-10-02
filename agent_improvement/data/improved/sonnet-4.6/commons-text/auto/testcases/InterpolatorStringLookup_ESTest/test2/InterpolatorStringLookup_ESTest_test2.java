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
     * Verifies that apply() returns the input string unchanged when the key contains no
     * prefix separator (':'), so no registered lookup matches, and the default
     * XmlDecoderStringLookup also cannot decode it (no XML entities present).
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Build an interpolator backed by XmlDecoderStringLookup as the default fallback
        XmlDecoderStringLookup xmlDecoderStringLookup0 = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorStringLookup0 = new InterpolatorStringLookup(xmlDecoderStringLookup0);

        // Input has no ':' separator, so no prefix-based lookup is attempted;
        // the fallback decoder finds no XML entities, so the key is returned as-is
        String string0 = interpolatorStringLookup0.apply("gr^,<$;(-Ld>~");

        assertEquals("gr^,<$;(-Ld>~", string0);
        assertNotNull(string0);
    }
}
