package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test04 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the default escape character of an interpolating
     * StringSubstitutor stays '$' even after reconfiguring it (enabling
     * recursive substitution and overriding the variable-prefix matcher) and
     * running a replacement.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        // setEnableSubstitutionInVariables returns the same instance for chaining.
        StringSubstitutor sameSubstitutor = substitutor.setEnableSubstitutionInVariables(true);
        sameSubstitutor.setVariablePrefixMatcher(StringSubstitutor.DEFAULT_SUFFIX);

        // Replace over the substitutor's own toString() output; the result is
        // unused here, we only care that it runs without altering the escape char.
        String description = substitutor.toString();
        substitutor.replace(description);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
