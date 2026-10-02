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

    private static final String TEXT_WITHOUT_LOOKUP_PREFIX = "gr^,<$;(-Ld>~";

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        XmlDecoderStringLookup xmlDecoderLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorLookup = new InterpolatorStringLookup(xmlDecoderLookup);

        String resolvedText = interpolatorLookup.apply(TEXT_WITHOUT_LOOKUP_PREFIX);

        assertEquals(TEXT_WITHOUT_LOOKUP_PREFIX, resolvedText);
        assertNotNull(resolvedText);
    }
}
