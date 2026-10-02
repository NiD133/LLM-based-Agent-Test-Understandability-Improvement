package org.apache.commons.compress.archivers.zip;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.zip.ZipException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ExtraFieldUtils_ESTest_test00 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that registering {@link UnparseableExtraFieldData} via
     * {@link ExtraFieldUtils#register(Class)} completes without throwing an exception.
     * {@code UnparseableExtraFieldData} satisfies the contract required by {@code register}:
     * it implements {@link ZipExtraField} and exposes a public no-arg constructor.
     */
    @Test(timeout = 4000)
    public void test00_registerUnparseableExtraFieldData_doesNotThrow() throws Throwable {
        Class<UnparseableExtraFieldData> unparseableExtraFieldDataClass = UnparseableExtraFieldData.class;
        ExtraFieldUtils.register(unparseableExtraFieldDataClass);
    }
}
