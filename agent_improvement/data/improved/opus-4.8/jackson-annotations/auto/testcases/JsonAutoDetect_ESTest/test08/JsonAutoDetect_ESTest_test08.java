package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test08 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that {@code Value.construct(PropertyAccessor, Visibility)} applies the given
     * visibility only to the targeted accessor (here CREATOR) and leaves every other accessor
     * at {@link JsonAutoDetect.Visibility#DEFAULT}, and that {@code equals(null)} returns false.
     */
    @Test(timeout = 4000)
    public void constructForCreatorSetsOnlyCreatorVisibilityAndEqualsNullIsFalse() throws Throwable {
        // Build a Value that sets only the CREATOR accessor to PUBLIC_ONLY.
        JsonAutoDetect.Value creatorOnlyValue =
                JsonAutoDetect.Value.construct(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        // A Value is never equal to null.
        assertFalse(creatorOnlyValue.equals((Object) null));

        // Only the creator visibility is overridden; all other accessors stay at DEFAULT.
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, creatorOnlyValue.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getScalarConstructorVisibility());
    }
}
