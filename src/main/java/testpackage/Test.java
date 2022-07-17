package testpackage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

public class Test {
	
	
	public static void main(String[] args) {
		System.out.println("Test222");
	}
	/**
     * 下載壓縮文件
     * @param serverUrl :位址
     * @param savePath ：保存路徑
     * @param zipSavePath ：壓縮文件保存路徑
     * @return ：下載結束
     * @throws Exception ：異常
     */
    public static String downloadZip(String serverUrl,String savePath,String zipSavePath) throws Exception{
        String result = "fail";
        File f = new File(savePath);
        if(!f.exists()){
            if (!f.mkdirs()) {
                throw new Exception("makdirs: '" + savePath + "'fail");
            }
        }
        //Sardine是WebDAV的工具包
        Sardine sardine = SardineFactory.begin("test","test");
        if(sardine.exists(serverUrl)){
            URL url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            int length = conn.getContentLength();
            conn.setConnectTimeout(3 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            InputStream is = sardine.get("");
            BufferedInputStream bis = new BufferedInputStream(is);
            FileOutputStream fos = new FileOutputStream(zipSavePath);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int len;
            byte[] bytes = new byte[length/5];
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            //清除缓存
            bos.flush();
            //关闭流
            fos.close();
            is.close();
            bis.close();
            bos.close();
            result = "success";
 
        }else {
             throw new Exception("can not find file");
        }
        return result;
    }
 
 
}
