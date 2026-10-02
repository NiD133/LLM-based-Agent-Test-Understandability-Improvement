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
     * Verifies that flatten expands a sparse argument array differently depending on stopAtNonOption.
     *
     * With stopAtNonOption=true, the parser hits the first non-option token ("i}=ILQ<"),
     * inserts a "--" sentinel, then drags the remaining null entries along via eatTheRest,
     * producing 7 tokens total.
     *
     * With stopAtNonOption=false on that 7-token result, null entries are never consumed
     * by eatTheRest, so only the two non-null tokens ("--" and "i}=ILQ<") make it through,
     * producing 2 tokens total.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        // Build an argument array: one non-option string followed by five nulls.
        String[] sparseArgs = new String[6];
        sparseArgs[0] = "i}=ILQ<";

        // First pass: stopAtNonOption=true causes the parser to add "--" before the
        // non-option token and then sweep all remaining (null) entries into the output.
        String[] firstPassResult = parser.flatten(emptyOptions, sparseArgs, true);
        assertEquals(7, firstPassResult.length);

        // Second pass: stopAtNonOption=false; null tokens are skipped and eatTheRest is
        // never triggered, so only the two non-null tokens survive.
        String[] secondPassResult = parser.flatten(emptyOptions, firstPassResult, false);
        assertEquals(2, secondPassResult.length);
    }
}
