package org.apache.commons.cli;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Properties;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PosixParser_ESTest_test05 extends PosixParser_ESTest_scaffolding {

    private static final String UNRECOGNIZED_LONG_OPTION = "--=";

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        String[] commandLineArguments = new String[2];
        commandLineArguments[1] = UNRECOGNIZED_LONG_OPTION;
        Options emptyOptions = new Options();
        PosixParser parser = new PosixParser();

        try {
            parser.parse(emptyOptions, commandLineArguments);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Unrecognized option: --=
            //
            verifyException("org.apache.commons.cli.Parser", e);
        }
    }
}
