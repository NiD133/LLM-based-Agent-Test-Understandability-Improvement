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
public class PosixParser_ESTest_test11 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that burstToken handles a two-character token where the second character
     * matches a registered option that requires an argument, but there are no remaining
     * characters to supply as that argument.
     *
     * Steps:
     *  1. Parse a command line of null entries so the parser initialises its internal state.
     *  2. Register option "Z" (which requires an argument) on the same Options object.
     *  3. Call burstToken("wZ", stopAtNonOption=true): the loop skips index 0 ('w') and
     *     inspects index 1 ('Z'), finds option Z, adds "-Z" to tokens, then sees no
     *     remaining characters — so no argument token is appended and the loop exits cleanly.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        PosixParser parser = new PosixParser();
        Options options = new Options();

        // Six null entries: the parser must tolerate null command-line tokens.
        String[] nullArguments = new String[6];
        Properties emptyProperties = new Properties();

        // Initialise the parser's internal state by running a full parse pass first.
        parser.parse(options, nullArguments, emptyProperties, true);

        // Register option "Z" as one that accepts an argument (hasArg=true).
        Option optionZ = new Option("Z", true, "");
        options.addOption(optionZ);

        // "wZ": index 0 ('w') is skipped by burstToken, index 1 ('Z') matches the
        // registered option. Because "wZ" ends at 'Z' there are no trailing characters
        // to use as the argument value, so the burst completes without adding an argument token.
        parser.burstToken("wZ", true);
    }
}
