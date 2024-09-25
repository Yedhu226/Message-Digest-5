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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 *
 * @author yedhu226
 */
public class Operations {

    static int leftRotate(int n, int d) {
        return (n << d) | (n >>> (32 - d));
    }

    static int toInt(byte[] bytes, int offset) {
        return ByteBuffer.wrap(bytes, offset, 4).order(ByteOrder.BIG_ENDIAN).getInt();
    }

    // Converts an integer to a 4-byte array and stores it in 'bytes' at the specified offset (big-endian)
    static void toBytes(int val, byte[] bytes, int offset) {
        ByteBuffer.wrap(bytes, offset, 4).order(ByteOrder.BIG_ENDIAN).putInt(val);
    }
}
