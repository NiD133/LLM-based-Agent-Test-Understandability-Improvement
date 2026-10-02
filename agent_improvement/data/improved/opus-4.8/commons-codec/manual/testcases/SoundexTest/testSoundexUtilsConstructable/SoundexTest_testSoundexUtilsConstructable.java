package org.apache.commons.codec.language;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Tests that the {@link SoundexUtils} helper class can be instantiated.
 *
 * <p>
 * {@code SoundexUtils} is a package-private utility class. This test simply
 * exercises its (default) constructor so that the constructor is covered, even
 * though all of the class's useful methods are static.
 * </p>
 */
public class SoundexTest_testSoundexUtilsConstructable extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void soundexUtilsCanBeInstantiated() {
        // Invoking the constructor is enough; we only verify it does not throw.
        new SoundexUtils();
    }
}
