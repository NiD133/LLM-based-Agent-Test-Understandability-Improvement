package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StrSubstitutor_ESTest_test29 extends StrSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that replacing within a null CharSequence returns null and that
     * the default escape character remains '$' (ASCII 36) after the call.
     *
     * The offset and length are both 36, derived from the ASCII value of '$'.
     * Even though the source is null, no exception is thrown — the method
     * short-circuits and returns null immediately.
     */
    @Test(timeout = 4000)
    public void test29_replaceNullCharSequenceReturnsNull() throws Throwable {
        // '$' cast to int equals 36; used here as the offset and length arguments
        final int offsetAndLength = (int) '$';

        StrSubstitutor substitutor = new StrSubstitutor();
        String result = substitutor.replace((CharSequence) null, offsetAndLength, offsetAndLength);

        assertNull("Replacing a null CharSequence should return null", result);
        assertEquals("Default escape character should be '$'",
                '$', substitutor.getEscapeChar());
    }
}
