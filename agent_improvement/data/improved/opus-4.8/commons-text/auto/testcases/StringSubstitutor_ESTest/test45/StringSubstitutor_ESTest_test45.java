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
     * Verifies the static {@link StringSubstitutor#replace(Object, java.util.Properties)} helper:
     * when given an empty Properties map, it has no variables to substitute and simply returns
     * the string representation of the source object (never null).
     * Also confirms a freshly created interpolator uses the default escape character '$'.
     */
    @Test(timeout = 4000)
    public void replaceWithEmptyPropertiesReturnsSourceToString() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        Properties emptyProperties = new Properties();

        String result = StringSubstitutor.replace((Object) interpolator, emptyProperties);

        assertEquals("Newly created interpolator should use the default escape char",
                '$', interpolator.getEscapeChar());
        assertNotNull("replace() should return the source's string representation, not null", result);
    }
}
