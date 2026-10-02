package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test09 extends CharSetUtils_ESTest_scaffolding {

    // Tests that delete() leaves the input string unchanged when none of its
    // characters appear in the character set — even when the set array is
    // mostly null entries with only one non-null pattern element.
    @Test(timeout = 4000)
    public void test_deleteWithSparseSetArray_returnsInputUnchangedWhenNoCharactersMatch() throws Throwable {
        String[] charSetArray = new String[9];
        charSetArray[7] = "Iyc@#m)6#tX";

        String result = CharSetUtils.delete("YhY[", charSetArray);

        assertEquals("YhY[", result);
    }
}
