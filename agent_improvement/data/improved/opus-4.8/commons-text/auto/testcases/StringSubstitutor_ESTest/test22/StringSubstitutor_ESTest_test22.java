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
public class StringSubstitutor_ESTest_test22 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * Verifies that {@link StringSubstitutor#replace(TextStringBuilder, int, int)}
     * returns {@code null} when given a {@code null} source, regardless of the
     * offset and length arguments. The substitutor is also left with the default
     * escape character ('$').
     */
    @Test(timeout = 4000)
    public void replaceNullTextStringBuilderReturnsNull() throws Throwable {
        Map<String, Object> emptyValueMap = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor(emptyValueMap);

        String result = substitutor.replace((TextStringBuilder) null, -25, -25);

        assertNull(result);
        assertEquals('$', substitutor.getEscapeChar());
    }
}
