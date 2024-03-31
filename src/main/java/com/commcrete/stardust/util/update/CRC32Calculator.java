package com.commcrete.stardust.util.update;

import java.nio.ByteBuffer;


public class CRC32Calculator {

    private static final long[] SW_CRC32_BY_BYTE_TABLE = {
            0x00000000l, 0x04C11DB7l, 0x09823B6El, 0x0D4326D9l,
            0x130476DCl, 0x17C56B6Bl, 0x1A864DB2l, 0x1E475005l,
            0x2608EDB8l, 0x22C9F00Fl, 0x2F8AD6D6l, 0x2B4BCB61l,
            0x350C9B64l, 0x31CD86D3l, 0x3C8EA00Al, 0x384FBDBDl,
            0x4C11DB70l, 0x48D0C6C7l, 0x4593E01El, 0x4152FDA9l,
            0x5F15ADACl, 0x5BD4B01Bl, 0x569796C2l, 0x52568B75l,
            0x6A1936C8l, 0x6ED82B7Fl, 0x639B0DA6l, 0x675A1011l,
            0x791D4014l, 0x7DDC5DA3l, 0x709F7B7Al, 0x745E66CDl,
            0x9823B6E0l, 0x9CE2AB57l, 0x91A18D8El, 0x95609039l,
            0x8B27C03Cl, 0x8FE6DD8Bl, 0x82A5FB52l, 0x8664E6E5l,
            0xBE2B5B58l, 0xBAEA46EFl, 0xB7A96036l, 0xB3687D81l,
            0xAD2F2D84l, 0xA9EE3033l, 0xA4AD16EAl, 0xA06C0B5Dl,
            0xD4326D90l, 0xD0F37027l, 0xDDB056FEl, 0xD9714B49l,
            0xC7361B4Cl, 0xC3F706FBl, 0xCEB42022l, 0xCA753D95l,
            0xF23A8028l, 0xF6FB9D9Fl, 0xFBB8BB46l, 0xFF79A6F1l,
            0xE13EF6F4l, 0xE5FFEB43l, 0xE8BCCD9Al, 0xEC7DD02Dl,
            0x34867077l, 0x30476DC0l, 0x3D044B19l, 0x39C556AEl,
            0x278206ABl, 0x23431B1Cl, 0x2E003DC5l, 0x2AC12072l,
            0x128E9DCFl, 0x164F8078l, 0x1B0CA6A1l, 0x1FCDBB16l,
            0x018AEB13l, 0x054BF6A4l, 0x0808D07Dl, 0x0CC9CDCAl,
            0x7897AB07l, 0x7C56B6B0l, 0x71159069l, 0x75D48DDEl,
            0x6B93DDDBl, 0x6F52C06Cl, 0x6211E6B5l, 0x66D0FB02l,
            0x5E9F46BFl, 0x5A5E5B08l, 0x571D7DD1l, 0x53DC6066l,
            0x4D9B3063l, 0x495A2DD4l, 0x44190B0Dl, 0x40D816BAl,
            0xACA5C697l, 0xA864DB20l, 0xA527FDF9l, 0xA1E6E04El,
            0xBFA1B04Bl, 0xBB60ADFCl, 0xB6238B25l, 0xB2E29692l,
            0x8AAD2B2Fl, 0x8E6C3698l, 0x832F1041l, 0x87EE0DF6l,
            0x99A95DF3l, 0x9D684044l, 0x902B669Dl, 0x94EA7B2Al,
            0xE0B41DE7l, 0xE4750050l, 0xE9362689l, 0xEDF73B3El,
            0xF3B06B3Bl, 0xF771768Cl, 0xFA325055l, 0xFEF34DE2l,
            0xC6BCF05Fl, 0xC27DEDE8l, 0xCF3ECB31l, 0xCBFFD686l,
            0xD5B88683l, 0xD1799B34l, 0xDC3ABDEDl, 0xD8FBA05Al,
            0x690CE0EEl, 0x6DCDFD59l, 0x608EDB80l, 0x644FC637l,
            0x7A089632l, 0x7EC98B85l, 0x738AAD5Cl, 0x774BB0EBl,
            0x4F040D56l, 0x4BC510E1l, 0x46863638l, 0x42472B8Fl,
            0x5C007B8Al, 0x58C1663Dl, 0x558240E4l, 0x51435D53l,
            0x251D3B9El, 0x21DC2629l, 0x2C9F00F0l, 0x285E1D47l,
            0x36194D42l, 0x32D850F5l, 0x3F9B762Cl, 0x3B5A6B9Bl,
            0x0315D626l, 0x07D4CB91l, 0x0A97ED48l, 0x0E56F0FFl,
            0x1011A0FAl, 0x14D0BD4Dl, 0x19939B94l, 0x1D528623l,
            0xF12F560El, 0xF5EE4BB9l, 0xF8AD6D60l, 0xFC6C70D7l,
            0xE22B20D2l, 0xE6EA3D65l, 0xEBA91BBCl, 0xEF68060Bl,
            0xD727BBB6l, 0xD3E6A601l, 0xDEA580D8l, 0xDA649D6Fl,
            0xC423CD6Al, 0xC0E2D0DDl, 0xCDA1F604l, 0xC960EBB3l,
            0xBD3E8D7El, 0xB9FF90C9l, 0xB4BCB610l, 0xB07DABA7l,
            0xAE3AFBA2l, 0xAAFBE615l, 0xA7B8C0CCl, 0xA379DD7Bl,
            0x9B3660C6l, 0x9FF77D71l, 0x92B45BA8l, 0x9675461Fl,
            0x8832161Al, 0x8CF30BADl, 0x81B02D74l, 0x857130C3l,
            0x5D8A9099l, 0x594B8D2El, 0x5408ABF7l, 0x50C9B640l,
            0x4E8EE645l, 0x4A4FFBF2l, 0x470CDD2Bl, 0x43CDC09Cl,
            0x7B827D21l, 0x7F436096l, 0x7200464Fl, 0x76C15BF8l,
            0x68860BFDl, 0x6C47164Al, 0x61043093l, 0x65C52D24l,
            0x119B4BE9l, 0x155A565El, 0x18197087l, 0x1CD86D30l,
            0x029F3D35l, 0x065E2082l, 0x0B1D065Bl, 0x0FDC1BECl,
            0x3793A651l, 0x3352BBE6l, 0x3E119D3Fl, 0x3AD08088l,
            0x2497D08Dl, 0x2056CD3Al, 0x2D15EBE3l, 0x29D4F654l,
            0xC5A92679l, 0xC1683BCEl, 0xCC2B1D17l, 0xC8EA00A0l,
            0xD6AD50A5l, 0xD26C4D12l, 0xDF2F6BCBl, 0xDBEE767Cl,
            0xE3A1CBC1l, 0xE760D676l, 0xEA23F0AFl, 0xEEE2ED18l,
            0xF0A5BD1Dl, 0xF464A0AAl, 0xF9278673l, 0xFDE69BC4l,
            0x89B8FD09l, 0x8D79E0BEl, 0x803AC667l, 0x84FBDBD0l,
            0x9ABC8BD5l, 0x9E7D9662l, 0x933EB0BBl, 0x97FFAD0Cl,
            0xAFB010B1l, 0xAB710D06l, 0xA6322BDFl, 0xA2F33668l,
            0xBCB4666Dl, 0xB8757BDAl, 0xB5365D03l, 0xB1F740B4l
    };

    public static long calculateSwCRCByByte(long crc32, byte[] pBuffer, long numOfBytes, boolean isLittleEndian) {
        long lastData = 0;
        long numOfDword = numOfBytes >> 2;
        long numOftailByte = numOfBytes & 3;
        int idx = 0;
        while (numOfDword > 0) {
            numOfDword--;
            long value;
            if (!isLittleEndian) {
                value = (long) ((pBuffer[idx] & 0xFF) | ((pBuffer[idx + 1] & 0xFF) << 8) | ((pBuffer[idx + 2] & 0xFF) << 16) | ((pBuffer[idx + 3] & 0xFF) << 24));
            } else {
                value = (long) ((pBuffer[idx + 3] & 0xFF) | ((pBuffer[idx + 2] & 0xFF) << 8) | ((pBuffer[idx + 1] & 0xFF) << 16) | ((pBuffer[idx] & 0xFF) << 24));
            }
            crc32 = crc32 ^ value;
            idx += 4;

            for (int i = 0; i < 4; ++i) {
                crc32 = ((crc32 << 8) & 0xFFFFFFFFl) ^ SW_CRC32_BY_BYTE_TABLE[(int) (crc32 >> 24) & 0xFF];
            }
        }
        switch ((int) numOftailByte) {
            case 0:
                return crc32;
            case 1:
                lastData = ((long) (pBuffer[idx] & 0xFF)) << 24;
                break;
            case 2:
                lastData = ((long) (pBuffer[idx] & 0xFF) | ((long) (pBuffer[idx + 1] & 0xFF) << 8)) << 16;
                break;
            case 3:
                lastData = ((long) (pBuffer[idx] & 0xFF) | ((long) (pBuffer[idx + 1] & 0xFF) << 8) | ((long) (pBuffer[idx + 2] & 0xFF) << 16)) << 8;
                break;
        }

        byte[] bytes = ByteBuffer.allocate(8).putLong(lastData).array();
        crc32 = calculateSwCRCByByte(crc32, bytes, 4, true);
        crc32 ^= 0xFFFFFFFF;

        return crc32;
    }

    public static void main() {
        long crc32 = calculateSwCRCByByte(0xFFFFFFFFl, new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08}, 8, true);
    }
}

// Example how to call this function:

