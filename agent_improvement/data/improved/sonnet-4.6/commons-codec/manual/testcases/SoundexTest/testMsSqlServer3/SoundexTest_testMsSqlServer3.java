package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.apache.commons.codec.AbstractStringEncoderTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class SoundexTest_testMsSqlServer3 extends AbstractStringEncoderTest<Soundex> {

    @Override
    protected Soundex createStringEncoder() {
        return new Soundex();
    }

    /**
     * Verifies Soundex encoding of common English first names against expected codes
     * from the MS SQL Server reference: https://databases.about.com/library/weekly/aa042901a.htm
     *
     * Each row maps a name to its 4-character Soundex code (first letter + 3 digits).
     * Note that "Ann" and "Anne" produce the same code (A500) because trailing 'e' is silent.
     */
    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({
        "Ann,      A500",
        "Andrew,   A536",
        "Janet,    J530",
        "Margaret, M626",
        "Steven,   S315",
        "Michael,  M240",
        "Robert,   R163",
        "Laura,    L600",
        "Anne,     A500"
    })
    void testMsSqlServer3(String name, String expectedSoundexCode) {
        assertEquals(expectedSoundexCode.trim(), getStringEncoder().encode(name.trim()));
    }
}
