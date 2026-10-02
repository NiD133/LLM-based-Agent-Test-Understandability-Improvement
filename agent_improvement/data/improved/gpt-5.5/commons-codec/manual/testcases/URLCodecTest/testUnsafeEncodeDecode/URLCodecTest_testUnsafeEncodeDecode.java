package org.apache.commons.codec.net;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class URLCodecTest_testUnsafeEncodeDecode {

    private static final String UNSAFE_CHARACTERS = "~!@#$%^&()+{}\"\\;:`,/[]";
    private static final String URL_ENCODED_UNSAFE_CHARACTERS =
            "%7E%21%40%23%24%25%5E%26%28%29%2B%7B%7D%22%5C%3B%3A%60%2C%2F%5B%5D";

    private void validateState(final URLCodec urlCodec) {
        // No state-based assertions are needed for this stateless codec scenario.
    }

    @Test
    void testUnsafeEncodeDecode() throws Exception {
        final URLCodec urlCodec = new URLCodec();

        final String encoded = urlCodec.encode(UNSAFE_CHARACTERS);
        assertEquals(URL_ENCODED_UNSAFE_CHARACTERS, encoded, "Unsafe chars URL encoding test");
        assertEquals(UNSAFE_CHARACTERS, urlCodec.decode(encoded), "Unsafe chars URL decoding test");
        validateState(urlCodec);
    }
}
