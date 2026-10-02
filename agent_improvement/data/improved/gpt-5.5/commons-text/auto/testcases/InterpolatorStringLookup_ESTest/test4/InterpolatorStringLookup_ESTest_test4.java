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

    private static final String LOOKUP_KEY_WITH_EMPTY_PREFIX = ":XML_DECODER";
    private static final String EXPECTED_DEFAULT_LOOKUP_RESULT = "XML_DECODER";

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        XmlDecoderStringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorLookup = new InterpolatorStringLookup(defaultLookup);

        String resolvedValue = interpolatorLookup.lookup(LOOKUP_KEY_WITH_EMPTY_PREFIX);

        assertNotNull(resolvedValue);
        assertEquals(EXPECTED_DEFAULT_LOOKUP_RESULT, resolvedValue);
    }
}
