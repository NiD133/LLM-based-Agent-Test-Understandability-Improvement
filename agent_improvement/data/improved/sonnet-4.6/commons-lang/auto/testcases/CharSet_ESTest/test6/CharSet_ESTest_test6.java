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
    public void test_getInstanceWithNullArray_returnsEmptyCharSetWithValidHashCode() throws Throwable {
        // Passing a null String[] to getInstance should return the EMPTY CharSet
        CharSet emptyCharSet = CharSet.getInstance((String[]) null);

        // hashCode on an empty CharSet should succeed without throwing
        int hashCode = emptyCharSet.hashCode();

        // An empty CharSet has no CharRange entries, so its hash is 89 + 0 = 89
        assertEquals(89, hashCode);
    }
}
