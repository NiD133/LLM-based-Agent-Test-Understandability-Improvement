package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test24 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Replacing a null TextStringBuilder should be a no-op that returns without
     * error, leaving the substitutor's configuration (here, the default escape
     * character '$') unchanged.
     */
    @Test(timeout = 4000)
    public void replaceNullBuilderLeavesDefaultEscapeChar() throws Throwable {
        Map<String, Object> emptyValues = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyValues);

        substitutor.replace((TextStringBuilder) null);

        assertEquals('$', substitutor.getEscapeChar());
    }
}
