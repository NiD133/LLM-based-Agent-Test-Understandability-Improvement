package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test07 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonAutoDetect.Value#construct(PropertyAccessor, JsonAutoDetect.Visibility)}
     * only changes the visibility of the targeted accessor (here CREATOR) and
     * leaves every other accessor at {@link JsonAutoDetect.Visibility#DEFAULT}.
     */
    @Test(timeout = 4000)
    public void creatorOnlyVisibility_leavesOtherAccessorsAtDefault() throws Throwable {
        // Build a Value that overrides only the CREATOR accessor, setting it to NONE.
        JsonAutoDetect.Value creatorNoneValue =
                JsonAutoDetect.Value.construct(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE);

        // Comparing against the all-default Value must not throw and is exercised for behaviour parity.
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.defaultVisibility();
        defaultValue.equals(creatorNoneValue);

        // Only the CREATOR visibility should reflect the requested NONE; all others stay DEFAULT.
        assertEquals(JsonAutoDetect.Visibility.NONE, creatorNoneValue.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneValue.getFieldVisibility());
    }
}
