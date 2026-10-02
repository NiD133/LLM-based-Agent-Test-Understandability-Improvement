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
public class PosixParser_ESTest_test02 extends PosixParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        PosixParser parser = new PosixParser();
        Options emptyOptions = new Options();
        String[] arguments = new String[6];
        Option zOptionWithArgument = new Option("Z", true, "");
        Options optionsWithZ = emptyOptions.addOption(zOptionWithArgument);

        // Preserve the sparse EvoSuite input: only index 4 contains the option-like token.
        arguments[4] = "-Z&=";

        String[] firstFlattenedArguments = parser.flatten(optionsWithZ, arguments, true);
        String[] secondFlattenedArguments = parser.flatten(optionsWithZ, firstFlattenedArguments, true);

        assertEquals(2, secondFlattenedArguments.length);
    }
}
