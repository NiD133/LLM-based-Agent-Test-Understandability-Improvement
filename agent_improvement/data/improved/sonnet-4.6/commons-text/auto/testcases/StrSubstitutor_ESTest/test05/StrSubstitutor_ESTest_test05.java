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
public class StrSubstitutor_ESTest_test05 extends StrSubstitutor_ESTest_scaffolding {

    // When a text begins with the default escape character ('$') followed by the variable
    // prefix, and preserveEscapes is enabled, replaceIn should leave the text unchanged
    // and return false (no substitution performed).
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String variablePrefix = ".@lNt76>6[gcD#%{";
        String variableSuffix = ".@lNt76>6[gcD#%{";

        Map<String, String> emptyVariableMap = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyVariableMap, variablePrefix, variableSuffix);
        substitutor.setPreserveEscapes(true);

        // Build text: '$' (default escape char) followed by the variable prefix
        // This represents an escaped prefix sequence that should not be substituted
        StringBuilder text = new StringBuilder();
        text.append('$');
        text.append(variablePrefix);

        boolean substitutionOccurred = substitutor.replaceIn(text);

        // The escape character should be preserved, content unchanged
        assertEquals("$.@lNt76>6[gcD#%{", text.toString());
        assertFalse(substitutionOccurred);
    }
}
