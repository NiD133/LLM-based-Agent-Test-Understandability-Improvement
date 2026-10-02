package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test29 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null CharSequence returns null regardless of the offset and
     * length supplied, and the substitutor keeps the default escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceNullCharSequenceReturnsNull() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();

        String result = substitutor.replace((CharSequence) null, '$', '$');

        assertNull(result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
