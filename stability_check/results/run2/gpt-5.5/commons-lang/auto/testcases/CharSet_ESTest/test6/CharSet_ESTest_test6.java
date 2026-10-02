package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test6 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        String[] nullSetDefinitions = null;

        CharSet charSetFromNullDefinitions = CharSet.getInstance(nullSetDefinitions);
        int hashCode = charSetFromNullDefinitions.hashCode();

        assertSame(CharSet.EMPTY, charSetFromNullDefinitions);
        assertEquals(89, hashCode);
    }
}
