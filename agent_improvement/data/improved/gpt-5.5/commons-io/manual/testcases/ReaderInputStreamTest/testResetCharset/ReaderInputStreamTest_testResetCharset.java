package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testResetCharset {

    @Test
    void testResetCharset() {
        final ReaderInputStream.Builder builder = ReaderInputStream.builder()
                .setReader(new StringReader("\uD800"))
                .setCharset((Charset) null);

        assertNotNull(builder.getCharset());
    }
}
