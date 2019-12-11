package txtspiled;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
//实现了从指定第二域名下载 指定小说
//域名为http://www.xbiquge.la/的搜索
//随机等待时间 忽略各种网络错误后成功
//2019年10月20日23:38:26 调试成功
public class text {
	 static int i=0;
	 static String basePath="C:\\Users\\Administrator\\eclipse-workspace\\txtspiled\\build\\";
	 static String useagent="Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
	 public  static String Find_Txt_BQG(String secondurl,String name) throws InterruptedException, IOException{

        @SuppressWarnings("unused")
		String Url="https://www.biqugex.com"+secondurl.trim();
       
       
		try {
			Connection conn =Jsoup.connect(Url).validateTLSCertificates(false).ignoreHttpErrors(true)
					.timeout(240000);
			//Connection connect = Jsoup.connect("https://sou.xanbhx.com/search?siteid=bxwxla&s=2222");				
			 conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");			
			 conn.header("Accept-Encoding", "gzip, deflate, sdch");			
			 conn.header("Accept-Language", "zh-CN,zh;q=0.8");			
			 conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			
            
             if(conn.execute().statusCode()!=200){
            	 Random random = new Random();//默认构造方法
            	 int j=random.nextInt(60);
   				 TimeUnit.SECONDS.sleep(j);
   				 Find_Txt_BQG(secondurl,name);
   			 }
             Document doc=conn.get();
             
			@SuppressWarnings("unused")
			String title = doc.title();
			
			if(conn.execute().statusCode()!=200){
           	 Random random = new Random();//默认构造方法
           	 int j=random.nextInt(60);
  				 TimeUnit.SECONDS.sleep(j);
  				 Find_Txt_BQG(secondurl,name);
  			 }
			//筛选出主题文本 
			//Elements tt=doc.getElementsContainingText("下一章");
			
			Element txt = doc.select("#content").first();
			String Text=txt.text().replaceAll(Jsoup.parse("&nbsp;&nbsp;").text()," ");
			Text=Text.replace(" ", "\r\n");
			
			//筛选出题目
			Element tit = doc.select("#wrapper > div.book.reader > div.content > h1").first();
			String title1 = tit.text();
			
			
			    
			
			//顶部的下一章url  #wrapper > div.book.reader > div.content > div.page_chapter > ul > li:nth-child(3) > a
			Element next=doc.select("#wrapper > div.book.reader > div.content > div.page_chapter > ul > li:nth-child(3) > a").first();
			String nexturl=next.attr("href");
			
			//写入
			Write_Txt(title1,name);
			Write_Txt(Text,name);
			
			i++;
			System.out.println("第"+i+"章");
			
			return nexturl;
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
       }
	 public static void Write_Txt(String text,String name) throws IOException {
		   String filePath=basePath+name+".txt";
	        RandomAccessFile raf=null;
	        File file=null;
	        try {
	            file=new File(filePath);
	            // 以读写的方式打开一个RandomAccessFile对象
	            raf=new RandomAccessFile(file,"rw");
	            //将记录指针移动到该文件的最后
	            raf.seek(raf.length());
	            //向文件末尾追加内容
	            raf.write((text+"\r\n").getBytes());
	        }catch (IOException e){
	            e.printStackTrace();
	        }finally {
	            raf.close();
	        }

	 }
   }

