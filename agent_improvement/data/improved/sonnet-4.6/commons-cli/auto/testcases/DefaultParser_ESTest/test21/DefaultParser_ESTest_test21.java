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
public class DefaultParser_ESTest_test21 extends DefaultParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21_parseThrowsOnUnrecognizedOptionWhenStopAtNonOptionIsFalse() throws Throwable {
        // Set up options with one required boolean flag "s4"
        Options options = new Options();
        options.addRequiredOption("s4", "s4", false, "--s4");

        DefaultParser parser = new DefaultParser();

        // Build an 8-element argument array; only the last element is set to an
        // unrecognized token that starts with '-' but is not a registered option.
        String[] args = new String[8];
        args[7] = "-=};SP'";

        // With stopAtNonOption=false the parser must throw on any unrecognized
        // option token instead of silently skipping it.
        try {
            parser.parse(options, args, false);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: -=};SP'
            //
            verifyException("org.apache.commons.cli.DefaultParser", e);
        }
    }
}
