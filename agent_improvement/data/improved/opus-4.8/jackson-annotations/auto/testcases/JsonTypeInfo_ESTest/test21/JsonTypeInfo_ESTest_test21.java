package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test21 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies the behaviour of {@link JsonTypeInfo.Value#withRequireTypeIdForSubtypes(Boolean)}:
     * <ul>
     *   <li>Changing the value (from EMPTY's {@code null} to {@code false}) returns a new instance.</li>
     *   <li>Calling it again with the same {@code Boolean} reference is a no-op and returns the
     *       same instance, since the field is compared by reference identity.</li>
     *   <li>Unrelated settings (id visibility, write-type-id-for-default-impl) are unaffected.</li>
     * </ul>
     */
    @Test(timeout = 4000)
    public void withRequireTypeIdForSubtypes_secondCallWithSameValueReturnsSameInstance() throws Throwable {
        JsonTypeInfo.Value empty = JsonTypeInfo.Value.EMPTY;
        Boolean requireTypeId = Boolean.FALSE;

        JsonTypeInfo.Value withRequireSet = empty.withRequireTypeIdForSubtypes(requireTypeId);
        JsonTypeInfo.Value withRequireSetAgain = withRequireSet.withRequireTypeIdForSubtypes(requireTypeId);

        // The first call changed the value, so a fresh instance was created.
        assertNotSame(withRequireSetAgain, empty);
        // The second call passed the identical Boolean reference, so no new instance was created.
        assertSame(withRequireSetAgain, withRequireSet);

        // Unrelated settings keep their default values.
        assertFalse(withRequireSetAgain.getIdVisible());
        assertTrue(withRequireSetAgain.shouldWriteTypeIdForDefaultImpl());
    }
}
