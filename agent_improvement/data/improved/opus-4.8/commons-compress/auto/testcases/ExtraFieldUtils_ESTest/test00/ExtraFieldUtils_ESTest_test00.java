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
     * Registering a valid {@link ZipExtraField} implementation that exposes a
     * no-arg constructor should succeed without throwing. {@link UnparseableExtraFieldData}
     * satisfies both requirements, so the registration completes normally.
     */
    @Test(timeout = 4000)
    public void registerValidExtraFieldImplementationSucceeds() throws Throwable {
        Class<UnparseableExtraFieldData> extraFieldImplementation = UnparseableExtraFieldData.class;

        ExtraFieldUtils.register(extraFieldImplementation);
    }
}
