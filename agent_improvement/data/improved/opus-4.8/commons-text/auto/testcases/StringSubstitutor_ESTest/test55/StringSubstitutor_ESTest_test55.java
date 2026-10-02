package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test55 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that running a substitution over part of a source string does not
     * change the substitutor's escape character, which defaults to '$' for the
     * interpolator created by {@link StringSubstitutor#createInterpolator()}.
     */
    @Test(timeout = 4000)
    public void replaceOnSubstringKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Use the substitutor's own description as the source template. It is long
        // enough to cover the offset/length window used below.
        String source = interpolator.toString();
        final int offset = '$'; // 36
        final int length = 660;

        interpolator.replace(source, offset, length);

        assertEquals('$', interpolator.getEscapeChar());
    }
}
