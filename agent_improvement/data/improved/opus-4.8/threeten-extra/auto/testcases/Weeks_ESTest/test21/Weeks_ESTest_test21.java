package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Weeks_ESTest_test21 extends Weeks_ESTest_scaffolding {

    /**
     * Parsing the well-formed ISO-8601 period text "P1W" must succeed.
     * <p>
     * The surrounding try/catch mirrors the original generated test: it guards
     * against an (unexpected) {@link IllegalStateException} that could only be
     * raised from deep inside {@code java.util.regex.Matcher}. For valid input
     * such as "P1W" no exception is thrown, so the catch block is never reached.
     */
    @Test(timeout = 4000)
    public void parsingValidWeeksTextDoesNotThrowMatcherException() throws Throwable {
        try {
            Weeks.parse("P1W");
        } catch (IllegalStateException unexpected) {
            // Would indicate a "No match found" failure inside the regex Matcher.
            verifyException("java.util.regex.Matcher", unexpected);
        }
    }
}
