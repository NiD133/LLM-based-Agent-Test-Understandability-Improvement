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
public class DefaultParser_ESTest_test05 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String optionToken = "-=4{};J}P'";

        Options options = new Options();
        options.addOption("IGNORE", "IGNORE", true, optionToken);

        Properties defaultOptionValues = new Properties();

        DefaultParser.Builder parserBuilder = DefaultParser.builder();
        Boolean stripBalancedQuotes = Boolean.valueOf(optionToken);
        DefaultParser.Builder configuredBuilder = parserBuilder.setStripLeadingAndTrailingQuotes(stripBalancedQuotes);
        DefaultParser parser = configuredBuilder.get();

        String[] arguments = new String[1];
        arguments[0] = optionToken;

        CommandLine commandLine = parser.parse(options, arguments, defaultOptionValues);

        assertNotNull(commandLine);
    }
}
