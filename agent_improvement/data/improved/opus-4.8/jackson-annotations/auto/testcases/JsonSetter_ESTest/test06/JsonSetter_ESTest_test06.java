package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test06 extends JsonSetter_ESTest_scaffolding {

    /**
     * Builds a JsonSetter.Value whose value-nulls and content-nulls are both
     * AS_EMPTY, then verifies that the accessors echo back AS_EMPTY for each.
     */
    @Test(timeout = 4000)
    public void constructWithAsEmptyKeepsBothNullsSettings() throws Throwable {
        Nulls asEmpty = Nulls.AS_EMPTY;

        JsonSetter.Value setterValue = JsonSetter.Value.construct(asEmpty, asEmpty);

        // nonDefaultContentNulls() returns the content setting since it is not DEFAULT;
        // call it as in the original to preserve behaviour.
        setterValue.nonDefaultContentNulls();

        assertEquals(Nulls.AS_EMPTY, setterValue.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, setterValue.getValueNulls());
    }
}
