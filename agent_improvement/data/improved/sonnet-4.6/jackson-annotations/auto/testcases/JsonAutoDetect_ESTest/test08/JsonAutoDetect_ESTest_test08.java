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
public class JsonAutoDetect_ESTest_test08 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value with CREATOR accessor and PUBLIC_ONLY visibility
     * sets only the creator visibility to PUBLIC_ONLY, leaving all other visibilities at DEFAULT.
     * Also verifies the Value is not equal to null.
     */
    @Test(timeout = 4000)
    public void constructWithCreatorAccessor_setsOnlyCreatorVisibilityToPublicOnly() throws Throwable {
        PropertyAccessor creatorAccessor = PropertyAccessor.CREATOR;
        JsonAutoDetect.Visibility publicOnlyVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(creatorAccessor, publicOnlyVisibility);

        boolean equalsNull = value.equals((Object) null);
        assertFalse(equalsNull);
        assertEquals("Getter visibility should remain DEFAULT when only CREATOR accessor is configured",
                JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals("Is-getter visibility should remain DEFAULT when only CREATOR accessor is configured",
                JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals("Creator visibility should be set to PUBLIC_ONLY",
                JsonAutoDetect.Visibility.PUBLIC_ONLY, value.getCreatorVisibility());
        assertEquals("Scalar constructor visibility should remain DEFAULT when only CREATOR accessor is configured",
                JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
