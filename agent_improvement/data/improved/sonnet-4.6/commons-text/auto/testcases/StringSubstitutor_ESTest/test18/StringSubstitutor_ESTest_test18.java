package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.LinkOption;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test18 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replaceIn(StringBuffer) can be called on a substitutor configured with a null
     * value map and non-standard delimiters, without throwing an exception.
     *
     * The second substitutor uses "${" as the variable prefix, ":-" as the variable suffix,
     * '$' as the escape character, and "}" as the value delimiter — essentially swapping the
     * usual roles of ":-" and "}" to create an unusual but valid configuration.
     *
     * The input buffer is populated from the interpolator's toString(), which includes variable
     * references that may or may not match the non-standard delimiter pattern. The result of
     * replaceIn is not asserted because it depends on runtime state (marked unstable by EvoSuite).
     */
    @Test(timeout = 4000)
    public void test18() throws Throwable {
        // Create an interpolator-based substitutor that knows about many built-in lookups
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Capture the interpolator's string representation to use as substitute input
        String interpolatorDescription = interpolator.toString();

        // Create a substitutor with a null map and non-standard delimiters:
        //   prefix="${"  suffix=":-"  escape='$'  valueDelimiter="}"
        // This is an intentionally unusual configuration to exercise boundary behaviour.
        StringSubstitutor substitutorWithNullMap = new StringSubstitutor(
                (Map<String, LinkOption>) null,
                "${",   // variable prefix
                ":-",   // variable suffix (non-standard: normally this is the value delimiter)
                '$',    // escape character
                "}"     // value delimiter (non-standard: normally this is the suffix)
        );

        // Perform in-place substitution on the buffer; the outcome is runtime-dependent
        StringBuffer buffer = new StringBuffer(interpolatorDescription);
        boolean replacementOccurred = substitutorWithNullMap.replaceIn(buffer);

        //  // Unstable assertion: assertEquals(2307, stringBuffer0.length());
        //  // Unstable assertion: assertTrue(replacementOccurred);
    }
}
