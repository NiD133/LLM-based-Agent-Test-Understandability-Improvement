package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test26 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null StringBuffer should be a no-op that returns null, and it
     * must not change the substitutor's configuration. Here we verify the escape
     * character remains the default '$'.
     */
    @Test(timeout = 4000)
    public void test26() throws Throwable {
        Map<String, Object> emptyLookupValues = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyLookupValues);

        // Pass a null StringBuffer source with arbitrary offset and length.
        substitutor.replace((StringBuffer) null, 36, 36);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
