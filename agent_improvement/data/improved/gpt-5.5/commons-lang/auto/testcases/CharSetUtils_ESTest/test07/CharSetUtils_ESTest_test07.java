package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test07 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void keepReturnsEmptyStringForEmptyInput() throws Throwable {
        String[] emptyCharacterSetDefinitions = new String[9];

        String keptCharacters = CharSetUtils.keep("", emptyCharacterSetDefinitions);

        assertEquals("", keptCharacters);
    }
}
