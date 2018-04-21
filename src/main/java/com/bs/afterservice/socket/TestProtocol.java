package com.bs.afterservice.socket;

import java.util.Random;

/**
 * Description: 指令协议
 * AUTHOR: Champion Dragon
 * created at 2018/4/20
 **/
public class TestProtocol {
    private static final byte cmdHeart = 2;
    private static final byte cmdGet = 102;
    private static final byte cmdSet = 103;
    public static final short cmdIP = 410;
    public static final short cmdID = 411;

    //心跳：0 请求：1 回应：2  事件：3  通知：4
    private static final byte typeHeart = 0;
    private static final byte typeReq = 1;

    private static final byte flag_heart = 1;
    private static final byte flag_req = 2;

    /**
     * 返回发送指令的字节数组包
     */
    public static byte[] getReqpacket(String str) {
        int paramLen = str.length();
        int packetLen = paramLen + 26;
        byte[] packet = new byte[packetLen];
        packet[0] = (byte) packetLen;// 整个包的长度:2个字节
        packet[1] = (byte) (packetLen >> 8);
        packet[2] = cmdSet;// 命令：2个字节
        packet[3] = cmdSet >> 8;
        packet[4] = typeReq;// 请求类型，2个字节
        byte[] termID = {'A', 'E', '0', '0', '6', '7', 'B', 'D', '1', '1'};
        System.arraycopy(termID, 0, packet, 6, termID.length);// id:12个字节
        int seq = new Random().nextInt(1000);// 设置随机数：4个字节
        packet[18] = (byte) (seq & 0xff);
        packet[19] = (byte) ((seq >> 8) & 0xff);
        packet[20] = (byte) ((seq >> 16) & 0xff);
        packet[21] = (byte) ((seq >> 24) & 0xff);

        packet[22] = flag_req;// 设置flag:4个字节
        System.arraycopy(str.getBytes(), 0, packet, 26, paramLen);// 添加param数据
        return packet;
    }

    /**
     * 返回"心跳"字节数组包
     */
    public static byte[] getHeartpacket(String str) {
        int paramLen = str.length();
        int packetLen = paramLen + 26;
        byte[] packet = new byte[packetLen];
        packet[0] = (byte) packetLen;
        packet[2] = cmdHeart;
        packet[4] = typeHeart;
        byte[] termID = {'A', 'E', '0', '0', '6', '7', 'B', 'D', '1', '1'};
        System.arraycopy(termID, 0, packet, 6, termID.length);
        byte[] seq = "TEST".getBytes();
        // new byte[4];
        // for (int i = 0; i < seq.length; i++) {
        // seq[i] = (byte) new Random().nextInt(127);
        // }
        System.arraycopy(seq, 0, packet, 18, seq.length);
        packet[22] = flag_heart;
        System.arraycopy(str.getBytes(), 0, packet, 26, paramLen);// 添加param数据
        return packet;
    }


    /**
     * 查询设备的动态ip
     */
    public static byte[] getByCmd(String str, String ID, int cmd) {
        int paramLen = str.length();
        int packetLen = paramLen + 36;
        byte[] packet = new byte[packetLen];
        short len = (short) packetLen;
        packet[0] = (byte) (len & 0xff);
        packet[1] = (byte) ((len >> 8) & 0xff);
        packet[2] = (byte) (cmd & 0xff);
        packet[3] = (byte) ((cmd >> 8) & 0xff);
        packet[4] = (byte) (typeReq & 0xff);
        packet[5] = (byte) (typeReq >> 8);
        //设置要查询的ID值
        byte[] IDBytes = ID.getBytes();
        System.arraycopy(IDBytes, 0, packet, 6, IDBytes.length);
        int seq = new Random().nextInt(1000);// 设置随机数：4个字节
        packet[18] = (byte) (seq & 0xff);
        packet[19] = (byte) ((seq >> 8) & 0xff);
        packet[20] = (byte) ((seq >> 16) & 0xff);
        packet[21] = (byte) ((seq >> 24) & 0xff);
        int FLAG_MOBILE = 1234;
        packet[22] = (byte) (FLAG_MOBILE & 0xff);
        packet[23] = (byte) ((FLAG_MOBILE >> 8) & 0xff);
        packet[24] = (byte) ((FLAG_MOBILE >> 16) & 0xff);
        packet[25] = (byte) ((FLAG_MOBILE >> 24) & 0xff);
        System.arraycopy(IDBytes, 0, packet, 26, IDBytes.length);
        System.arraycopy(str.getBytes(), 0, packet, 36, paramLen);// 添加param数据
        return packet;
    }
    /**
     * 查询设备的状态
     */
    public static byte[] getState(String str, String ID) {
        int paramLen = str.length();
        int packetLen = paramLen + 36;
        byte[] packet = new byte[packetLen];
        short len = (short) packetLen;
        packet[0] = (byte) (len & 0xff);
        packet[1] = (byte) ((len >> 8) & 0xff);
        packet[2] = (byte) (cmdGet & 0xff);
        packet[3] = (byte) ((cmdGet >> 8) & 0xff);
        packet[4] = (byte) (typeReq & 0xff);
        packet[5] = (byte) (typeReq >> 8);
        //设置要查询的ID值
        byte[] IDBytes = ID.getBytes();
        System.arraycopy(IDBytes, 0, packet, 6, IDBytes.length);
        int seq = new Random().nextInt(1000);// 设置随机数：4个字节
        packet[18] = (byte) (seq & 0xff);
        packet[19] = (byte) ((seq >> 8) & 0xff);
        packet[20] = (byte) ((seq >> 16) & 0xff);
        packet[21] = (byte) ((seq >> 24) & 0xff);
        int FLAG_MOBILE = 1234;
        packet[22] = (byte) (FLAG_MOBILE & 0xff);
        packet[23] = (byte) ((FLAG_MOBILE >> 8) & 0xff);
        packet[24] = (byte) ((FLAG_MOBILE >> 16) & 0xff);
        packet[25] = (byte) ((FLAG_MOBILE >> 24) & 0xff);
        System.arraycopy(IDBytes, 0, packet, 26, IDBytes.length);
        System.arraycopy(str.getBytes(), 0, packet, 36, paramLen);// 添加param数据
        return packet;
    }

    /**
     * 向指定id发送指令
     */
    public static byte[] getReqpacket(String str, String ID) {
        int paramLen = str.length();
        int packetLen = paramLen + 36;
        byte[] packet = new byte[packetLen];
        short len = (short) packetLen;
        packet[0] = (byte) (len & 0xff);
        packet[1] = (byte) ((len >> 8) & 0xff);
        packet[2] = (byte) (cmdSet & 0xff);
        packet[3] = (byte) ((cmdSet >> 8) & 0xff);
        packet[4] = (byte) (typeReq & 0xff);
        packet[5] = (byte) (typeReq >> 8);
        //设置要查询的ID值
        byte[] IDBytes = ID.getBytes();
        System.arraycopy(IDBytes, 0, packet, 6, IDBytes.length);
        int seq = new Random().nextInt(1000);// 设置随机数：4个字节
        packet[18] = (byte) (seq & 0xff);
        packet[19] = (byte) ((seq >> 8) & 0xff);
        packet[20] = (byte) ((seq >> 16) & 0xff);
        packet[21] = (byte) ((seq >> 24) & 0xff);
        packet[22] = 49;
        packet[23] = 50;
        packet[24] = 49;
        packet[25] = 52;
        System.arraycopy(IDBytes, 0, packet, 26, IDBytes.length);
        System.arraycopy(str.getBytes(), 0, packet, 36, paramLen);// 添加param数据
        return packet;
    }
}
