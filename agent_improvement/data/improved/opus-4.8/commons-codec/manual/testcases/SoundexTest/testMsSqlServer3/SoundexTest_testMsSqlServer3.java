package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Soundex} reproduces the reference Soundex codes published by Microsoft for
 * SQL Server.
 *
 * <p>The expected name-to-code pairs are taken from the MS SQL Server examples at
 * https://databases.about.com/library/weekly/aa042901a.htm</p>
 */
public class SoundexTest_testMsSqlServer3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    @Test
    void testMsSqlServer3() {
        // Each row pairs an input name with the Soundex code MS SQL Server produces for it.
        assertSoundex("A500", "Ann");
        assertSoundex("A536", "Andrew");
        assertSoundex("J530", "Janet");
        assertSoundex("M626", "Margaret");
        assertSoundex("S315", "Steven");
        assertSoundex("M240", "Michael");
        assertSoundex("R163", "Robert");
        assertSoundex("L600", "Laura");
        assertSoundex("A500", "Anne");
    }

    /** Asserts that encoding {@code name} yields the {@code expectedCode}. */
    private void assertSoundex(final String expectedCode, final String name) {
        assertEquals(expectedCode, getStringEncoder().encode(name));
    }
}
