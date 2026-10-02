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

@Timeout(3) // Try to avoid occasional hang when testing
class Md5CryptTest {

    /** Pattern that all valid "$1$" MD5 crypt outputs must match: prefix, up-to-8-char salt, hash. */
    private static final String MD5_HASH_PATTERN = "^\\$1\\$[a-zA-Z0-9./]{0,8}\\$.{1,}$";

    @Test
    void testCtorDeprecated() {
        assertNotNull(new Md5Crypt());
    }

    @Test
    void testInvalidPrefix() {
        final byte[] keyBytes = { 1, 2, 3, 4, 5 };
        final String saltEndingWithBang =
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!";
        final String saltWithoutBang =
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        // Prefix without enclosing '$' delimiters is rejected before any regex evaluation
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes, saltEndingWithBang, "(.*a){10000}"));
        // Prefix with '$' delimiters but salt ends with '!' so it cannot match the prefix pattern
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes, saltEndingWithBang, "$(.*a){10000}$"));
        // Prefix with '$' delimiters and salt without '!' also fails to match the prefix pattern
        assertThrows(IllegalArgumentException.class,
                () -> Md5Crypt.md5Crypt(keyBytes, saltWithoutBang, "$(.*a){10000}$"));
    }

    @Test
    void testMd5CryptBytes() {
        // An empty Bytearray equals an empty String
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt(new byte[0], "$1$foo"));
        // UTF-8 stores ä "a with dieresis" as two bytes 0xc3 0xa4.
        assertEquals("$1$./$52agTEQZs877L9jyJnCNZ1", Crypt.crypt("täst", "$1$./$"));
        // ISO-8859-1 stores "a with dieresis" as single byte 0xe4.
        assertEquals("$1$./$J2UbKzGe0Cpe63WZAt6p//", Crypt.crypt("täst".getBytes(StandardCharsets.ISO_8859_1), "$1$./$"));
    }

    @Test
    void testMd5CryptExplicitCall() {
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes()).matches(MD5_HASH_PATTERN));
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches(MD5_HASH_PATTERN));
    }

    @Test
    void testMd5CryptExplicitCallWithThreadLocalRandom() {
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), threadLocalRandom).matches(MD5_HASH_PATTERN));
        assertTrue(Md5Crypt.md5Crypt("secret".getBytes(), (String) null).matches(MD5_HASH_PATTERN));
    }

    @Test
    void testMd5CryptLongInput() {
        assertEquals("$1$1234$MoxekaNNUgfPRVqoeYjCD/", Crypt.crypt("12345678901234567890", "$1$1234"));
    }

    @Test
    void testMd5CryptNullData() {
        assertThrows(NullPointerException.class, () -> Md5Crypt.md5Crypt((byte[]) null));
    }

    @Test
    void testMd5CryptStrings() {
        // empty data
        assertEquals("$1$foo$9mS5ExwgIECGE5YKlD5o91", Crypt.crypt("", "$1$foo"));
        // salt gets cut at dollar sign
        assertEquals("$1$1234$ImZYBLmYC.rbBKg9ERxX70", Crypt.crypt("secret", "$1$1234"));
        assertEquals("$1$1234$ImZYBLmYC.rbBKg9ERxX70", Crypt.crypt("secret", "$1$1234$567"));
        assertEquals("$1$1234$ImZYBLmYC.rbBKg9ERxX70", Crypt.crypt("secret", "$1$1234$567$890"));
        // salt gets cut at maximum length (8 characters)
        assertEquals("$1$12345678$hj0uLpdidjPhbMMZeno8X/", Crypt.crypt("secret", "$1$1234567890123456"));
        assertEquals("$1$12345678$hj0uLpdidjPhbMMZeno8X/", Crypt.crypt("secret", "$1$123456789012345678"));
    }

    @Test
    void testMd5CryptWithEmptySalt() {
        assertThrows(IllegalArgumentException.class, () -> Md5Crypt.md5Crypt("secret".getBytes(), ""));
    }

    @Test
    void testZeroOutInput() {
        final byte[] buffer = new byte[200];
        Arrays.fill(buffer, (byte) 'A');
        Md5Crypt.md5Crypt(buffer);
        // input password is 0-filled on return
        assertArrayEquals(new byte[buffer.length], buffer);
    }

}
