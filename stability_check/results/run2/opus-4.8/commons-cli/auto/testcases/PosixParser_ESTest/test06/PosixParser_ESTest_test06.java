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
     * Verifies how {@link PosixParser#flatten} treats a non-option argument depending on the
     * {@code stopAtNonOption} flag, when no {@link Options} are registered.
     *
     * <p>First pass ({@code stopAtNonOption = true}): the parser stops at the first non-option
     * token. It emits the "--" end-of-options marker, then keeps the non-option token and every
     * remaining (null) element untouched, producing 7 tokens.</p>
     *
     * <p>Second pass ({@code stopAtNonOption = false}): the "--" marker and the non-option token
     * are preserved while the trailing null entries are dropped, leaving only 2 tokens.</p>
     */
    @Test(timeout = 4000)
    public void flattenStopsAtNonOptionThenDropsNullsOnSecondPass() throws Throwable {
        PosixParser parser = new PosixParser();
        Options noOptions = new Options();

        // A non-option argument followed by 5 unset (null) slots.
        String[] arguments = new String[6];
        arguments[0] = "i}=ILQ<";

        // Pass 1: stopAtNonOption = true -> prepends "--" and keeps the trailing nulls.
        String[] flattenedStopping = parser.flatten(noOptions, arguments, true);
        assertEquals(7, flattenedStopping.length);

        // Pass 2: stopAtNonOption = false -> keeps "--" and the token, discards the nulls.
        String[] flattenedContinuing = parser.flatten(noOptions, flattenedStopping, false);
        assertEquals(2, flattenedContinuing.length);
    }
}
