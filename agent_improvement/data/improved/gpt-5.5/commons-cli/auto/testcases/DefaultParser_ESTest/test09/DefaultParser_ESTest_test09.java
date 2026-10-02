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
public class DefaultParser_ESTest_test09 extends DefaultParser_ESTest_scaffolding {

    private static final String OPTION_NAME = "s";
    private static final String OPTION_TOKEN = "-s";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        DefaultParser parser = new DefaultParser();
        Options options = new Options();

        Option.Builder optionBuilder = Option.builder();
        optionBuilder.longOpt(OPTION_NAME);
        Option longOnlyOption = optionBuilder.get();
        options.addOption(longOnlyOption);
        options.addOption(OPTION_NAME, true, OPTION_TOKEN);

        String[] repeatedOptionWithoutArgument = new String[7];
        repeatedOptionWithoutArgument[0] = OPTION_TOKEN;
        repeatedOptionWithoutArgument[1] = OPTION_TOKEN;
        // EvoSuite leaves the remaining entries null; the parser should still report the missing argument first.
        try {
            parser.parse(options, repeatedOptionWithoutArgument, true);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Missing argument for option: s
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
