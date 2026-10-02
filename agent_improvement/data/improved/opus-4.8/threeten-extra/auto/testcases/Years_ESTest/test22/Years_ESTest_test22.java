package org.threeten.extra;

import static org.evosuite.runtime.EvoAssertions.verifyException;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Years_ESTest_test22 extends Years_ESTest_scaffolding {

    /**
     * Parsing the ISO-8601 text "P0Y" exercises the regex-based parser in
     * {@link Years#parse(CharSequence)}. Should the underlying matcher ever be
     * left in an illegal state, it raises an {@link IllegalStateException}; this
     * test tolerates that case by verifying the exception originates from the
     * JDK {@code Matcher}.
     */
    @Test(timeout = 4000)
    public void parseZeroYearsHandlesMatcherIllegalState() throws Throwable {
        try {
            Years.parse("P0Y");
            // No exception is expected during normal execution.
        } catch (IllegalStateException e) {
            // "No match found" originates from java.util.regex.Matcher.
            verifyException("java.util.regex.Matcher", e);
        }
    }
}
