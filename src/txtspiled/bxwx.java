package txtspiled;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class bxwx {
/*
 * time:2019��10��17��21:47:09 ���Գɹ� �ɹ� ������� �ҵ����� ͼ�� ���� �ҵ���һ�µĵ�ַ
 * �����ʽΪgbk1213
 */
	static String goalurl;
	public static String find_bookmate(String name) throws IOException {
		  
		//��ȡbxwx�������ӿ� ѡȡ��һ�����ҳ�� ��������ȥ
			
			
			byte[] temp=name.getBytes("utf-8");//����дԭ���뷽ʽ
            String newStr=new String(temp,"gbk");//����дת����ı��뷽ʽ
        //���뷽ʽ��ͬ �����������ڵ����ݿⱨ��
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
				//����鼮Ŀ¼���� ��ַ
				String nexturl=aim.attr("href").replace("http://www.biqugex.com", "").replace("/goto/id/", "_").trim();
				goalurl=nexturl;
				//����
				
				Connection conn2 =Jsoup.connect("http://www.biqugex.com"+nexturl).validateTLSCertificates(false)
						.timeout(30000);
				//��Ŀ¼�л�õ�һ�µĵ�ַ
				Element firsturl=conn2.get().select("body > div.listmain > dl > dd:nth-child(15) > a").first();
				String firsttxt=firsturl.attr("href");
				
				
				return firsttxt;
				}
	
}
