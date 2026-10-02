package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test03 extends PosixParser_ESTest_scaffolding {

    /**
     * Parsing the long-option token "---" should fail because it can be matched
     * against more than one registered long option: the option declared with
     * long name "---" and the required option declared with long name "--".
     * Since the token is an ambiguous prefix of both, the PosixParser throws an
     * exception while flattening the arguments.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Register a long option named "---" (no short name, no description).
        Options options = new Options();
        Option ambiguousOption = new Option(null, "---", false, null);
        options.addOption(ambiguousOption);

        // Register a required long option named "--", whose name "---" also
        // matches as a prefix.
        options.addRequiredOption("nz6YwG7", "--", false, "---");

        // Build the argument list containing the ambiguous token "---".
        String[] arguments = new String[38];
        arguments[17] = "---";

        PosixParser posixParser = new PosixParser();
        try {
            posixParser.parse(options, arguments);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Ambiguous option: '---' (could be: '---', '--')
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
