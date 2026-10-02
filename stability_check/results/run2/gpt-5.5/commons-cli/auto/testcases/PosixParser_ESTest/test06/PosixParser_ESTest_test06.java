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
public class PosixParser_ESTest_test06 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        String[] arguments = new String[6];
        arguments[0] = "i}=ILQ<";

        String[] stoppedAtFirstNonOption = parser.flatten(emptyOptions, arguments, true);
        String[] fullyFlattenedResult = parser.flatten(emptyOptions, stoppedAtFirstNonOption, false);

        assertEquals(2, fullyFlattenedResult.length);
        assertEquals(7, stoppedAtFirstNonOption.length);
    }
}
