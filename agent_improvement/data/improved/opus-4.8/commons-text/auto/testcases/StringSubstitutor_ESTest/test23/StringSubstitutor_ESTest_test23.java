package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test23 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a zero-length region (offset 0, length 0) of a TextStringBuilder
     * should yield an empty string, while the substitutor keeps its default escape
     * character '$'.
     */
    @Test(timeout = 4000)
    public void replaceEmptyRegionReturnsEmptyString() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        TextStringBuilder source = TextStringBuilder.wrap(new char[1]);

        String result = substitutor.replace(source, 0, 0);

        assertNotNull(result);
        assertEquals("", result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
