package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test34 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing an empty source array should return an empty (non-null) result,
     * and the escape character supplied to the constructor should be retained.
     */
    @Test(timeout = 4000)
    public void replaceEmptyArrayReturnsEmptyResultAndKeepsEscapeChar() throws Throwable {
        StrLookup<Object> variableResolver =
                (StrLookup<Object>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        char escapeChar = '`';
        // The original test reused the same arbitrary, non-empty string for both
        // the prefix and the suffix; preserved here to keep behaviour identical.
        String variablePrefix = "Variable prefix matcher must not be null!";
        String variableSuffix = "Variable prefix matcher must not be null!";
        StrSubstitutor substitutor = new StrSubstitutor(
                variableResolver, variablePrefix, variableSuffix, escapeChar);

        char[] emptySource = new char[0];
        String result = substitutor.replace(emptySource);

        assertNotNull(result);
        assertEquals(escapeChar, substitutor.getEscapeChar());
    }
}
