package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test33 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that the DEFAULT Value renders every accessor's visibility
     * with its baseline setting in the toString() representation.
     */
    @Test(timeout = 4000)
    public void toStringOfDefaultValueListsBaselineVisibilities() throws Throwable {
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        String description = defaultValue.toString();

        assertEquals(
                "JsonAutoDetect.Value(fields=PUBLIC_ONLY,getters=PUBLIC_ONLY,isGetters=PUBLIC_ONLY,setters=ANY,creators=PUBLIC_ONLY,scalarConstructors=NON_PRIVATE)",
                description);
    }
}
