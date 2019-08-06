package cxp.xuexi.nettyUploadFile.randomAcessFile;

import java.io.*;

/**
 * Created by ASUS on 2018/3/15.
 */

public class TestRandomFile {

    public static void main(String[] args) throws IOException {
        String filePath = "D:\\BaiduNetdiskDownload\\randomAccessFile.txt";
        insert(filePath,100,"插入指定位置指定内容");
    }

    /**
     * 插入文件指定位置的指定内容
     * @param filePath 文件路径
     * @param pos  插入文件的指定位置
     * @param insertContent 插入文件中的内容
     * @throws IOException
     */
    public static void insert(String filePath,long pos,String insertContent)throws IOException{
        RandomAccessFile raf=null;
        File tmp=File.createTempFile("tmp",null);
        tmp.deleteOnExit();
        try {
            // 以读写的方式打开一个RandomAccessFile对象
            raf = new RandomAccessFile(new File(filePath), "rw");
            //创建一个临时文件来保存插入点后的数据
            FileOutputStream fileOutputStream = new FileOutputStream(tmp);
            FileInputStream fileInputStream = new FileInputStream(tmp);
            //把文件记录指针定位到pos位置
            raf.seek(pos);
            //------下面代码将插入点后的内容读入临时文件中保存-----
            byte[] bbuf = new byte[64];
            //用于保存实际读取的字节数据
            int hasRead = 0;
            //使用循环读取插入点后的数据
            while ((hasRead = raf.read(bbuf)) != -1) {
                //将读取的内容写入临时文件
                fileOutputStream.write(bbuf, 0, hasRead);
            }
            //-----下面代码用于插入内容 -----
            //把文件记录指针重新定位到pos位置
            raf.seek(pos);
            //追加需要插入的内容
            raf.write(insertContent.getBytes());
            //追加临时文件中的内容
            while ((hasRead = fileInputStream.read(bbuf)) != -1) {
                //将读取的内容写入临时文件
                raf.write(bbuf, 0, hasRead);
            }
        }catch (IOException e){
            throw  e;
        }
    }
}
