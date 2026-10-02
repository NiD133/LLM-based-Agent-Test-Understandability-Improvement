package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test06 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies how {@link PosixParser#flatten} handles a non-option argument
     * followed by trailing null tokens, first with stopAtNonOption enabled and
     * then disabled.
     *
     * <p>First pass (stopAtNonOption = true): the leading non-option token
     * triggers "eat the rest" behaviour, so the parser inserts the "--"
     * marker before the value and then copies every remaining (null) token
     * verbatim. This turns the 6-element input into a 7-element result:
     * "--", the value, and the 5 trailing nulls.</p>
     *
     * <p>Second pass (stopAtNonOption = false): the "--" marker is kept, the
     * value is added as a plain token, and the null tokens are dropped, leaving
     * only 2 tokens.</p>
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        // One non-option value followed by five null (unset) argument slots.
        String[] arguments = new String[6];
        arguments[0] = "i}=ILQ<";

        String[] flattenedStopping = parser.flatten(emptyOptions, arguments, true);
        assertEquals(7, flattenedStopping.length);

        String[] flattenedNonStopping = parser.flatten(emptyOptions, flattenedStopping, false);
        assertEquals(2, flattenedNonStopping.length);
    }
}
