package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test35 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that passing a null Options object to parse() throws a NullPointerException.
     *
     * DefaultParser.parse() calls Objects.requireNonNull(options, "options") internally,
     * so a null Options argument must immediately raise a NullPointerException whose
     * message is "options", originating from java.util.Objects.
     */
    @Test(timeout = 4000)
    public void test_parseWithNullOptions_throwsNullPointerException() throws Throwable {
        DefaultParser parser = new DefaultParser();

        try {
            parser.parse((Options) null, (String[]) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The exception message is "options" — set by Objects.requireNonNull(options, "options")
            verifyException("java.util.Objects", e);
        }
    }
}
