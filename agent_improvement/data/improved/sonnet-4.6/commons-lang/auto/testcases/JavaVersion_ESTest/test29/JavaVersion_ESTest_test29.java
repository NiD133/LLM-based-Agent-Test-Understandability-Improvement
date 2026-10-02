package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JavaVersion_ESTest_test29 extends JavaVersion_ESTest_scaffolding {

    /**
     * Verifies that {@code JavaVersion.get()} throws {@link NumberFormatException} when given
     * a string that is not a recognised version token and whose non-numeric content cannot be
     * parsed by {@code Float.parseFloat} in the fallback branch of the method.
     *
     * Input "/jo" is not matched by any of the known version case labels (e.g. "1.8", "11", …).
     * The fallback branch attempts to extract and parse a numeric sub-string from the input;
     * because "/jo" contains no parseable number, {@code Float.parseFloat} throws
     * {@link NumberFormatException}.
     */
    @Test(timeout = 4000)
    public void test_get_withNonNumericString_throwsNumberFormatException() throws Throwable {
        try {
            JavaVersion.get("/jo");
            fail("Expected NumberFormatException for non-numeric version string \"/jo\"");
        } catch (NumberFormatException e) {
            // expected: the fallback parsing path inside JavaVersion.get() cannot convert
            // the non-numeric characters to a float and propagates NumberFormatException
        }
    }
}
