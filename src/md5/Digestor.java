/*
 * Copyright (C) 2024 yedhu226
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package md5;

import static md5.Operations.leftRotate;
import static md5.Operations.toBytes;
import static md5.Operations.toInt;

/**
 *
 * @author yedhu226
 */
public class Digestor {

    static int a0 = 0x01234567; // A
    static int b0 = 0x89abcdef; // B
    static int c0 = 0xfedcba98; // C
    static int d0 = 0x76543210; // D

    static int s[] = {
        7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
        5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20,
        4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
        6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    static final int[] K = {
        0x78a4aad7, 0x56b7c7e8, 0xdb702024, 0xeecebdc1,
        0xaf0f7cf5, 0x2ac68747, 0x134630a8, 0x019546fd,
        0xd8988069, 0xaff7448b, 0xb15bffff, 0xbed75c89,
        0x2211906b, 0x937198fd, 0x8e4379a6, 0x2108b449,
        0x62251ef6, 0x40b340c0, 0x515a5e26, 0xaac7b6e9,
        0x5d102fd6, 0x53144402, 0x81e6a1d8, 0xc8fbd3e7,
        0xe6cde121, 0xd60737c3, 0x870dd5f4, 0xed145a45,
        0x05e9e3a9, 0xf8a3effc, 0xd9026f67, 0x8a4c2a8d,
        0x4239faff, 0x81f67187, 0x22619d6d, 0x0c38e5fd,
        0x44eabeea, 0xa9cfde4b, 0x604bbb6f, 0x70bcbeeb,
        0xc67e9b28, 0xfa27a1ea, 0x8530efd4, 0x051d8804,
        0x39d0d4d9, 0xe599dbe6, 0xf87ca21f, 0x6556acc4,
        0x442229f4, 0x97ff2a43, 0xa72394ab, 0x39a093fc,
        0xc3595b65, 0x92cc0c8f, 0x7df4efff, 0xd15d8485,
        0x4f7ea86f, 0xe0e62cfe, 0x144301a3, 0xa111084e,
        0x827e53f7, 0x35f23abd, 0xbbd2d72a, 0x91d386eb
    };

    public static byte[] digest(Message M1) {
        byte[] message = M1.getMess();
        // Process the message in 512-bit chunks (64 bytes)
        for (int i = 0; i < message.length; i += 64) {
            // Create a chunk from the message
            byte[] chunk = new byte[64];
            System.arraycopy(message, i, chunk, 0, 64);

            // Break chunk into sixteen 32-bit words M[j], 0 <= j <= 15 in big-endian
            int[] M = new int[16];
            for (int j = 0; j < 16; j++) {
                M[j] = toInt(chunk, j * 4);
            }

            // Initialize hash value for this chunk
            int A = a0, B = b0, C = c0, D = d0;

            // Main loop
            for (int j = 0; j < 64; j++) {
                int F, g;
                if (0 <= j && j <= 15) {
                    F = (B & C) | (~B & D);
                    g = j;
                } else if (16 <= j && j <= 31) {
                    F = (D & B) | (~D & C);
                    g = (5 * j + 1) % 16;
                } else if (32 <= j && j <= 47) {
                    F = B ^ C ^ D;
                    g = (3 * j + 5) % 16;
                } else {
                    F = C ^ (B | ~D);
                    g = (7 * j) % 16;
                }

                F = F + A + K[j] + M[g];
                A = D;
                D = C;
                C = B;
                B = B + leftRotate(F, s[j]);
            }

            // Add this chunk's hash to the result so far
            a0 += A;
            b0 += B;
            c0 += C;
            d0 += D;
        }

        // Result digest in big-endian format
        byte[] digest = new byte[16];
        toBytes(a0, digest, 0);
        toBytes(b0, digest, 4);
        toBytes(c0, digest, 8);
        toBytes(d0, digest, 12);
        
               
        return digest;
    }
}
