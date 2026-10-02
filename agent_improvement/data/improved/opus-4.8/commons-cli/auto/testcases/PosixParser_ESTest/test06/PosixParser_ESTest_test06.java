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
     * Verifies how {@link PosixParser#flatten} treats a non-option argument followed by
     * trailing nulls, first with {@code stopAtNonOption = true} and then with
     * {@code stopAtNonOption = false}.
     *
     * <p>First pass ({@code stopAtNonOption = true}): the leading non-option token
     * "i}=ILQ&lt;" triggers "eat the rest" mode, so the parser emits the "--" marker,
     * the non-option token itself, and then copies the five remaining nulls verbatim,
     * yielding 7 tokens.</p>
     *
     * <p>Second pass ({@code stopAtNonOption = false}) over those 7 tokens: the "--"
     * marker and the non-option token are kept, while the five null entries are skipped,
     * yielding 2 tokens.</p>
     */
    @Test(timeout = 4000)
    public void flattenEatsRestThenDropsNulls() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        // One non-option argument followed by five null slots.
        String[] arguments = new String[6];
        arguments[0] = "i}=ILQ<";

        // stopAtNonOption = true: emit "--", the token, and copy the 5 trailing nulls.
        String[] flattenedStopping = parser.flatten(emptyOptions, arguments, true);
        assertEquals(7, flattenedStopping.length);

        // stopAtNonOption = false: keep "--" and the token, drop the null entries.
        String[] flattenedNonStopping = parser.flatten(emptyOptions, flattenedStopping, false);
        assertEquals(2, flattenedNonStopping.length);
    }
}
