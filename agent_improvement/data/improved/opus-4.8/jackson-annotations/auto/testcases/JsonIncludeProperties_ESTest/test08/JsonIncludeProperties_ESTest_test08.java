package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test08 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Removing a value that is not present in an empty set returns false.
     */
    @Test(timeout = 4000)
    public void removingAbsentValueFromEmptySetReturnsFalse() throws Throwable {
        LinkedHashSet<String> emptySet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value includeAll = JsonIncludeProperties.Value.all();

        boolean wasRemoved = emptySet.remove(includeAll);

        assertFalse(wasRemoved);
    }
}
