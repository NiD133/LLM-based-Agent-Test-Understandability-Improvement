package org.apache.commons.io.file.attribute;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.file.Path;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class FileTimes_ESTest_test17 extends FileTimes_ESTest_scaffolding {

    /**
     * Verifies that {@link FileTimes#setLastModifiedTime(Path)} rejects a {@code null}
     * path by propagating the {@link NullPointerException} thrown from
     * {@code java.nio.file.Files}.
     */
    @Test(timeout = 4000)
    public void setLastModifiedTimeWithNullPathThrowsNullPointerException() throws Throwable {
        try {
            FileTimes.setLastModifiedTime((Path) null);
            fail("Expected a NullPointerException for a null path");
        } catch (NullPointerException e) {
            // The exception originates from java.nio.file.Files and carries no message.
            verifyException("java.nio.file.Files", e);
        }
    }
}
