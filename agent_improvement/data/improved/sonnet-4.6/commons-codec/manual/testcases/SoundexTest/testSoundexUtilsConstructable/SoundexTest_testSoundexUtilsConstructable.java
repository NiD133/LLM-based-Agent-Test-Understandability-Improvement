package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.apache.commons.codec.EncoderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SoundexTest_testSoundexUtilsConstructable extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies that {@link SoundexUtils} has a public, no-arg constructor and can be
     * instantiated without throwing an exception. Although {@code SoundexUtils} is
     * effectively a static-utility class, its constructor must remain accessible so
     * that tools (e.g. dependency-injection containers, serialisation frameworks) that
     * reflectively construct classes do not fail.
     */
    @Test
    @DisplayName("SoundexUtils should be instantiable via its public no-arg constructor")
    void testSoundexUtilsConstructable() {
        SoundexUtils instance = new SoundexUtils();
        assertNotNull(instance, "SoundexUtils should be constructable and not null");
    }
}
