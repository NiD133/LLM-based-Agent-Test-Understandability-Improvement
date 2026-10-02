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
public class StrSubstitutor_ESTest_test11 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn returns false (no substitution occurred) when the variable
     * prefix and suffix are identical non-standard tokens, and the input text contains
     * only that token repeated — meaning no resolvable variable pattern exists.
     * Also confirms the default escape character '$' is preserved after configuration.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Use an unusual string as both prefix and suffix so the delimiter pair is the same
        final String customDelimiter = ".@let_E6[gcD#*{";

        StrSubstitutor substitutor = new StrSubstitutor();
        // setVariableSuffix returns 'this', so both references point to the same instance
        StrSubstitutor substitutorAfterSuffixSet = substitutor.setVariableSuffix(customDelimiter);
        substitutorAfterSuffixSet.setVariablePrefix(customDelimiter);

        // Build input that contains only the delimiter string twice; no resolvable variable
        StringBuilder input = new StringBuilder(customDelimiter);
        input.append(customDelimiter);

        // No variable can be resolved when prefix == suffix with this content, so no replacement
        boolean replacementMade = substitutor.replaceIn(input);
        assertFalse(replacementMade);

        // The default escape character should remain unchanged
        assertEquals('$', substitutor.getEscapeChar());
    }
}
