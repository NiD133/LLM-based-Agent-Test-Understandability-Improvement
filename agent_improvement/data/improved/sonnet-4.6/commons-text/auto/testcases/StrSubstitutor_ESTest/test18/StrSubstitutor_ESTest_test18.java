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
public class StrSubstitutor_ESTest_test18 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test18_replaceOnNullBufferDoesNotAlterEscapeChar() throws Throwable {
        // Construct a substitutor with a custom escape character (';') and custom prefix/suffix delimiters.
        Map<String, Object> emptyVariableMap = new HashMap<String, Object>();
        String customDelimiter = " [stringLookupMap=";
        char escapeChar = ';';
        StrSubstitutor substitutor = new StrSubstitutor(emptyVariableMap, customDelimiter, customDelimiter, escapeChar);

        // Calling replace on a null StringBuffer should be a no-op and must not throw.
        substitutor.replace((StringBuffer) null, Integer.MAX_VALUE, Integer.MAX_VALUE);

        // The escape character must be preserved after the replace call.
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
