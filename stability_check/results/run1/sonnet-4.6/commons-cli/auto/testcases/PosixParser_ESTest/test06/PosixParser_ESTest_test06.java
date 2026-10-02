package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test06 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        // Input: one non-option token followed by five null entries
        String[] args = new String[6];
        args[0] = "i}=ILQ<"; // does not start with '-', so it is a non-option token

        // First flatten with stopAtNonOption=true:
        // The non-option token causes the parser to prepend "--" and then consume all
        // remaining tokens (the five nulls) via eatTheRest, producing 7 elements total.
        String[] firstFlattenResult = parser.flatten(emptyOptions, args, true);
        assertEquals(7, firstFlattenResult.length);

        // Second flatten with stopAtNonOption=false on the first result:
        // Null tokens are passed through without action; only "--" and "i}=ILQ<"
        // are non-null, so the result contains exactly 2 elements.
        String[] secondFlattenResult = parser.flatten(emptyOptions, firstFlattenResult, false);
        assertEquals(2, secondFlattenResult.length);
    }
}
