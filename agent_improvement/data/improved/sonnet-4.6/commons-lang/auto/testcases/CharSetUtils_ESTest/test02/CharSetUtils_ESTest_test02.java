package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test02 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_squeeze_removesConsecutiveDots_whenCharSetContainsDot() throws Throwable {
        // CharSet is built from a two-element array; the null entry is ignored,
        // and "..." defines '.' as the squeeze target.
        String[] charSetDefinition = new String[2];
        charSetDefinition[1] = "...";

        // "..." squeezed against a set containing '.' collapses the run to a single dot.
        String result = CharSetUtils.squeeze("...", charSetDefinition);

        assertEquals(".", result);
    }
}
