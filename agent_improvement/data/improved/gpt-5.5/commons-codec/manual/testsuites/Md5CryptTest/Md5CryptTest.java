/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.codec.digest;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

@Timeout(3) // Try to avoid occasional hang when testing.
class Md5CryptTest {

    private static final String GENERATED_MD5_CRYPT_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";
    private static final byte[] INVALID_PREFIX_KEY = { 1, 2, 3, 4, 5 };
    private static final String LONG_INVALID_SALT =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!";
    private static final String LONG_INVALID_SALT_WITHOUT_BANG =
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    private static final String REGEX_LIKE_PREFIX = "(.*a){10000}";
    private static final String REGEX_LIKE_PREFIX_WITH_DOLLARS = "$(.*a){10000}$";
    private static final String SECRET = "secret";
    private static final String SHORT_SALT = "$1$1234";
    private static final String SHORT_SALT_HASH = "$1$1234$ImZYBLmYC.rbBKg9ERxX70";
    private static final String UTF8_DIERESIS_TEXT = "t\u00e4st";

    private static void assertGeneratedMd5CryptFormat(final String hash) {
        assertTrue(hash.matches(GENERATED_MD5_CRYPT_PATTERN));
    }

    @Test
    void testCtorDeprecated() {
        assertNotNull(new Md5Crypt());
    }

    @Test
    void testInvalidPrefix() {
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(INVALID_PREFIX_KEY, LONG_INVALID_SALT, REGEX_LIKE_PREFIX));
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(INVALID_PREFIX_KEY, LONG_INVALID_SALT, REGEX_LIKE_PREFIX_WITH_DOLLARS));
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(INVALID_PREFIX_KEY, LONG_INVALID_SALT_WITHOUT_BANG,
                        REGEX_LIKE_PREFIX_WITH_DOLLARS));
    }

    @Test
    void testMd5CryptBytes() {
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt(new byte[0], "$1$foo"));
        assertEquals("$1$./$52agTEQZs877L9jyJnCNZ1", Crypt.crypt(UTF8_DIERESIS_TEXT, "$1$./$"));
        assertEquals("$1$./$J2UbKzGe0Cpe63WZAt6p//",
                Crypt.crypt(UTF8_DIERESIS_TEXT.getBytes(StandardCharsets.ISO_8859_1), "$1$./$"));
    }

    @Test
    void testMd5CryptExplicitCall() {
        assertGeneratedMd5CryptFormat(Md5Crypt.md5Crypt(SECRET.getBytes()));
        assertGeneratedMd5CryptFormat(Md5Crypt.md5Crypt(SECRET.getBytes(), (String) null));
    }

    @Test
    void testMd5CryptExplicitCallWithThreadLocalRandom() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();

        assertGeneratedMd5CryptFormat(Md5Crypt.md5Crypt(SECRET.getBytes(), threadLocalRandom));
        assertGeneratedMd5CryptFormat(Md5Crypt.md5Crypt(SECRET.getBytes(), (String) null));
    }

    @Test
    void testMd5CryptLongInput() {
        assertEquals("$1$1234$MoxekaNNUgfPRVqoeYjCD/", Crypt.crypt("12345678901234567890", SHORT_SALT));
    }

    @Test
    void testMd5CryptNullData() {
        assertThrows(NullPointerException.class, () -> Md5Crypt.md5Crypt((byte[]) null));
    }

    @Test
    void testMd5CryptStrings() {
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt("", "$1$foo"));

        assertEquals(SHORT_SALT_HASH, Crypt.crypt(SECRET, SHORT_SALT));
        assertEquals(SHORT_SALT_HASH, Crypt.crypt(SECRET, "$1$1234$567"));
        assertEquals(SHORT_SALT_HASH, Crypt.crypt(SECRET, "$1$1234$567$890"));

        assertEquals("$1$12345678$hj0uLpdidjPhbMMZeno8X/", Crypt.crypt(SECRET, "$1$1234567890123456"));
        assertEquals("$1$12345678$hj0uLpdidjPhbMMZeno8X/", Crypt.crypt(SECRET, "$1$123456789012345678"));
    }

    @Test
    void testMd5CryptWithEmptySalt() {
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt(SECRET.getBytes(), ""));
    }

    @Test
    void testZeroOutInput() {
        final byte[] buffer = new byte[200];
        Arrays.fill(buffer, (byte) 'A');

        Md5Crypt.md5Crypt(buffer);

        assertArrayEquals(new byte[buffer.length], buffer);
    }

}
