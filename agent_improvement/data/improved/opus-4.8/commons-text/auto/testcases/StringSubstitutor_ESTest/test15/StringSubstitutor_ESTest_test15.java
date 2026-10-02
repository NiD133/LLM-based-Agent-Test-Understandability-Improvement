package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test15 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing in text that contains no variable placeholders should leave the
     * text unchanged. {@code replaceIn} returns {@code false} when nothing was
     * substituted, and the substitutor keeps its default escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceInTextWithoutVariablesMakesNoChange() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        TextStringBuilder textWithoutVariables = new TextStringBuilder((CharSequence) ":-");

        boolean wasReplaced = substitutor.replaceIn(textWithoutVariables);

        assertFalse("No variable to replace, so nothing should change", wasReplaced);
        assertEquals("Default escape character should be '$'", '$', substitutor.getEscapeChar());
    }
}
