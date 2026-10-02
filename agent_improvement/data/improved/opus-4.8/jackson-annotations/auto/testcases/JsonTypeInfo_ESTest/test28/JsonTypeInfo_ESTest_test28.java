package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test28 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#withDefaultImpl(Class)} is idempotent:
     * the first call with a new default implementation returns a distinct copy, while
     * a second call with the same class returns the very same instance (no needless copy).
     */
    @Test(timeout = 4000)
    public void withDefaultImpl_isIdempotentForSameClass() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // First call sets a new default impl, producing a fresh Value distinct from EMPTY.
        JsonTypeInfo.Value withObjectImpl = emptyValue.withDefaultImpl(Object.class);

        // Second call with the same class is a no-op and returns the same instance.
        JsonTypeInfo.Value sameImplAgain = withObjectImpl.withDefaultImpl(Object.class);

        assertFalse("Default impl change must not affect id visibility", sameImplAgain.getIdVisible());
        assertNotSame("Setting a default impl must produce a new Value, not EMPTY", sameImplAgain, emptyValue);
        assertSame("Re-applying the same default impl must reuse the existing Value", sameImplAgain, withObjectImpl);
    }
}
