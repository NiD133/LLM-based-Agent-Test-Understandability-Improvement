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
public class DefaultParser_ESTest_test31 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing an argument array containing an unrecognized long option
     * throws an exception when partial matching is disabled.
     *
     * The argument array has 7 slots with only index 3 populated; the option string
     * "--kfS14:mS:6|kIq HbB" does not match any registered option, so
     * DefaultParser must throw an UnrecognizedOptionException.
     */
    @Test(timeout = 4000)
    public void test31() throws Throwable {
        Options options = new Options();

        // Sparse args array: only index 3 holds the unrecognized long option token
        String[] args = new String[7];
        args[3] = "--kfS14:mS:6|kIq HbB";

        // Build a parser that requires exact (non-partial) long-option matching
        DefaultParser parser = DefaultParser.builder()
                .setAllowPartialMatching(false)
                .get();

        Properties properties = new Properties();

        try {
            parser.parse(options, args, properties);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Unrecognized option: --kfS14:mS:6|kIq HbB
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
