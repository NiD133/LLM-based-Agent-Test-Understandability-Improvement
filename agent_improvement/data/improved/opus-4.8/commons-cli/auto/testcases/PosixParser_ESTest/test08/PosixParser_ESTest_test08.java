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
public class PosixParser_ESTest_test08 extends PosixParser_ESTest_scaffolding {

    /**
     * Flattening a clustered POSIX token "-ZA" where only "Z" is a known option
     * (and "A" is not), with stopAtNonOption enabled, should burst the token into
     * the recognized option and then "eat the rest" of the input.
     *
     * The bursting produces:
     *   "-Z"  -> the recognized option
     *   "--"  -> the non-option marker inserted once a non-option char is reached
     *   "A"   -> the unrecognized remainder of "-ZA"
     *   null  -> the single trailing argument slot copied verbatim after "eat the rest"
     * giving 4 flattened tokens in total.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        PosixParser parser = new PosixParser();

        // Register a single option whose short name is "Z".
        Options options = new Options();
        options.addOption(new Option("Z", "Z"));

        // A mostly-empty argument array with the clustered token "-ZA" at index 5.
        String[] arguments = new String[7];
        arguments[5] = "-ZA";

        String[] flattened = parser.flatten(options, arguments, true);

        assertEquals(4, flattened.length);
    }
}
