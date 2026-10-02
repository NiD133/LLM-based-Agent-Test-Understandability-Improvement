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
public class InterpolatorStringLookup_ESTest_test4 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * When the key starts with ':' (empty prefix), no prefix-keyed lookup matches.
     * The interpolator strips the empty prefix and delegates the remaining key
     * ("XML_DECODER") to its default StringLookup (XmlDecoderStringLookup).
     * XmlDecoderStringLookup returns the plain text unchanged when it contains
     * no XML-encoded characters, so the result equals the stripped key itself.
     */
    @Test(timeout = 4000)
    public void test_lookupWithEmptyPrefix_delegatesToDefaultLookupAndReturnsStrippedKey() throws Throwable {
        // Use XmlDecoderStringLookup as the default fallback lookup
        XmlDecoderStringLookup xmlDecoderStringLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorStringLookup = new InterpolatorStringLookup(xmlDecoderStringLookup);

        // A key beginning with ':' has an empty prefix; no prefix entry matches,
        // so the part after ':' ("XML_DECODER") is passed to the default lookup
        String result = interpolatorStringLookup.lookup(":XML_DECODER");

        assertNotNull(result);
        assertEquals("XML_DECODER", result);
    }
}
