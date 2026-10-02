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
public class JsonAutoDetect_ESTest_test07 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value with only CREATOR set to NONE leaves all
     * other accessor visibilities at DEFAULT, and that equals() can be called
     * between default-visibility and single-override Value instances.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Build a Value with full default visibility settings
        JsonAutoDetect.Value defaultConfig = JsonAutoDetect.Value.defaultVisibility();

        // Build a Value where only the CREATOR accessor is restricted to NONE;
        // all other accessors remain at DEFAULT
        PropertyAccessor creatorAccessor = PropertyAccessor.CREATOR;
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value creatorNoneConfig = JsonAutoDetect.Value.construct(creatorAccessor, noneVisibility);

        // equals() result is intentionally unused — call exercises the equality path
        defaultConfig.equals(creatorNoneConfig);

        // All non-CREATOR accessors must remain DEFAULT
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneConfig.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneConfig.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneConfig.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneConfig.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorNoneConfig.getFieldVisibility());

        // CREATOR accessor must reflect the NONE override
        assertEquals(JsonAutoDetect.Visibility.NONE, creatorNoneConfig.getCreatorVisibility());
    }
}
