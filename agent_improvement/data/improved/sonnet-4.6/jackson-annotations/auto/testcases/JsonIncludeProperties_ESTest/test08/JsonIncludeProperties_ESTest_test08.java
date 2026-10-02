package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test08 extends JsonIncludeProperties_ESTest_scaffolding {

    // Verify that removing a JsonIncludeProperties.Value from an empty String set returns false,
    // confirming the Value instance is not contained in (and is incompatible with) a String collection.
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LinkedHashSet<String> emptyStringSet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value allValue = JsonIncludeProperties.Value.all();
        boolean removed = emptyStringSet.remove(allValue);
        assertFalse(removed);
    }
}
