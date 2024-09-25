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

/**
 *
 * @author yedhu226
 */
public class Message {

    private byte[] mess;

    public Message(String in) {
        this.mess = in.getBytes();
        this.mess=padMessage(this.mess);
    }

    public byte[] getMess() {
        return mess;
    }

    private byte[] padMessage(byte[] message) {
        int originalLength = message.length;
        int paddingLength = (56 - (originalLength + 1) % 64 + 64) % 64;
        byte[] paddedMessage = new byte[originalLength + 1 + paddingLength + 8];

        System.arraycopy(message, 0, paddedMessage, 0, originalLength);

        // Append '1' bit (in byte, 0x80)
        paddedMessage[originalLength] = (byte) 0x80;

        // Append original length in bits (big-endian 64-bit integer)
        long lengthInBits = (long) originalLength * 8;
        for (int i = 0; i < 8; i++) {
            paddedMessage[paddedMessage.length - 1 - i] = (byte) (lengthInBits >>> (8 * i));
        }

        return paddedMessage;
    }
}
