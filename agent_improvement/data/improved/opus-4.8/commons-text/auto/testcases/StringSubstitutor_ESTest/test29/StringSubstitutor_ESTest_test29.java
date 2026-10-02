package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test29 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the default interpolator created via {@link StringSubstitutor#createInterpolator()}:
     * <ul>
     *   <li>uses '$' as its escape character, and</li>
     *   <li>returns a non-null result when replacing a StringBuffer that contains
     *       no variable placeholders (so the content passes through unchanged).</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void replaceBufferWithoutVariablesReturnsNonNullAndKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        StringBuffer bufferWithoutVariables = new StringBuffer("}");
        String result = interpolator.replace(bufferWithoutVariables);

        assertEquals("Interpolator should use the default '$' escape character",
                '$', interpolator.getEscapeChar());
        assertNotNull("replace should return a non-null result", result);
    }
}
