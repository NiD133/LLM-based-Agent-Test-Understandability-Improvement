package org.apache.commons.codec.language;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SoundexTest_testMsSqlServer3 {

    private static final SoundexExample[] MS_SQL_SERVER_EXAMPLES = {
            new SoundexExample("Ann", "A500"),
            new SoundexExample("Andrew", "A536"),
            new SoundexExample("Janet", "J530"),
            new SoundexExample("Margaret", "M626"),
            new SoundexExample("Steven", "S315"),
            new SoundexExample("Michael", "M240"),
            new SoundexExample("Robert", "R163"),
            new SoundexExample("Laura", "L600"),
            new SoundexExample("Anne", "A500") };

    private final Soundex soundex = new Soundex();

    /**
     * Examples for MS SQLServer from https://databases.about.com/library/weekly/aa042901a.htm
     */
    @Test
    void testMsSqlServer3() {
        for (final SoundexExample example : MS_SQL_SERVER_EXAMPLES) {
            assertEncodesTo(example.expectedCode, example.name);
        }
    }

    private void assertEncodesTo(final String expectedCode, final String name) {
        assertEquals(expectedCode, soundex.encode(name));
    }

    private static final class SoundexExample {
        private final String name;
        private final String expectedCode;

        private SoundexExample(final String name, final String expectedCode) {
            this.name = name;
            this.expectedCode = expectedCode;
        }
    }
}
