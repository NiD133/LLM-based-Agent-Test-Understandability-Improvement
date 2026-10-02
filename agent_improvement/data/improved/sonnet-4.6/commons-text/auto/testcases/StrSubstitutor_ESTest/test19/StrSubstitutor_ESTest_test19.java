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

    /**
     * Verifies that replacing a zero-length range within a StringBuffer returns an empty string,
     * and that the default escape character is '$'.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // Create a mock StrLookup that returns no variable values
        StrLookup<String> mockLookup = (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrSubstitutor substitutor = new StrSubstitutor(mockLookup);

        // StringBuffer with only whitespace characters; the replace range is length 0 so no content is substituted
        StringBuffer whitespaceBuffer = new StringBuffer(" \t\n\r\f");
        String result = substitutor.replace(whitespaceBuffer, 0, 0);

        assertNotNull(result);
        assertEquals("", result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
