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
public class DefaultParser_ESTest_test30 extends DefaultParser_ESTest_scaffolding {

    private static final String REQUIRED_OPTION = "s4";
    private static final String REQUIRED_OPTION_DESCRIPTION = "--s4";

    @Test(timeout = 4000)
    public void test30() throws Throwable {
        Options options = new Options();
        options.addRequiredOption(REQUIRED_OPTION, REQUIRED_OPTION, false, REQUIRED_OPTION_DESCRIPTION);

        DefaultParser parser = new DefaultParser(false);
        String[] arguments = new String[6];
        arguments[1] = "--s4";

        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
