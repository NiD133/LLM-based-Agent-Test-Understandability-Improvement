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
     * Verifies that PosixParser.flatten handles a mixed array of one non-option token plus
     * null entries differently depending on the stopAtNonOption flag:
     *
     * First pass (stopAtNonOption=true): encountering the non-option token "i}=ILQ<" causes
     * flatten to prepend a "--" separator and then consume all remaining iterator elements
     * (the 5 nulls), producing 7 tokens total: "--", "i}=ILQ<", null, null, null, null, null.
     *
     * Second pass (stopAtNonOption=false) on that result: null tokens are silently skipped
     * (the `if (token != null)` guard in flatten), so only the 2 non-null tokens "--" and
     * "i}=ILQ<" survive.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        // Build an argument array with one non-option token and five trailing nulls.
        String[] argsWithNulls = new String[6];
        argsWithNulls[0] = "i}=ILQ<";
        // argsWithNulls[1..5] remain null

        // First flatten: stopAtNonOption=true.
        // The non-option token triggers "eat-the-rest" mode, inserting "--" before it
        // and appending all remaining iterator elements (the 5 nulls) verbatim.
        String[] flattenedWithStop = parser.flatten(emptyOptions, argsWithNulls, true);
        assertEquals("stopAtNonOption=true should produce 7 tokens (\"--\", the value, and 5 nulls)",
                7, flattenedWithStop.length);

        // Second flatten: stopAtNonOption=false.
        // Null tokens are silently dropped; only the 2 non-null strings survive.
        String[] flattenedWithoutStop = parser.flatten(emptyOptions, flattenedWithStop, false);
        assertEquals("stopAtNonOption=false should drop null tokens, leaving 2 non-null tokens",
                2, flattenedWithoutStop.length);
    }
}
