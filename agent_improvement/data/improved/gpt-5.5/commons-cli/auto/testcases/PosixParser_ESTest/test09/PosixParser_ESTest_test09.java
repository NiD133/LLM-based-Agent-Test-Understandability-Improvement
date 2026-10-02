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
public class PosixParser_ESTest_test09 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();

        String[] arguments = new String[6];
        arguments[4] = "-Z&=";

        String[] flattenedArguments = parser.flatten(emptyOptions, arguments, false);

        assertEquals(1, flattenedArguments.length);
    }
}
