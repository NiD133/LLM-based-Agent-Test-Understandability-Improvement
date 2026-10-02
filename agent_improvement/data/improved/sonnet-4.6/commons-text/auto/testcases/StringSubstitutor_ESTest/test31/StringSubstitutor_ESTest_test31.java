package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test31 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that calling replace(String, int, int) with a null source string
     * does not alter the default escape character ('$') on an interpolator instance.
     *
     * The two offset arguments are both 807, meaning the requested substring range
     * is empty; the call should return without performing any substitution.
     */
    @Test(timeout = 4000)
    public void test_replaceNullStringWithEqualOffsets_defaultEscapeCharIsUnchanged() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Replacing on a null source with identical start/end offsets is a no-op
        interpolator.replace((String) null, 807, 807);

        // The default escape character must still be '$' after the no-op call
        assertEquals('$', interpolator.getEscapeChar());
    }
}
