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
public class DefaultParser_ESTest_test21 extends DefaultParser_ESTest_scaffolding {

    private static final String REQUIRED_OPTION = "s4";
    private static final String REQUIRED_OPTION_DESCRIPTION = "--s4";
    private static final String UNRECOGNIZED_OPTION_TOKEN = "-=};SP'";
    private static final int UNRECOGNIZED_TOKEN_INDEX = 7;

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        Options options = new Options();
        options.addRequiredOption(REQUIRED_OPTION, REQUIRED_OPTION, false, REQUIRED_OPTION_DESCRIPTION);

        DefaultParser parser = new DefaultParser();
        String[] arguments = new String[8];
        arguments[UNRECOGNIZED_TOKEN_INDEX] = UNRECOGNIZED_OPTION_TOKEN;

        try {
            parser.parse(options, arguments, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: -=};SP'
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
