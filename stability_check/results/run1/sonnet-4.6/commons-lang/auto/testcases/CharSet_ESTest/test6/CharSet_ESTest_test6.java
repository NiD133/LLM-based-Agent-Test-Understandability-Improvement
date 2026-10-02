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
     * Verifies that calling getInstance(null) returns the EMPTY CharSet and
     * that hashCode() completes without throwing an exception.
     * The EMPTY CharSet has an empty internal set, so its hashCode is 89 (= 89 + emptySet.hashCode()).
     */
    @Test(timeout = 4000)
    public void test_hashCode_onNullInput_returnsEmptyCharSetHashCode() throws Throwable {
        CharSet emptyCharSet = CharSet.getInstance((String[]) null);
        int hashCode = emptyCharSet.hashCode();
        assertEquals(89, hashCode);
    }
}
