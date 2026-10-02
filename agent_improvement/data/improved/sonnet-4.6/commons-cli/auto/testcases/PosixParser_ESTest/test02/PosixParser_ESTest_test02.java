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
public class PosixParser_ESTest_test02 extends PosixParser_ESTest_scaffolding {

    /**
     * Verifies that flattening a sparse argument array containing "-Z&=" (where "Z" is a
     * known option requiring an argument) splits the token into ["-Z", "&="], and that
     * applying flatten a second time on that already-split result produces the same two
     * elements.
     *
     * The input array has six slots; only index 4 is set to "-Z&=". During the first
     * flatten call PosixParser bursts "-Z&=" into the option token "-Z" and its argument
     * value "&=", discarding the five null slots.  The second flatten call receives the
     * clean ["-Z", "&="] pair and leaves it unchanged, confirming idempotent behaviour
     * once the tokens are already separated.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        PosixParser posixParser = new PosixParser();

        // Register option "Z" as one that accepts an argument value.
        Option optionZ = new Option("Z", true, "");
        Options options = new Options();
        Options optionsWithZ = options.addOption(optionZ);

        // Build a sparse six-element argument array; only the fifth slot contains a token.
        // "-Z&=" encodes option Z with the attached argument value "&=".
        String[] sparseArgs = new String[6];
        sparseArgs[4] = "-Z&=";

        // First flatten: null slots are ignored; "-Z&=" is burst into "-Z" and "&=".
        String[] flattenedOnce = posixParser.flatten(optionsWithZ, sparseArgs, true);

        // Second flatten: the already-separated tokens pass through unchanged.
        String[] flattenedTwice = posixParser.flatten(optionsWithZ, flattenedOnce, true);

        assertEquals(2, flattenedTwice.length);
    }
}
