package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test52 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that enabling substitution-in-variables sticks, and that running a
     * replace on an arbitrary string (here the substitutor's own toString output)
     * does not reset the flag.
     */
    @Test(timeout = 4000)
    public void enableSubstitutionInVariablesRemainsSetAfterReplace() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        interpolator.setEnableSubstitutionInVariables(true);

        // Replace some text; the toString() output is just a convenient input string.
        String inputText = interpolator.toString();
        interpolator.replace(inputText);

        assertTrue(interpolator.isEnableSubstitutionInVariables());
    }
}
