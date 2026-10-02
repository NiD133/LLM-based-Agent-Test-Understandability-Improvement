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
public class PosixParser_ESTest_test09 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that flatten() collapses a sparse argument array (mostly nulls) containing
     * one unrecognised multi-character short option ("-Z&=") into a single-element result.
     *
     * The 6-element array has null at every position except index 4.
     * With stopAtNonOption=false and an empty Options set, the parser does not know "-Z",
     * so burstToken() falls into the "unknown option, keep going" branch and emits the
     * whole token unchanged. Null entries are skipped. The net result is one token.
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        PosixParser posixParser0 = new PosixParser();
        Options options0 = new Options();

        // Build a 6-element argument array; only slot 4 carries a value.
        // The token "-Z&=" is longer than two characters and starts with "-",
        // so PosixParser will attempt to burst it. Because "Z" is not a registered
        // option and stopAtNonOption is false, the whole token is emitted as-is.
        String[] argumentsWithSparseEntry = new String[6];
        argumentsWithSparseEntry[4] = "-Z&=";

        String[] flattenedArguments = posixParser0.flatten(options0, argumentsWithSparseEntry, false);

        // Only the one non-null token should appear in the output.
        assertEquals(1, flattenedArguments.length);
    }
}
