package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test21 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * A buffer that holds only the bare variable-start marker ("${") with no
     * variable name or closing brace is not a complete substitution target, so
     * {@link StringSubstitutor#replaceIn(StringBuffer)} performs no replacement
     * and reports that the buffer was left unchanged.
     */
    @Test(timeout = 4000)
    public void replaceInLeavesIncompleteVariableMarkerUnchanged() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        StringBuffer buffer = new StringBuffer(StringSubstitutor.DEFAULT_VAR_START);

        boolean replaced = interpolator.replaceIn(buffer);

        assertFalse("Incomplete marker \"${\" should not be substituted", replaced);
        assertEquals('$', interpolator.getEscapeChar());
    }
}
