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
public class StringSubstitutor_ESTest_test55 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that the default escape character remains '$' after calling
     * replace(String, int, int) on an interpolator-based substitutor.
     *
     * Steps:
     *  1. Create a substitutor via the factory interpolator (supports many built-in lookups).
     *  2. Obtain the substitutor's string representation to use as the source text.
     *  3. Call replace() with an offset of '$' (36) and a length of 660 — both the offset
     *     and length are out of the meaningful range for the short toString() result, so no
     *     substitution is expected to take place.
     *  4. Assert that the escape character is still '$', the default for StringSubstitutor.
     */
    @Test(timeout = 4000)
    public void test55() throws Throwable {
        // Create a substitutor that supports environment/system-property lookups
        StringSubstitutor interpolatorSubstitutor = StringSubstitutor.createInterpolator();

        // Use the substitutor's own string representation as the source string
        String substitutorDescription = interpolatorSubstitutor.toString();

        // Call replace with offset='$' (36) and length=660; no in-range substitution occurs
        interpolatorSubstitutor.replace(substitutorDescription, (int) '$', 660);

        // The escape character must still be the default '$'
        assertEquals('$', interpolatorSubstitutor.getEscapeChar());
    }
}
