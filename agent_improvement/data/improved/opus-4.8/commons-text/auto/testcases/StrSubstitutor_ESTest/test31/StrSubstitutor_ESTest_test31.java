package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test31 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Text that has no "${...}" variable placeholders should be returned
     * unchanged by replace(CharSequence). The leading '$' is not followed by a
     * variable prefix, so it is left as-is rather than treated as an escape.
     */
    @Test(timeout = 4000)
    public void replaceWithNoVariablesReturnsInputUnchanged() throws Throwable {
        StrSubstitutor substitutor = new StrSubstitutor();
        String input = "$org.apache.cmmons.";

        String result = substitutor.replace((CharSequence) input);

        assertNotNull(result);
        assertEquals("input should be returned unchanged when it has no variables",
                input, result);
        assertEquals("default escape character should be '$'",
                '$', substitutor.getEscapeChar());
    }
}
