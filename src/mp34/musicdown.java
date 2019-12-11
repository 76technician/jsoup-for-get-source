package mp34;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/*
 * 此类库实现了搜索式下载网易云的免费歌曲
 */

public class musicdown {
	/*
	 * 网易的搜索接口为utf-8编码 但音乐的id号获取的路径上的为自动化的 而且被识别为爬虫
	 * 网易在线播放链接是：https://music.163.com/#/song?id=474567580

     *mp3下载链接替换成：http://music.163.com/song/media/outer/url?id=474567580.mp3
          2019年10月19日17:41:47
                调试成功
	 */
	public static void downmp3(String id,String name) throws Exception {
		String Path = "C:\\Users\\Administrator\\eclipse-workspace\\txtspiled\\build\\";
		   String DownUrl="http://music.163.com/song/media/outer/url?id=";
		   StringBuffer sb1=new StringBuffer();
		   sb1.append(DownUrl);
		   sb1.append(id);
		   sb1.append(".mp3");
		   DownUrl=sb1.toString();
		   /*
		    * 这里存在一个问题 如果直接用string+的话 路径直接错误 拼接用 sb为好
		    */
	        StringBuffer sb=new StringBuffer();
	       sb.append(Path);
	       sb.append(name);
	       sb.append(".mp3");
	        try {
	            // 获取songURL
	            URL url = new URL(DownUrl);
	            // 获得连接
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            // 设置10秒的相应时间
	            connection.setConnectTimeout(10 * 1000);
	            connection.setReadTimeout(6000);
	            
	            int code = connection.getResponseCode();

	                     if (code != HttpURLConnection.HTTP_OK) {

	                    throw new Exception("文件读取失败");

	                }        
	            
	            // 获得输入流
	                DataInputStream in = new DataInputStream(connection.getInputStream());

	                DataOutputStream out = new DataOutputStream(new FileOutputStream(sb.toString()));

	         byte[] buffer = new byte[2048];

	         int count = 0;

	           while ((count = in.read(buffer)) > 0) {

              out.write(buffer, 0, count);

          }

	        out.close();

	       in.close();
	       System.out.println("下载成功");
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	public static String find_name_id(String name) throws IOException {
		/*
		 * http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s=%E5%90%9E%E5%99%AC&type=1
		 * 其中的
		 */
		
        String newStr=java.net.URLEncoder.encode(name, "UTF-8");//这里写转换后的编码方式
		String url="http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s=name&type=1";
		url=url.replace("name", newStr);
		Connection conn =Jsoup.connect(url).validateTLSCertificates(false)
				.timeout(30000);
		//Connection connect = Jsoup.connect("https://sou.xanbhx.com/search?siteid=bxwxla&s=2222");				
		 conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");			
		 conn.header("Accept-Encoding", "gzip, deflate, sdch");			
		 conn.header("Accept-Language", "zh-CN,zh;q=0.8");			
		 conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
         Document doc=conn.get();
         String text=doc.text();

         int index=text.indexOf("id")+3;
         int outdex=text.indexOf(",");

		return text.substring(index+1,outdex);
	}
}


