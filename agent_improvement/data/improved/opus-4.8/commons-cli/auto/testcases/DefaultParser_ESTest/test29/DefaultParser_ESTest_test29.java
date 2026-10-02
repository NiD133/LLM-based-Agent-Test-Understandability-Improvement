package org.apache.commons.cli;

import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test29 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that, after a successful parse establishes the parser's option set,
     * {@link DefaultParser#handleConcatenatedOptions(String)} can be invoked with a
     * single known short option ("-s") without throwing.
     */
    @Test(timeout = 4000)
    public void handleConcatenatedOptions_withKnownShortOption_doesNotThrow() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Register a single short option "-s" that takes an argument.
        Options options = new Options();
        options.addOption("s", true, "-s");

        // Parse empty arguments so the parser stores the option set internally.
        String[] noArguments = new String[5];
        parser.parse(options, noArguments, true);

        // "-s" maps to the registered option, so bursting it succeeds.
        parser.handleConcatenatedOptions("-s");
    }
}
