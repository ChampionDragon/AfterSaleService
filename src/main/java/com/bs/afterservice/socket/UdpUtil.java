package com.bs.afterservice.socket;

import android.net.wifi.WifiManager;

import com.bs.afterservice.base.BaseApplication;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.utils.Logs;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Description:UDP发送指令的封装类
 * AUTHOR: Champion Dragon
 * created at 2018/4/20
 **/

public class UdpUtil {
    private static DatagramSocket dSocket;
    private static InetAddress inetAddress;//表示互联网协议（IP）地址
    private static WifiManager.MulticastLock lock = BaseApplication.lock;

    /**
     * @param ip 服务器ip地址
     * @param port  服务器端口号
     * @param data  发送数据的字节数组
     * @return  服务器返回的字符串
     */
    /*向服务器发送数据*/
    public static String Send(String ip, int port, byte[] data) {
        try {
            //在给定主机名的情况下确定主机的IP地址,如果参数为null,获得的是本机的IP地址
            inetAddress = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            Logs.i("udputil 25    " + e);
        }
        try {
            dSocket = new DatagramSocket();
            dSocket.setSoTimeout(10000);// 如果对方连接状态10秒没有收到数据的话强制断开客户端
        } catch (SocketException e) {
            e.printStackTrace();
            Logs.i("udputil 32    " + e);
        }
        // 创建一个DatagramPacket对象，用于发送数据。
        // 参数一：要发送的数据 参数二：数据的长度 参数三：服务端的网络地址 参数四：服务器端端口号
        DatagramPacket dPacket = new DatagramPacket(data, data.length, inetAddress, port);
        try {
            dSocket.send(dPacket);
            Logs.i(dPacket.getAddress() + "  udputil 45  " + dPacket.getPort());
//            Logs.i("udputil 47    " + Arrays.toString(dPacket.getData()));
        } catch (IOException e) {
            e.printStackTrace();
            Logs.i("udputil 50    " + e);
        }
        String s = Recevice();
        return s;
    }

    /*从服务器接收数据*/
    private static String Recevice() {
        String result = "NO RESPONSE";
        byte[] dataRec = new byte[66];
        DatagramPacket dPacket = new DatagramPacket(dataRec, dataRec.length);
        try {
            lock.acquire();
            dSocket.receive(dPacket);
            lock.release();
            result = new String(dPacket.getData());
//            Logs.i("udputil 66          " + result + "\n" + Arrays.toString(dPacket.getData()));
        } catch (IOException e) {
            e.printStackTrace();
            Logs.i("udputil 69    " + e);
        }
        dSocket.close();
        return result;
    }


    /**
     * 通过字节数组包的接口
     */
    public static String IpSend(byte[] data) {
        return Send(Constant.ipIP, Constant.ipPort, data);
    }

    /**
     * 通过字符串的接口
     */
    public static String ServerSend(byte[] data) {
        return Send(Constant.serverIP, Constant.serverPort, data);
    }
}
