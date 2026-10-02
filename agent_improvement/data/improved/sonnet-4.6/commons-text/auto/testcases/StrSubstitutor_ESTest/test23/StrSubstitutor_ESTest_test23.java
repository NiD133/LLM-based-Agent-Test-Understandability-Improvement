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
public class StrSubstitutor_ESTest_test23 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a null String with an out-of-range offset does not throw
     * and that the escape character supplied at construction time is preserved.
     */
    @Test(timeout = 4000)
    public void test_replaceNullString_preservesEscapeChar() throws Throwable {
        StrLookup<String> variableResolver = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrMatcher delimiterMatcher = mock(StrMatcher.class, new ViolatedAssumptionAnswer());
        char escapeChar = '1';

        StrSubstitutor substitutor = new StrSubstitutor(variableResolver, delimiterMatcher, delimiterMatcher, escapeChar);

        // Replacing on a null source string should be a no-op (returns null without throwing)
        substitutor.replace((String) null, 1899, 1);

        // The escape character set via the constructor must be retained unchanged
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
