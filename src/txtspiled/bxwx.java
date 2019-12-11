package txtspiled;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class bxwx {
/*
 * time:2019年10月17日21:47:09 调试成功 成功 从书库中 找到所需 图书 并且 找到第一章的地址
 * 编码格式为gbk1213
 */
	static String goalurl;
	public static String find_bookmate(String name) throws IOException {
		  
		//调取bxwx的搜索接口 选取第一个书的页面 并进入爬去
			
			
			byte[] temp=name.getBytes("utf-8");//这里写原编码方式
            String newStr=new String(temp,"gbk");//这里写转换后的编码方式
        //编码方式不同 导致中文所在的数据库报错
			Connection conn =Jsoup.connect("https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqugex.com&q="+newStr).validateTLSCertificates(false)
					.timeout(30000);
			//Connection connect = Jsoup.connect("https://sou.xanbhx.com/search?siteid=bxwxla&s=2222");				
			 conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");			
			 conn.header("Accept-Encoding", "gzip, deflate, sdch");			
			 conn.header("Accept-Language", "zh-CN,zh;q=0.8");			
			 conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
             Document doc=conn.get();
			
                
           //http://www.biqugex.com/book/goto/id/4070
           //https://www.biqugex.com/book_4070/
           //https://www.biqugex.com/book_4070/2482508.html

             
				Element aim=doc.select("#search-main > div.search-list > ul > li:nth-child(2) > span.s2 > a").first();
				//获得书籍目录所在 地址
				String nexturl=aim.attr("href").replace("http://www.biqugex.com", "").replace("/goto/id/", "_").trim();
				goalurl=nexturl;
				//返回
				
				Connection conn2 =Jsoup.connect("http://www.biqugex.com"+nexturl).validateTLSCertificates(false)
						.timeout(30000);
				//从目录中获得第一章的地址
				Element firsturl=conn2.get().select("body > div.listmain > dl > dd:nth-child(15) > a").first();
				String firsttxt=firsturl.attr("href");
				
				
				return firsttxt;
				}
	
}
