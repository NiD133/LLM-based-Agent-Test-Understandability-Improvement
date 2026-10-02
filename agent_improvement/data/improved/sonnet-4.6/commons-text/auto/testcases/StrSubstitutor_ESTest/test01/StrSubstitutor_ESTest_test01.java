package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test01 extends StrSubstitutor_ESTest_scaffolding {

    private static final String CUSTOM_DELIMITER = ".@let_E6[gkIcD#*{";

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Create substitutor with empty value map and the same string as both prefix and suffix
        Map<String, String> emptyValueMap = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(emptyValueMap, CUSTOM_DELIMITER, CUSTOM_DELIMITER);

        // Use '$' as the value delimiter and disable recursive substitution in variable values
        substitutor.setValueDelimiter('$');
        substitutor.setDisableSubstitutionInValues(true);

        // Build text that contains a variable pattern: <prefix> + '$' + <suffix>
        // The '$' between the custom delimiters acts as the variable name expression
        StringBuilder content = new StringBuilder(CUSTOM_DELIMITER);
        content.append('$');
        content.append(CUSTOM_DELIMITER);

        // replaceIn returns true because the variable pattern is found and processed
        boolean wasReplaced = substitutor.replaceIn(content);

        assertTrue(substitutor.isDisableSubstitutionInValues());
        assertTrue(wasReplaced);
    }
}
