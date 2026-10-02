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
public class ExtraFieldUtils_ESTest_test01 extends ExtraFieldUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link ExtraFieldUtils#fillExtraField} wraps the
     * {@link ArrayIndexOutOfBoundsException} raised while parsing inconsistent
     * extra-field data into a {@link ZipException}.
     *
     * <p>Here the declared length (11) and the negative offset (-2) do not fit
     * the 2-byte data buffer, so the underlying NTFS field parsing reads out of
     * bounds. {@code ExtraFieldUtils} catches that and rethrows it through
     * {@code ZipUtil} as a corrupt-extra-field {@link ZipException}.</p>
     */
    @Test(timeout = 4000)
    public void fillExtraFieldWithOutOfBoundsRangeThrowsZipException() throws Throwable {
        final byte[] extraFieldData = new byte[2];
        final int invalidOffset = -2;
        final int declaredLength = 11;
        final boolean fromLocalFileHeader = false;

        final X000A_NTFS ntfsField = new X000A_NTFS();

        try {
            ExtraFieldUtils.fillExtraField(
                    ntfsField, extraFieldData, invalidOffset, declaredLength, fromLocalFileHeader);
            fail("Expecting exception: ZipException");
        } catch (final ZipException e) {
            // "Failed to parse corrupt ZIP extra field of type a" is raised by ZipUtil.
            verifyException("org.apache.commons.compress.archivers.zip.ZipUtil", e);
        }
    }
}
