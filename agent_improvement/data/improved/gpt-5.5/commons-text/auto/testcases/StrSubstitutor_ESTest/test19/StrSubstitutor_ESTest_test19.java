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
public class StrSubstitutor_ESTest_test19 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        final StrLookup<String> variableResolver =
                (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        final StrSubstitutor substitutor = new StrSubstitutor(variableResolver);
        final StringBuffer source = new StringBuffer(" \t\n\r\f");

        final String replacedSubstring = substitutor.replace(source, 0, 0);

        assertNotNull(replacedSubstring);
        assertEquals("", replacedSubstring);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
