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
public class DefaultParser_ESTest_test26 extends DefaultParser_ESTest_scaffolding {

    /**
     * Tests that parsing succeeds when the first argument starts with a recognized short option
     * ("-s") immediately followed by unrecognized characters ("-#\"J"), and stopAtNonOption is
     * true. The parser handles "-s" and stops at the unrecognized suffix without throwing, so
     * the returned CommandLine must be non-null.
     */
    @Test(timeout = 4000)
    public void test_parseStopsAtUnrecognizedSuffixWhenStopAtNonOptionIsTrue() throws Throwable {
        Options options = new Options();
        options.addOption("s", false, "s");

        // First token is "-s" concatenated with unrecognized characters "-#\"J".
        // Remaining 13 slots are left null (unused arguments).
        String[] args = new String[14];
        args[0] = "-s-#\"J";

        DefaultParser parser = new DefaultParser();
        CommandLine result = parser.parse(options, args, true);

        assertNotNull(result);
    }
}
