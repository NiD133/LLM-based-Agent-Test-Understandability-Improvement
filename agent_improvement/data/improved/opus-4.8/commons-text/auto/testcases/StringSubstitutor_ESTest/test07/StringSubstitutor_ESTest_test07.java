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
public class StringSubstitutor_ESTest_test07 extends StringSubstitutor_ESTest_scaffolding {

    /**
     * The static {@code replace(source, valueMap, prefix, suffix)} overload requires a non-null
     * variable suffix. Passing {@code null} for the suffix must raise an
     * {@code IllegalArgumentException} (thrown by Apache Commons Lang's {@code Validate}).
     */
    @Test(timeout = 4000)
    public void replaceWithNullSuffixThrowsIllegalArgumentException() throws Throwable {
        Map<String, Object> emptyValues = new HashMap<String, Object>();
        String source = "${";
        String prefix = "${";
        String nullSuffix = null;

        try {
            StringSubstitutor.replace((Object) source, emptyValues, prefix, nullSuffix);
            fail("Expecting exception: IllegalArgumentException (variable suffix must not be null)");
        } catch (IllegalArgumentException e) {
            // Message: "Variable suffix must not be null!"
            verifyException("org.apache.commons.lang3.Validate", e);
        }
    }
}
