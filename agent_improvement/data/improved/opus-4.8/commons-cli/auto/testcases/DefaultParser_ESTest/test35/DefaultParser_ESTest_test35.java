package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test35 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing with a {@code null} {@link Options} argument fails fast.
     *
     * {@code DefaultParser.parse(Options, String[])} delegates to the core parse
     * method, which guards the options parameter with
     * {@code Objects.requireNonNull(options, "options")}. Passing {@code null}
     * options (and {@code null} arguments) therefore raises a
     * {@link NullPointerException} originating from {@code java.util.Objects}.
     */
    @Test(timeout = 4000)
    public void parseWithNullOptionsThrowsNullPointerException() throws Throwable {
        DefaultParser parser = new DefaultParser();

        try {
            parser.parse((Options) null, (String[]) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null-check on the "options" argument is performed by java.util.Objects.
            verifyException("java.util.Objects", e);
        }
    }
}
