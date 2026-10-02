package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test52 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that enabling substitution-in-variables is retained after calling replace()
     * on the substitutor's own toString() output.
     *
     * Steps:
     *  1. Create a fully-configured interpolator instance.
     *  2. Enable the "substitution in variables" feature so that variable names are
     *     themselves resolved before being looked up.
     *  3. Replace using the substitutor's own string representation (which contains no
     *     variable placeholders), confirming the call succeeds without error.
     *  4. Assert that the flag is still enabled after the replace call.
     */
    @Test(timeout = 4000)
    public void test52() throws Throwable {
        // Create an interpolator that resolves variables from common sources (env, sys, etc.)
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Enable recursive resolution of variable names themselves
        interpolator.setEnableSubstitutionInVariables(true);

        // Use the substitutor's own toString() as input — it contains no variable syntax,
        // so replace() should return the string unchanged
        String substituorDescription = interpolator.toString();
        interpolator.replace(substituorDescription);

        // Confirm the flag was not reset by the replace() call
        assertTrue(interpolator.isEnableSubstitutionInVariables());
    }
}
