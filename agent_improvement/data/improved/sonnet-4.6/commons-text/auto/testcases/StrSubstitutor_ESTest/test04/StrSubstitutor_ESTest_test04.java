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
public class StrSubstitutor_ESTest_test04 extends StrSubstitutor_ESTest_scaffolding {

    // Used as both the variable prefix and suffix, so a "variable" looks like: DELIMITER<name>DELIMITER
    private static final String DELIMITER = ".@let_E6[gcD#*{";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Substitutor with an empty lookup map — no variable values are defined
        HashMap<String, String> emptyValueMap = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor((Map<String, String>) emptyValueMap, DELIMITER, DELIMITER);

        // Build a text that is exactly two delimiters concatenated (prefix immediately followed by suffix, no variable name between them)
        StringBuilder text = new StringBuilder(DELIMITER);
        substitutor.setValueDelimiter("");
        StringBuilder textWithDoubleDelimiter = text.append(DELIMITER);

        // replaceIn should return false because no substitution was performed (no matching variable in the empty map)
        boolean substitutionOccurred = substitutor.replaceIn(textWithDoubleDelimiter);
        assertFalse(substitutionOccurred);

        // The escape character should remain at its default value '$'
        assertEquals('$', substitutor.getEscapeChar());
    }
}
