package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test06 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        final StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        final StrMatcher prefixMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());

        try {
            new StrSubstitutor(variableResolver, prefixMatcher, (StrMatcher) null, 'x', (StrMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable suffix matcher must not be null!
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
