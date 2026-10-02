package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests that StringSubstitutor throws IllegalArgumentException when an undefined variable
 * is encountered and enableUndefinedVariableException is set to true.
 *
 * The input template is the substitutor's own toString() output, which contains variable-like
 * patterns (e.g. field names with '{') that cannot be resolved, triggering the exception.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test54 extends StringSubstitutor_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test54_replaceThrowsWhenUndefinedVariableExceptionEnabledAndInputContainsUnresolvableVariables() throws Throwable {
        // Create an interpolator-based substitutor (supports multiple lookup sources)
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();

        // Enable strict mode: unresolved variables will throw instead of being left as-is
        substitutor.setEnableUndefinedVariableException(true);

        // Use the substitutor's own toString() as the template.
        // toString() contains internal field representations with characters like '{' and ']',
        // which the substitutor mistakenly parses as variable references it cannot resolve.
        String templateWithUnresolvableVariable = substitutor.toString();

        // Replacing a string containing unresolvable variable patterns must throw
        try {
            substitutor.replace(templateWithUnresolvableVariable);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.StringSubstitutor", e);
        }
    }
}
