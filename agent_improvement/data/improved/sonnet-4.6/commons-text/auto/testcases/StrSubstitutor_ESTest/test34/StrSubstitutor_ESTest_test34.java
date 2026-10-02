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
public class StrSubstitutor_ESTest_test34 extends StrSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test34() throws Throwable {
        // Create a mock variable resolver that returns no substitutions
        StrLookup<Object> mockResolver = (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());

        // Construct a substitutor with custom (non-default) prefix, suffix, and escape character
        String customPrefix = "Variable prefix matcher must not be null!";
        String customSuffix = "Variable prefix matcher must not be null!";
        char escapeChar = '`';
        StrSubstitutor substitutor = new StrSubstitutor(mockResolver, customPrefix, customSuffix, escapeChar);

        // Replacing within an empty char array should return a non-null empty string
        char[] emptyChars = new char[0];
        String result = substitutor.replace(emptyChars);
        assertNotNull(result);

        // The escape character set via the constructor should be preserved
        assertEquals('`', substitutor.getEscapeChar());
    }
}
