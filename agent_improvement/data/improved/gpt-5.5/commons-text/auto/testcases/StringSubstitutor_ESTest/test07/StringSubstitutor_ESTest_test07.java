package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest_test07 extends StringSubstitutor_ESTest_scaffolding {

    private static final String TEMPLATE_WITH_ONLY_PREFIX = "${";
    private static final String VARIABLE_PREFIX = "${";
    private static final String NULL_VARIABLE_SUFFIX = null;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        final HashMap<String, Object> values = new HashMap<String, Object>();

        try {
            StringSubstitutor.replace(
                    (Object) TEMPLATE_WITH_ONLY_PREFIX,
                    (Map<String, Object>) values,
                    VARIABLE_PREFIX,
                    (String) NULL_VARIABLE_SUFFIX);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
            verifyException("org.apache.commons.lang3.Validate", expected);
        }
    }
}
