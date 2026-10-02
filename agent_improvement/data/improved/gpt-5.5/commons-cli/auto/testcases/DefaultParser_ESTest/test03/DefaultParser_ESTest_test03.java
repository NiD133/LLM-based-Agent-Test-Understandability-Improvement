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
public class DefaultParser_ESTest_test03 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Options options = new Options();
        Options optionsWithArgument = options.addOption("s", true, "-o=9RUmk9vb/'-u");

        String[] arguments = new String[9];
        arguments[0] = "-s";
        arguments[1] = "-o=9RUmk9vb/'-u";

        DefaultParser.Builder builder = DefaultParser.builder();
        Boolean stripBalancedQuotes = Boolean.valueOf(true);
        DefaultParser.Builder configuredBuilder = builder.setStripLeadingAndTrailingQuotes(stripBalancedQuotes);
        DefaultParser parser = configuredBuilder.get();

        CommandLine commandLine = parser.parse(optionsWithArgument, arguments, true);

        assertNotNull(commandLine);
    }
}
