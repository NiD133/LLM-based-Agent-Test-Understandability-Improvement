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
public class StrSubstitutor_ESTest_test00 extends StrSubstitutor_ESTest_scaffolding {

    // Custom prefix and suffix strings used in place of the default "${" and "}"
    private static final String CUSTOM_PREFIX = "org.apache.commons.text.lookup.ConstantStringLookup";
    private static final String CUSTOM_SUFFIX  = "org.apache.commons.text.lookup.ConstantStringLookup";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Substitutor backed by an empty map with custom variable delimiters
        Map<String, String> emptyValueMap = new HashMap<String, String>();
        StrSubstitutor substitutor = new StrSubstitutor(
                (Map<String, String>) emptyValueMap, CUSTOM_PREFIX, CUSTOM_SUFFIX);

        // Replace zero characters from a single-element char array; expect an empty result
        char[] singleCharArray = new char[1];
        String result = substitutor.replace(singleCharArray, 0, 0);

        assertEquals("", result);
        assertNotNull(result);
        // Default escape character must be '$' regardless of the custom delimiters
        assertEquals('$', substitutor.getEscapeChar());
    }
}
