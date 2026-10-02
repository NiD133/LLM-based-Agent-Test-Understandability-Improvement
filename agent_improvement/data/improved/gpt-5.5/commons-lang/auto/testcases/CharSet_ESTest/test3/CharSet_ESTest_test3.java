package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test3 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        final int definitionCount = 6;
        final int populatedDefinitionIndex = 4;
        final String populatedDefinition = "=]w9^0fV";

        String[] setDefinitions = new String[definitionCount];
        setDefinitions[populatedDefinitionIndex] = populatedDefinition;

        CharSet charSet = new CharSet(setDefinitions);
    }
}
