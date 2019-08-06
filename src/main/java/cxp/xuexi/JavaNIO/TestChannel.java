package cxp.xuexi.JavaNIO;

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by ASUS on 2018/4/3.
 */
public class TestChannel {

    public static void main(String[] args) throws Exception {
   //     transferFile("D:\\BaiduNetdiskDownload\\randomAccessFile.txt","D:\\BaiduNetdiskDownload\\wirteFile.txt");
             readFile();
    }

    public static void transferFile(String fromFile, String descFile) throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fisChannel = null;
        FileChannel fosChannel = null;
        try{
             fis = new FileInputStream(new File(fromFile));
             fos = new FileOutputStream(new File(descFile));
             fisChannel = fis.getChannel();
             fosChannel = fos.getChannel();

            fisChannel.transferTo(0, fisChannel.size(), fosChannel);
        }finally {
            try {
                if (fisChannel != null)
                    fisChannel.close();
                if (fosChannel != null)
                    fosChannel.close();
                if (fis != null)
                    fis.close();
                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private  static void readFile() {
        RandomAccessFile randomAccessFile = null;
        FileChannel channel=null;
        try {
            randomAccessFile = new RandomAccessFile("D:\\BaiduNetdiskDownload\\randomAccessFile.txt", "rw");
            channel = randomAccessFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(128);
//            CharBuffer charBuffer = CharBuffer.allocate(512);
            int read = channel.read(buf);

            Charset charset = Charset.forName("UTF-8");
//            CharsetDecoder decoder = charset.newDecoder();
            while (read != -1) {
                System.out.println("read = " + read);
                buf.flip();
/*                decoder.decode(buf, charBuffer, false);
                charBuffer.flip();
                while (charBuffer.hasRemaining()) {
                    System.out.println(charBuffer.get());
                }
                System.out.println();*/
                CharBuffer charBuffer = charset.decode(buf);
                System.out.println(charBuffer);
                buf.clear();
                charBuffer.clear();
                read = channel.read(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           try{
               channel.close();
           }catch (Exception e){

           }
        }

    }
}
