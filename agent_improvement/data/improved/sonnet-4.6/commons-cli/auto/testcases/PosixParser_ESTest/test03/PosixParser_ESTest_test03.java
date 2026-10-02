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
     * Verifies that parsing an argument "---" throws AmbiguousOptionException when two options
     * share matching long-option prefixes: one registered with longOpt "---" and another with
     * longOpt "--". Both match the prefix "---", making the argument ambiguous.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Build an argument array whose slot 17 holds the ambiguous token "---"
        String[] args = new String[38];
        args[17] = "---";

        // Register an option with longOpt "---" (opt and description are null/unset)
        Options options = new Options();
        Option tripleDashOption = new Option(args[27], "---", false, args[1]);
        options.addOption(tripleDashOption);

        // Register a second (required) option with longOpt "--", which also matches prefix "---"
        options.addRequiredOption("nz6YwG7", "--", false, "---");

        // Parsing must fail: "---" is ambiguous between longOpts "---" and "--"
        PosixParser posixParser = new PosixParser();
        try {
            posixParser.parse(options, args);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Ambiguous option: '---'  (could be: '---', '--')
            //
            verifyException("org.apache.commons.cli.PosixParser", e);
        }
    }
}
