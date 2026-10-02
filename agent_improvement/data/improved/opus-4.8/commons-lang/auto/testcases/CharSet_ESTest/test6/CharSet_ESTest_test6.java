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
     * Passing a null String array to getInstance yields the shared EMPTY CharSet,
     * and calling hashCode() on it completes without throwing.
     */
    @Test(timeout = 4000)
    public void hashCodeOfEmptyCharSetDoesNotThrow() throws Throwable {
        CharSet emptyCharSet = CharSet.getInstance((String[]) null);

        // hashCode() of the empty set should be computable without error.
        emptyCharSet.hashCode();
    }
}
