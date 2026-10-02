package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test36 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that calling withOverrides(DEFAULT) on a modified Value returns the DEFAULT instance,
     * because DEFAULT contains only non-DEFAULT visibilities which override every field.
     * Also confirms that withCreatorVisibility preserves all other visibility settings unchanged.
     */
    @Test(timeout = 4000)
    public void test36() throws Throwable {
        // Start from the DEFAULT visibility configuration
        JsonAutoDetect.Value defaultValue = JsonAutoDetect.Value.DEFAULT;

        // Change only the creator visibility from PUBLIC_ONLY to NON_PRIVATE
        JsonAutoDetect.Value withNonPrivateCreator = defaultValue.withCreatorVisibility(JsonAutoDetect.Visibility.NON_PRIVATE);
        assertNotNull(withNonPrivateCreator);

        // Applying DEFAULT as an override replaces all settings back to DEFAULT's values,
        // so the result should be the same object as DEFAULT
        JsonAutoDetect.Value afterOverride = withNonPrivateCreator.withOverrides(defaultValue);
        assertSame(afterOverride, defaultValue);

        // Confirm withCreatorVisibility only changed the creator visibility; others remain as in DEFAULT
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withNonPrivateCreator.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withNonPrivateCreator.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, withNonPrivateCreator.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY,         withNonPrivateCreator.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, withNonPrivateCreator.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, withNonPrivateCreator.getScalarConstructorVisibility());
    }
}
