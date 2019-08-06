package hadoop.xuexi.hdfs_api;

import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;

/**
 * Created by ASUS on 2018/5/6.
 */
public class TestHDFSClient {
    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        //这里指定使用的是hdfs文件系统
        //     conf.set("fs.defaultFS", "hdfs://chengxp133:9000");
        //通过这种方式设置java客户端访问hdfs的身份
        //     System.setProperty("HADOOP_USER_NAME","root");

        //      FileSystem fs = FileSystem.get(conf);
        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        //     fs.create(new Path("/helloByJava2"));
        fs.delete(new Path("/helloByJava"), true);
        fs.close();
    }

    @Test
    public void testDirectory() throws Exception {
        Configuration conf = new Configuration();
        //如果没有设置Hadoop的环境变量，可能通过下面代码设置(只在本方法中有效)
        System.setProperty("hadoop.home.dir", "E:\\application soft\\java\\Hadoop\\hadoop-2.7.6\\hadoop-2.7.6");

        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        //创建目录
        fs.mkdirs(new Path("/Hello1"));
        fs.mkdirs(new Path("/Hello2"));
        //删除目录
        fs.delete(new Path("/Hello2"), true);
        //重命名目录
        fs.rename(new Path("/Hello1"), new Path("/HelloHadoop"));
        //从本地上传文件到HDFS
        fs.copyFromLocalFile(new Path("D:\\dowload\\tri_1.jpg"), new Path("/HelloHadoop"));
        //从HDFS下载文件到本地
        fs.copyToLocalFile(false, new Path("/HelloHadoop/tri_1.jpg"),
                new Path("D:\\BaiduNetdiskDownload\\"), true);
        fs.close();
    }

    @Test
    public void testDownloadFile() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        //下载文件
        fs.copyToLocalFile(new Path("/helloByjava/zookeeper.out"),
                new Path("D://BaiduNetdiskDownload"));
        fs.close();
    }

    @Test
    public void listFiles() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        //RemoteIterator 远程迭代器
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);

        while (listFiles.hasNext()) {
            LocatedFileStatus file = listFiles.next();
            Path path = file.getPath();
            //String fileName=path.getName();
            System.out.println(path.toString());
            System.out.println("权限：" + file.getPermission());
            System.out.println("组：" + file.getGroup());
            System.out.println("文件大小：" + file.getBlockSize());
            System.out.println("所属者：" + file.getOwner());
            System.out.println("副本数：" + file.getReplication());

            BlockLocation[] blockLocations = file.getBlockLocations();
            for (BlockLocation bl : blockLocations) {
                System.out.println("块起始位置：" + bl.getOffset());
                System.out.println("块长度：" + bl.getLength());
                String[] hosts = bl.getHosts();
                for (String h : hosts) {
                    System.out.println("块所在DataNode：" + h);
                }

            }
            System.out.println("*****************************************");
        }
        System.out.println("-----------------华丽的分割线----------------");
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for (FileStatus status : listStatus) {
            String name = status.getPath().getName();
            System.out.println(name + (status.isDirectory() ? " is Dir" : " is File"));
        }
    }

    /**
     * 从HDFS上下载文件
     *
     * @throws Exception
     */
    @Test
    public void testDownloadStream() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        FSDataInputStream inputStream = fs.open(new Path("/helloByjava/zookeeper.out"));
        IOUtils.copy(inputStream, new FileOutputStream(new File("D:\\BaiduNetdiskDownload\\zookeeper.out")));
        fs.close();
    }

    /**
     * 上传文件到HDFS上
     *
     * @throws Exception
     */
    @Test
    public void testUploadStream() throws Exception {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        FSDataOutputStream outputStream = fs.create(new Path("/helloByjava/access.txt"), true);
        FileInputStream fis = new FileInputStream("D:\\BaiduNetdiskDownload\\randomAccessFile.txt");
        IOUtils.copy(fis, outputStream);
        fs.close();
    }

    /**
     * 读取文件内容
     *
     * @throws Exception
     */
    @Test
    public void testReadFile() throws Exception {
        Configuration conf = new Configuration();
        System.setProperty("hadoop.home.dir", "E:\\application soft\\java\\Hadoop\\hadoop-2.7.6\\hadoop-2.7.6");
        FileSystem fs = FileSystem.get(new URI("hdfs://chengxp133:9000"), conf, "root");
        FSDataInputStream inputStream = fs.open(new Path("/helloByjava/access.txt"));
        String str = IOUtils.toString(inputStream);
        System.out.println(str);
        fs.close();
    }

}
