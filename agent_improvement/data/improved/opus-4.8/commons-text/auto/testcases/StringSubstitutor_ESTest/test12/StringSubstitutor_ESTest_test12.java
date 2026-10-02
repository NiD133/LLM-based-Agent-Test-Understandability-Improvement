package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test12 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * replaceIn(TextStringBuilder, offset, length) returns false (nothing altered)
     * when the source builder is null, regardless of the offset and length given.
     * The default interpolator's escape character remains the unchanged default '$'.
     */
    @Test(timeout = 4000)
    public void replaceInNullSourceReturnsFalse() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();

        boolean altered = interpolator.replaceIn((TextStringBuilder) null, '$', '$');

        assertFalse("Replacing in a null source must report no change", altered);
        assertEquals("Default escape character should be '$'", '$', interpolator.getEscapeChar());
    }
}
