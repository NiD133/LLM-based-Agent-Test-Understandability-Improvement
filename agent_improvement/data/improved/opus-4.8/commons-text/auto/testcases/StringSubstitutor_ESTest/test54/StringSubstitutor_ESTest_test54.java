package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test54 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * When undefined-variable failures are enabled, replacing a string that contains
     * an unresolvable variable placeholder must throw IllegalArgumentException.
     *
     * The substitutor's own toString() output happens to contain the default variable
     * prefix/suffix syntax, so feeding it back into replace() yields a variable name
     * that cannot be resolved.
     */
    @Test(timeout = 4000)
    public void replaceWithUnresolvableVariableThrowsWhenFailureEnabled() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.setEnableUndefinedVariableException(true);

        String textWithUnresolvableVariable = substitutor.toString();

        try {
            substitutor.replace(textWithUnresolvableVariable);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Cannot resolve variable '...' (enableSubstitutionInVariables=false).
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
