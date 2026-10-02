package org.apache.commons.io.input;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ReaderInputStreamTest_testReadEofTwice {

    private static final String UTF_16 = StandardCharsets.UTF_16.name();

    private static final String UTF_8 = StandardCharsets.UTF_8.name();

    private static final String TEST_STRING = "\u00e0 peine arriv\u00e9s nous entr\u00e2mes dans sa chambre";

    private static final String LARGE_TEST_STRING = StringUtils.repeat(TEST_STRING, 100);

    private final Random random = new Random();

    @Test
    void testReadEofTwice() throws IOException {
        try (ReaderInputStream reader = ReaderInputStream.builder().setCharset(StandardCharsets.UTF_8).setReader(new StringReader("123")).get()) {
            assertEquals('1', reader.read());
            assertEquals('2', reader.read());
            assertEquals('3', reader.read());
            assertEquals(-1, reader.read());
            assertEquals(-1, reader.read());
        }
    }
}
