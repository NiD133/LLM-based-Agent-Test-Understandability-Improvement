package org.apache.commons.compress.harmony.pack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CodecEncodingTest_testCanonicalEncodings {

    /**
     * Provides (specifier index, expected codec string) pairs for all 115 canonical
     * encodings defined in the Pack200 specification (section 6.7.3).
     *
     * The encodings are grouped below according to the bands they typically cover:
     *   indices  1-16  : B=1..4, H=256, all (S,D) combinations
     *   indices 17-46  : B=5,    H=4/16/32/64/128, S=0..2, D=0 and D=1
     *   indices 47-115 : B=2..4, H=192..252 and small-H signed variants
     */
    static Stream<Arguments> canonicalEncodings() {
        return Stream.of(
            // --- B=1..4, H=256: (S=0,D=0), (S=1,D=0), (S=0,D=1), (S=1,D=1) ---
            Arguments.of(  1, "(1,256)"     ),
            Arguments.of(  2, "(1,256,1)"   ),
            Arguments.of(  3, "(1,256,0,1)" ),
            Arguments.of(  4, "(1,256,1,1)" ),
            Arguments.of(  5, "(2,256)"     ),
            Arguments.of(  6, "(2,256,1)"   ),
            Arguments.of(  7, "(2,256,0,1)" ),
            Arguments.of(  8, "(2,256,1,1)" ),
            Arguments.of(  9, "(3,256)"     ),
            Arguments.of( 10, "(3,256,1)"   ),
            Arguments.of( 11, "(3,256,0,1)" ),
            Arguments.of( 12, "(3,256,1,1)" ),
            Arguments.of( 13, "(4,256)"     ),
            Arguments.of( 14, "(4,256,1)"   ),
            Arguments.of( 15, "(4,256,0,1)" ),
            Arguments.of( 16, "(4,256,1,1)" ),

            // --- B=5, H=4..128, D=0: S cycles through 0, 1, 2 ---
            Arguments.of( 17, "(5,4)"       ),
            Arguments.of( 18, "(5,4,1)"     ),
            Arguments.of( 19, "(5,4,2)"     ),
            Arguments.of( 20, "(5,16)"      ),
            Arguments.of( 21, "(5,16,1)"    ),
            Arguments.of( 22, "(5,16,2)"    ),
            Arguments.of( 23, "(5,32)"      ),
            Arguments.of( 24, "(5,32,1)"    ),
            Arguments.of( 25, "(5,32,2)"    ),
            Arguments.of( 26, "(5,64)"      ),
            Arguments.of( 27, "(5,64,1)"    ),
            Arguments.of( 28, "(5,64,2)"    ),
            Arguments.of( 29, "(5,128)"     ),
            Arguments.of( 30, "(5,128,1)"   ),
            Arguments.of( 31, "(5,128,2)"   ),

            // --- B=5, H=4..128, D=1: S cycles through 0, 1, 2 ---
            Arguments.of( 32, "(5,4,0,1)"   ),
            Arguments.of( 33, "(5,4,1,1)"   ),
            Arguments.of( 34, "(5,4,2,1)"   ),
            Arguments.of( 35, "(5,16,0,1)"  ),
            Arguments.of( 36, "(5,16,1,1)"  ),
            Arguments.of( 37, "(5,16,2,1)"  ),
            Arguments.of( 38, "(5,32,0,1)"  ),
            Arguments.of( 39, "(5,32,1,1)"  ),
            Arguments.of( 40, "(5,32,2,1)"  ),
            Arguments.of( 41, "(5,64,0,1)"  ),
            Arguments.of( 42, "(5,64,1,1)"  ),
            Arguments.of( 43, "(5,64,2,1)"  ),
            Arguments.of( 44, "(5,128,0,1)" ),
            Arguments.of( 45, "(5,128,1,1)" ),
            Arguments.of( 46, "(5,128,2,1)" ),

            // --- B=2, large H (unsigned), then small H (signed) ---
            Arguments.of( 47, "(2,192)"     ),
            Arguments.of( 48, "(2,224)"     ),
            Arguments.of( 49, "(2,240)"     ),
            Arguments.of( 50, "(2,248)"     ),
            Arguments.of( 51, "(2,252)"     ),
            Arguments.of( 52, "(2,8,0,1)"   ),
            Arguments.of( 53, "(2,8,1,1)"   ),
            Arguments.of( 54, "(2,16,0,1)"  ),
            Arguments.of( 55, "(2,16,1,1)"  ),
            Arguments.of( 56, "(2,32,0,1)"  ),
            Arguments.of( 57, "(2,32,1,1)"  ),
            Arguments.of( 58, "(2,64,0,1)"  ),
            Arguments.of( 59, "(2,64,1,1)"  ),
            Arguments.of( 60, "(2,128,0,1)" ),
            Arguments.of( 61, "(2,128,1,1)" ),
            Arguments.of( 62, "(2,192,0,1)" ),
            Arguments.of( 63, "(2,192,1,1)" ),
            Arguments.of( 64, "(2,224,0,1)" ),
            Arguments.of( 65, "(2,224,1,1)" ),
            Arguments.of( 66, "(2,240,0,1)" ),
            Arguments.of( 67, "(2,240,1,1)" ),
            Arguments.of( 68, "(2,248,0,1)" ),
            Arguments.of( 69, "(2,248,1,1)" ),

            // --- B=3, large H (unsigned), then small H (signed) ---
            Arguments.of( 70, "(3,192)"     ),
            Arguments.of( 71, "(3,224)"     ),
            Arguments.of( 72, "(3,240)"     ),
            Arguments.of( 73, "(3,248)"     ),
            Arguments.of( 74, "(3,252)"     ),
            Arguments.of( 75, "(3,8,0,1)"   ),
            Arguments.of( 76, "(3,8,1,1)"   ),
            Arguments.of( 77, "(3,16,0,1)"  ),
            Arguments.of( 78, "(3,16,1,1)"  ),
            Arguments.of( 79, "(3,32,0,1)"  ),
            Arguments.of( 80, "(3,32,1,1)"  ),
            Arguments.of( 81, "(3,64,0,1)"  ),
            Arguments.of( 82, "(3,64,1,1)"  ),
            Arguments.of( 83, "(3,128,0,1)" ),
            Arguments.of( 84, "(3,128,1,1)" ),
            Arguments.of( 85, "(3,192,0,1)" ),
            Arguments.of( 86, "(3,192,1,1)" ),
            Arguments.of( 87, "(3,224,0,1)" ),
            Arguments.of( 88, "(3,224,1,1)" ),
            Arguments.of( 89, "(3,240,0,1)" ),
            Arguments.of( 90, "(3,240,1,1)" ),
            Arguments.of( 91, "(3,248,0,1)" ),
            Arguments.of( 92, "(3,248,1,1)" ),

            // --- B=4, large H (unsigned), then small H (signed) ---
            Arguments.of( 93, "(4,192)"     ),
            Arguments.of( 94, "(4,224)"     ),
            Arguments.of( 95, "(4,240)"     ),
            Arguments.of( 96, "(4,248)"     ),
            Arguments.of( 97, "(4,252)"     ),
            Arguments.of( 98, "(4,8,0,1)"   ),
            Arguments.of( 99, "(4,8,1,1)"   ),
            Arguments.of(100, "(4,16,0,1)"  ),
            Arguments.of(101, "(4,16,1,1)"  ),
            Arguments.of(102, "(4,32,0,1)"  ),
            Arguments.of(103, "(4,32,1,1)"  ),
            Arguments.of(104, "(4,64,0,1)"  ),
            Arguments.of(105, "(4,64,1,1)"  ),
            Arguments.of(106, "(4,128,0,1)" ),
            Arguments.of(107, "(4,128,1,1)" ),
            Arguments.of(108, "(4,192,0,1)" ),
            Arguments.of(109, "(4,192,1,1)" ),
            Arguments.of(110, "(4,224,0,1)" ),
            Arguments.of(111, "(4,224,1,1)" ),
            Arguments.of(112, "(4,240,0,1)" ),
            Arguments.of(113, "(4,240,1,1)" ),
            Arguments.of(114, "(4,248,0,1)" ),
            Arguments.of(115, "(4,248,1,1)" )
        );
    }

    @ParameterizedTest
    @MethodSource("canonicalEncodings")
    void testCanonicalEncodings(final int i, final String expectedCodec) throws IOException, Pack200Exception {
        assertEquals(expectedCodec, CodecEncoding.getCodec(i, null, null).toString());
    }
}
