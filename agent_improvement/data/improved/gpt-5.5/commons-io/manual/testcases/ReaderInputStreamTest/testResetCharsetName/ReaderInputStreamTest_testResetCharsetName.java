package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testResetCharsetName {

    @Test
    void testResetCharsetName() {
        final ReaderInputStream.Builder builder = ReaderInputStream.builder()
                .setReader(new StringReader("\uD800"))
                .setCharset((String) null);

        assertNotNull(builder.getCharset());
    }
}
