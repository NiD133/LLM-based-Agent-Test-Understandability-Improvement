package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DefaultParser_ESTest_test32 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parsing arguments that do not supply a required option must fail.
     *
     * The options definition declares "js4" as a required option, but the
     * argument array contains no tokens that reference it, so DefaultParser
     * should throw a MissingOptionException ("Missing required option: js4").
     */
    @Test(timeout = 4000)
    public void parseFailsWhenRequiredOptionIsMissing() throws Throwable {
        Options options = new Options();
        options.addRequiredOption("js4", "-=};SP'", true, "js4");

        DefaultParser parser = new DefaultParser(true);

        // Arguments contain only null entries, so the required "js4" option is never provided.
        String[] arguments = new String[32];

        try {
            parser.parse(options, arguments, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Missing required option: js4
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
