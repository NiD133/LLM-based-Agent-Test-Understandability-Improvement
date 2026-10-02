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

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        final XmlDecoderStringLookup defaultLookup = XmlDecoderStringLookup.INSTANCE;
        final InterpolatorStringLookup interpolator = new InterpolatorStringLookup(defaultLookup);
        final String textWithoutLookupPrefix = "gr^,<$;(-Ld>~";

        final String resolvedText = interpolator.apply(textWithoutLookupPrefix);

        assertEquals(textWithoutLookupPrefix, resolvedText);
        assertNotNull(resolvedText);
    }
}
