package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test11 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonSetter.Value#withValueNulls(Nulls, Nulls)} overrides
     * both the value-nulls and content-nulls settings with the supplied value.
     *
     * The source {@link JsonSetter} annotation reports {@code null} for both
     * {@code nulls()} and {@code contentNulls()}, so the {@code Value} built from it
     * starts at {@link Nulls#DEFAULT}. Applying {@link Nulls#AS_EMPTY} to both
     * settings should produce a {@code Value} whose value- and content-nulls are
     * both {@code AS_EMPTY}.
     */
    @Test(timeout = 4000)
    public void withValueNulls_setsBothValueAndContentNulls() throws Throwable {
        // A JsonSetter annotation whose nulls()/contentNulls() both report null.
        JsonSetter sourceAnnotation = mock(JsonSetter.class, CALLS_REAL_METHODS);
        doReturn((Nulls) null).when(sourceAnnotation).nulls();
        doReturn((Nulls) null).when(sourceAnnotation).contentNulls();

        JsonSetter.Value defaultValue = JsonSetter.Value.from(sourceAnnotation);

        JsonSetter.Value updatedValue =
                defaultValue.withValueNulls(Nulls.AS_EMPTY, Nulls.AS_EMPTY);

        assertEquals(Nulls.AS_EMPTY, updatedValue.getValueNulls());
        assertEquals(Nulls.AS_EMPTY, updatedValue.getContentNulls());
    }
}
