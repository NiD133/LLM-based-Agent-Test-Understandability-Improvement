package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test29 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that handleConcatenatedOptions can be called after a parse() invocation
     * that used stopAtNonOption=true with an all-null argument array.
     * The "-s" token passed to handleConcatenatedOptions contains the known short option 's',
     * which has been registered as accepting an argument. Because no trailing characters
     * remain after 's', no argument value is processed and no exception should be thrown.
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        DefaultParser parser = new DefaultParser();

        Options options = new Options();
        // Register short option "s" that accepts an argument; description is "-s"
        Options optionsWithS = options.addOption("s", true, "-s");

        // Five-element array of nulls — null tokens are silently skipped during parsing
        String[] nullArguments = new String[5];
        parser.parse(optionsWithS, nullArguments, true);

        // "-s" has 's' at index 1: a known option with no trailing characters, so no arg is added
        parser.handleConcatenatedOptions("-s");
    }
}
