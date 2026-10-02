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

    /**
     * Verifies that passing a null String array to getInstance() returns the
     * EMPTY CharSet and that hashCode() can be computed on it without error.
     *
     * When setStrs is null, getInstance() short-circuits to return CharSet.EMPTY
     * (an instance whose internal range-set is empty). The expected hash code is
     * 89 because CharSet.hashCode() returns 89 + set.hashCode() and an empty
     * LinkedHashSet has a hash code of 0.
     */
    @Test(timeout = 4000)
    public void test_hashCode_ofCharSetCreatedFromNullArray_returnsEmptySetHashCode() throws Throwable {
        // getInstance(null array) maps directly to CharSet.EMPTY (no ranges added)
        CharSet emptyCharSet = CharSet.getInstance((String[]) null);

        // hashCode() of an empty CharSet is defined as 89 + 0 == 89
        assertEquals(89, emptyCharSet.hashCode());
    }
}
