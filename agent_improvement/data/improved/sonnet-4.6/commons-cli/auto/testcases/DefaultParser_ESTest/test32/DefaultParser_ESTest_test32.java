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
public class DefaultParser_ESTest_test32 extends DefaultParser_ESTest_scaffolding {

    private static final String REQUIRED_OPT_SHORT = "js4";
    private static final String REQUIRED_OPT_LONG = "-=};SP'";
    private static final String REQUIRED_OPT_DESCRIPTION = "js4";

    // Parsing with a required option absent in a 32-element null array should throw MissingOptionException.
    @Test(timeout = 4000)
    public void testParsingWithMissingRequiredOptionThrowsException() throws Throwable {
        Options options = new Options();
        options.addRequiredOption(REQUIRED_OPT_SHORT, REQUIRED_OPT_LONG, true, REQUIRED_OPT_DESCRIPTION);

        DefaultParser parser = new DefaultParser(true);
        String[] argsWithNoOptions = new String[32];

        try {
            parser.parse(options, argsWithNoOptions, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing required option: js4
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
