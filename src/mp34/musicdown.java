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
 * �����ʵ��������ʽ���������Ƶ���Ѹ���
 */

public class musicdown {
	/*
	 * ���׵������ӿ�Ϊutf-8���� �����ֵ�id�Ż�ȡ��·���ϵ�Ϊ�Զ����� ���ұ�ʶ��Ϊ����
	 * �������߲��������ǣ�https://music.163.com/#/song?id=474567580

     *mp3���������滻�ɣ�http://music.163.com/song/media/outer/url?id=474567580.mp3
          2019��10��19��17:41:47
                ���Գɹ�
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
		    * �������һ������ ���ֱ����string+�Ļ� ·��ֱ�Ӵ��� ƴ���� sbΪ��
		    */
	        StringBuffer sb=new StringBuffer();
	       sb.append(Path);
	       sb.append(name);
	       sb.append(".mp3");
	        try {
	            // ��ȡsongURL
	            URL url = new URL(DownUrl);
	            // �������
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            // ����10�����Ӧʱ��
	            connection.setConnectTimeout(10 * 1000);
	            connection.setReadTimeout(6000);
	            
	            int code = connection.getResponseCode();

	                     if (code != HttpURLConnection.HTTP_OK) {

	                    throw new Exception("�ļ���ȡʧ��");

	                }        
	            
	            // ���������
	                DataInputStream in = new DataInputStream(connection.getInputStream());

	                DataOutputStream out = new DataOutputStream(new FileOutputStream(sb.toString()));

	         byte[] buffer = new byte[2048];

	         int count = 0;

	           while ((count = in.read(buffer)) > 0) {

              out.write(buffer, 0, count);

          }

	        out.close();

	       in.close();
	       System.out.println("���سɹ�");
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	public static String find_name_id(String name) throws IOException {
		/*
		 * http://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&s=%E5%90%9E%E5%99%AC&type=1
		 * ���е�
		 */
		
        String newStr=java.net.URLEncoder.encode(name, "UTF-8");//����дת����ı��뷽ʽ
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


