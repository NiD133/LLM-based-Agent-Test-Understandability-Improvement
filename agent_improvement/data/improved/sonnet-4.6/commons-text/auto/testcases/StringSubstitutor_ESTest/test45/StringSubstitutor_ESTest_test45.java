package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test45 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing an object using an interpolator substitutor with an empty
     * Properties map produces a non-null result and preserves the default escape character '$'.
     */
    @Test(timeout = 4000)
    public void test45_replaceObjectWithInterpolatorAndEmptyProperties_returnsNonNullAndKeepsDefaultEscapeChar() throws Throwable {
        // Create a substitutor pre-configured with common interpolation lookups (sys props, env vars, etc.)
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        // Use an empty Properties map — no variable values will be substituted
        Properties emptyProperties = new Properties();

        // Replace variables in the string representation of the interpolator itself
        String result = StringSubstitutor.replace((Object) interpolator, emptyProperties);

        // The default escape character should remain '$'
        assertEquals('$', interpolator.getEscapeChar());
        assertNotNull(result);
    }
}
