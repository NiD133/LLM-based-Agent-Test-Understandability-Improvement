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
public class DefaultParser_ESTest_test04 extends DefaultParser_ESTest_scaffolding {

    private static final String OPTION_NAME = "d";
    private static final String OPTION_DESCRIPTION = "-=R};SP'-";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Options emptyOptions = new Options();
        Options optionsWithArgument = emptyOptions.addOption(OPTION_NAME, OPTION_NAME, true, OPTION_DESCRIPTION);

        DefaultParser.Builder parserBuilder = DefaultParser.builder();
        Boolean stripBalancedQuotes = Boolean.TRUE;
        DefaultParser.Builder quoteStrippingParserBuilder = parserBuilder.setStripLeadingAndTrailingQuotes(stripBalancedQuotes);
        DefaultParser parser = quoteStrippingParserBuilder.get();

        String[] arguments = new String[6];
        arguments[0] = OPTION_DESCRIPTION;

        CommandLine parsedCommandLine = parser.parse(optionsWithArgument, arguments, true);

        assertNotNull(parsedCommandLine);
    }
}
