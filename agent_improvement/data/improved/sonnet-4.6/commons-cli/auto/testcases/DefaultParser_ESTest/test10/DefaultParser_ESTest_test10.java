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
public class DefaultParser_ESTest_test10 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that a single dash "-" is accepted as the argument value for an option
     * when stopAtNonOption is true. The option "-s" requires an argument, and "-" (a lone
     * dash, not a valid option prefix) should be treated as that argument value rather than
     * causing a parse error.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        DefaultParser parser = new DefaultParser();

        // Register "-s" as an option that requires an argument
        Options options = new Options();
        options.addOption("s", true, "-s");

        // Provide "-s" followed by "-" (lone dash) as its argument value;
        // remaining slots are left null (unused)
        String[] args = new String[6];
        args[0] = "-s";
        args[1] = "-";

        // With stopAtNonOption=true, the lone "-" is consumed as the value of "-s"
        // rather than being rejected, so parsing should succeed
        CommandLine commandLine = parser.parse(options, args, true);
        assertNotNull(commandLine);
    }
}
