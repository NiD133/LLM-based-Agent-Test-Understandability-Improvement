package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class ReaderInputStreamTest_testResetCharsetName {

    // A surrogate character used to exercise the builder's charset fallback path
    private static final String SURROGATE_CHAR = "\uD800";

    @Test
    void testResetCharsetName() {
        // Passing null as the charset name should not leave the builder's charset as null;
        // it must reset to a non-null default charset.
        ReaderInputStream.Builder builder = ReaderInputStream.builder()
                .setReader(new StringReader(SURROGATE_CHAR))
                .setCharset((String) null);

        Charset resolvedCharset = builder.getCharset();

        assertNotNull(resolvedCharset,
                "Builder should resolve a non-null default charset when charset name is set to null");
    }
}
