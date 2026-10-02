package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test25 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * The default interpolator should substitute over an (effectively empty)
     * TextStringBuilder without failing, returning a non-null result and
     * keeping its default escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceEmptyBuilderKeepsDefaultEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // A negative requested capacity is treated as an empty buffer.
        TextStringBuilder emptyBuilder = new TextStringBuilder(-1097462182);

        String result = interpolator.replace(emptyBuilder);

        assertNotNull(result);
        assertEquals('$', interpolator.getEscapeChar());
    }
}
