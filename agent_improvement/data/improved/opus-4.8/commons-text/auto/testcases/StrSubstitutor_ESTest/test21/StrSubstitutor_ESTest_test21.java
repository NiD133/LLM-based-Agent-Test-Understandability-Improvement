package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test21 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing an empty StringBuffer should return a non-null (empty) result,
     * and a default StrSubstitutor should use '$' as its escape character.
     */
    @Test(timeout = 4000)
    public void replaceEmptyBufferReturnsNonNullAndUsesDefaultEscapeChar() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();
        StringBuffer emptySource = new StringBuffer();

        String result = substitutor.replace(emptySource);

        assertNotNull(result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
