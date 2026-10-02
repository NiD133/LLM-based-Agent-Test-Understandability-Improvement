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

    /**
     * Verifies that a DefaultParser built with quote stripping enabled can parse a
     * short option ("-s") that takes an argument, where the argument itself looks
     * like an option ("-o=9RUmk9vb/'-u"). Because the "-s" option accepts an argument,
     * the following token is consumed as that argument rather than treated as a new option.
     */
    @Test(timeout = 4000)
    public void parseShortOptionWithOptionLikeArgument() throws Throwable {
        // The argument value that follows "-s"; it is also the description text for the option.
        String optionArgument = "-o=9RUmk9vb/'-u";

        // Define a single short option "-s" that requires an argument.
        Options options = new Options();
        options.addOption("s", true, optionArgument);

        // Command line: "-s" followed by its argument, with seven trailing null tokens (ignored).
        String[] arguments = new String[9];
        arguments[0] = "-s";
        arguments[1] = optionArgument;

        // Build a parser that strips balanced leading/trailing quotes from option arguments.
        DefaultParser parser = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(Boolean.TRUE)
                .get();

        // Parse with stopAtNonOption = true so unrecognized tokens do not raise an exception.
        CommandLine commandLine = parser.parse(options, arguments, true);

        assertNotNull(commandLine);
    }
}
