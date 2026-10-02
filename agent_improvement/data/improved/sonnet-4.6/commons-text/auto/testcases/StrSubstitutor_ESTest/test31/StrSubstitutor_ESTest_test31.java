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
public class StrSubstitutor_ESTest_test31 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that a string beginning with the default escape/prefix character '$'
     * but containing no valid variable syntax (i.e. no '${...}' pattern) is
     * returned verbatim by replace(CharSequence), and that the default escape
     * character is '$'.
     */
    @Test(timeout = 4000)
    public void test31() throws Throwable {
        // A plain string that starts with '$' but is NOT a variable reference
        // (missing the '{...}' brackets), so nothing should be substituted.
        String inputWithDollarPrefix = "$org.apache.cmmons.";

        StrSubstitutor substitutor = new StrSubstitutor();
        String result = substitutor.replace((CharSequence) inputWithDollarPrefix);

        // The default escape character for StrSubstitutor is '$'.
        assertEquals('$', substitutor.getEscapeChar());

        // Because the input contains no recognised variable pattern, the result
        // must be non-null and identical to the original input.
        assertNotNull(result);
        assertEquals(inputWithDollarPrefix, result);
    }
}
