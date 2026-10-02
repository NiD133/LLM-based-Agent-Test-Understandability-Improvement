package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Value;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test20 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies the single-accessor factory {@link Value#construct(PropertyAccessor, Visibility)}:
     * it should apply the given visibility only to the named accessor (here CREATOR)
     * and leave every other accessor at {@link Visibility#DEFAULT}.
     */
    @Test(timeout = 4000)
    public void constructForCreatorSetsOnlyCreatorVisibility() throws Throwable {
        // Obtain a NONE visibility value (taken from a Value whose accessors are all NONE).
        Value allNone = Value.construct(Visibility.NONE, Visibility.NONE, Visibility.NONE,
                Visibility.NONE, Visibility.NONE, Visibility.NONE);
        Visibility noneVisibility = allNone.getFieldVisibility();

        // Build a Value that sets only the CREATOR accessor to NONE.
        Value creatorOnly = Value.construct(PropertyAccessor.CREATOR, noneVisibility);

        // CREATOR takes the requested visibility; all other accessors stay at DEFAULT.
        assertEquals(Visibility.NONE, creatorOnly.getCreatorVisibility());
        assertEquals(Visibility.DEFAULT, creatorOnly.getSetterVisibility());
        assertEquals(Visibility.DEFAULT, creatorOnly.getScalarConstructorVisibility());
        assertEquals(Visibility.DEFAULT, creatorOnly.getGetterVisibility());
        assertEquals(Visibility.DEFAULT, creatorOnly.getIsGetterVisibility());
    }
}
