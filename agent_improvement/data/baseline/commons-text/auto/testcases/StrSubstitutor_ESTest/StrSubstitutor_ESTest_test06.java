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
        StrLookup<String> strLookup0 = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher strMatcher0 = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        StrSubstitutor strSubstitutor0 = null;
        try {
            strSubstitutor0 = new StrSubstitutor(strLookup0, strMatcher0, (StrMatcher) null, 'x', (StrMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable suffix matcher must not be null!
            //
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
