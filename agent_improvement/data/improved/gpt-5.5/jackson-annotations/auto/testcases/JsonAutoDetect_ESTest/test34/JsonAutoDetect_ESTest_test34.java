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
public class JsonAutoDetect_ESTest_test34 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test34() throws Throwable {
        JsonAutoDetect.Visibility noVisibility = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allAccessorsHidden = JsonAutoDetect.Value.construct(
                noVisibility, noVisibility, noVisibility,
                noVisibility, noVisibility, noVisibility);
        assertNotNull(allAccessorsHidden);

        JsonAutoDetect.Visibility fieldVisibility = allAccessorsHidden.getFieldVisibility();

        // Getter visibility is already NONE, so applying the field visibility keeps the value equal.
        JsonAutoDetect.Value updatedGetterVisibility = allAccessorsHidden.withGetterVisibility(fieldVisibility);

        assertTrue(updatedGetterVisibility.equals((Object) allAccessorsHidden));
    }
}
