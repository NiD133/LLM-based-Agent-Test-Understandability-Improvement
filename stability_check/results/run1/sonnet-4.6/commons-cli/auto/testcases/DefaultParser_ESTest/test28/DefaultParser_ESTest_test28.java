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
public class DefaultParser_ESTest_test28 extends DefaultParser_ESTest_scaffolding {

    /**
     * Verifies that parsing with stopAtNonOption=true succeeds even when the first
     * argument is an unrecognized option token ("-s-#\"J") that matches no defined option.
     * With stopAtNonOption=true, unrecognized tokens stop parsing and are collected as
     * plain arguments rather than causing a ParseException.
     */
    @Test(timeout = 4000)
    public void test_parseWithStopAtNonOption_unrecognizedOptionTokenIsAccepted() throws Throwable {
        Options emptyOptions = new Options();
        DefaultParser parser = new DefaultParser();

        // Only index 0 is set; remaining 13 elements are null
        String[] args = new String[14];
        args[0] = "-s-#\"J";

        CommandLine result = parser.parse(emptyOptions, args, true);
        assertNotNull(result);
    }
}
