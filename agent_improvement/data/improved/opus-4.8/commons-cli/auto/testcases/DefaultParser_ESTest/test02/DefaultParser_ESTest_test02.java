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
public class DefaultParser_ESTest_test02 extends DefaultParser_ESTest_scaffolding {

    /**
     * Parses the single argument "-s s" against an option "s" that takes a value.
     * The parser is built with quote stripping explicitly disabled (Boolean.valueOf("s")
     * yields false), and stopAtNonOption is false. Parsing should succeed and return a
     * non-null CommandLine.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Define an option "-s" that requires an argument.
        Options options = new Options();
        options.addOption("s", true, "s");

        // Command line: "-s" followed by its value "s"; remaining entries are unused (null).
        String[] arguments = new String[9];
        arguments[0] = "-s";
        arguments[1] = "s";

        // Build a DefaultParser with quote stripping disabled ("s" is not "true").
        boolean stripQuotes = Boolean.valueOf("s");
        DefaultParser parser = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(stripQuotes)
                .get();

        // Parse with stopAtNonOption = false.
        CommandLine commandLine = parser.parse(options, arguments, false);

        assertNotNull(commandLine);
    }
}
