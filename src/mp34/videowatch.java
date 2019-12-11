package mp34;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * 2019年10月20日21:53:49 接口调试成功 成功返回对于电影的网址
 * https://z1.m1907.cn/?jx=https://www.iqiyi.com/v_19rv44s864.html?vfm=m_303_qqll
 * 发现一个神奇的接口网站http://www.chplayer.com/examples/player.html?videourl=https://v.qq.com/x/cover/rjae621myqca41h/t00329rbass.html?ptag=qqbrowser+源地址   直接播放m3u8
 * 直接返回m3u8地址的url http://p.p40.top/api.php?url=https://v.qq.com/x/cover/rjae621myqca41h/t00329rbass.html?ptag=qqbrowser
 */
public class videowatch {
	public static void main(String[] arag) throws Exception {
		System.out.println(videowatch.find_m3u8("https://v.qq.com/x/cover/rjae621myqca41h/t00329rbass.html"));
		
	}
	public static String find_m3u8(String secondUrl) throws IOException{
		String Url="http://p.p40.top/api.php?url=";
		//String secondUrl="https://www.iqiyi.com/v_19rrhlbh27.html?vfrm=pcw_home&vfrmblk=L&vfrmrst=711219_home_dianying_float_pic_area2";
		StringBuffer sb=new StringBuffer();
		sb.append(Url);
		sb.append(secondUrl);
		
		Connection conn =Jsoup.connect(sb.toString()).validateTLSCertificates(false).ignoreContentType(true)
				.timeout(30000);
					
		 conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");			
		 conn.header("Accept-Encoding", "gzip, deflate, sdch");			
		 conn.header("Accept-Language", "zh-CN,zh;q=0.8");			
		 conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
         Document doc=conn.get();
         String txt=doc.text();
         int index=txt.indexOf("url");
         int outdex=txt.indexOf("type");
         String m3u8Url=txt.substring(index+6,outdex-3).replace("\\","").trim();
		  return m3u8Url;
		//String url = "http://www.baidu.com/1.mp4";//示例，实际填你的网络视频链接
//		String extension = MimeTypeMap.getFileExtensionFromUrl(url);
//		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
//		Intent mediaIntent = new Intent(Intent.ACTION_VIEW);
//		mediaIntent.setDataAndType(Uri.parse(url), mimeType);
//		startActivity(mediaIntent);
	}
}
