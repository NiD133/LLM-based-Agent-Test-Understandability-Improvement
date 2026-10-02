package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test40 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing a char[] that contains no variable references
     * returns a (non-null) String, and that a default StringSubstitutor uses
     * '$' as its escape character.
     */
    @Test(timeout = 4000)
    public void replaceCharArrayWithoutVariablesReturnsNonNullString() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        char[] sourceWithNoVariables = new char[1];

        String result = substitutor.replace(sourceWithNoVariables);

        assertNotNull("replace should return a non-null result", result);
        assertEquals("default escape character should be '$'", '$', substitutor.getEscapeChar());
    }
}
