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
public class StrSubstitutor_ESTest_test19 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a zero-length region (offset 0, length 0) of a StringBuffer
     * should yield an empty result, regardless of the buffer's contents.
     * The substitutor's default escape character ('$') must remain unchanged.
     */
    @Test(timeout = 4000)
    public void replaceEmptyRegionReturnsEmptyString() throws Throwable {
        StrLookup<String> variableResolver =
                (StrLookup<String>) mock(StrLookup.class, new ViolatedAssumptionAnswer());
        StrSubstitutor substitutor = new StrSubstitutor(variableResolver);

        StringBuffer source = new StringBuffer(" \t\n\r\f");
        String result = substitutor.replace(source, 0, 0);

        assertNotNull(result);
        assertEquals("", result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
