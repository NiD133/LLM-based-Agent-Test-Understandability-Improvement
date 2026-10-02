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

    /**
     * An arbitrary token that is reused as the option description, as the input to
     * {@link Boolean#valueOf(String)}, and as the single command-line argument.
     * Because it is not equal to "true" (ignoring case), {@code Boolean.valueOf} maps it to {@code false}.
     */
    private static final String ARBITRARY_TOKEN = "-=4{};J}P'";

    /**
     * Verifies that {@link DefaultParser#parse(Options, String[], Properties)} returns a non-null
     * {@link CommandLine} when the parser is configured to NOT strip leading/trailing quotes and is
     * given an argument that does not match any defined option.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Define a single option "IGNORE" (short and long name) that requires an argument.
        Options options = new Options();
        options.addOption("IGNORE", "IGNORE", true, ARBITRARY_TOKEN);

        // Boolean.valueOf("-=4{};J}P'") -> false, so quote stripping is explicitly disabled.
        Boolean stripQuotes = Boolean.valueOf(ARBITRARY_TOKEN);
        DefaultParser parser = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(stripQuotes)
                .get();

        // Parse a single argument with no properties; this should succeed and yield a CommandLine.
        Properties properties = new Properties();
        String[] arguments = { ARBITRARY_TOKEN };
        CommandLine commandLine = parser.parse(options, arguments, properties);

        assertNotNull(commandLine);
    }
}
