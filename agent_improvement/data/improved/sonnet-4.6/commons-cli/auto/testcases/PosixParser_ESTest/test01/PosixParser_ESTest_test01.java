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
public class PosixParser_ESTest_test01 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that PosixParser.parse() succeeds when one element of a sparse args array
     * matches a registered option ("-bdKQ"), and the remaining 12 slots are null.
     * The result must be a non-null CommandLine even when most argument slots are unused.
     */
    @Test(timeout = 4000)
    public void testParseSucceedsWithSparseArgsArrayContainingKnownOption() throws Throwable {
        // Build a 13-element args array with only index 2 populated
        String[] args = new String[13];
        args[2] = "-bdKQ";

        // Register "bdKQ" as a flag option (no argument value required)
        Options options = new Options();
        Option knownOption = new Option("bdKQ", false, "bdKQ");
        options.addOption(knownOption);

        // Parse the sparse args array; should succeed without throwing
        PosixParser posixParser = new PosixParser();
        CommandLine commandLine = posixParser.parse(options, args);

        assertNotNull(commandLine);
    }
}
