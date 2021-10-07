import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


public class sever {
    public static void main(String[] args) throws Exception  {
        //����http���������󶨱���8010�˿�
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8010), 10);
        //��������
        httpServer.createContext("/", new TestHttpHandler());
        //����������
        httpServer.start();
        System.out.println("Biao-Bai-Qaing started successful");
        System.out.println("lister:" + 8010);
    }
}

//��������
class TestHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String msg = inputStreamToString(exchange.getRequestBody());
        //ƴװ����ֵ
        String response = "func( arrr = [";
        if (msg != null & msg.trim() != "") {
            //д��data
            TestHttpHandler.writeFile(msg);  
        }else{
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            response = readFile(response) + " ])";
            //����data��ƴװ
            System.out.println("sending:" + response);
            os.write(response.getBytes("unicode"));
            os.close();

        }
        System.out.println("received:" + msg);
    }

    //inputStreamToString ���ƶ���
    private static String inputStreamToString(InputStream inputStream) {
		StringBuffer buffer = new StringBuffer();
		InputStreamReader inputStreamReader;
		try {
			inputStreamReader = new InputStreamReader(inputStream, "GB2312");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			// �ͷ���Դ
			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
        
    /**
    * ����TXT�ļ�
    */
    public static String readFile(String response) {
        System.out.println("reading file");
        String pathname = "data.txt";
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                // һ�ζ���һ������
                Pattern pattern = Pattern.compile("(?<=name=).*?(?=&message)");
                Pattern pattern0 = Pattern.compile("(?<=&message=).*");
                Matcher matcher = pattern.matcher(line);
                Matcher matcher0 = pattern0.matcher(line);
                while(matcher.find()) {
                    //System.out.println(matcher.group());
                    response += "['" +matcher.group() + "',";
                };
                while(matcher0.find()) {
                    //System.out.println(matcher0.group());
                    response += "'" +matcher0.group() + "'],";
                };
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * д��TXT�ļ�
     */
    public static void writeFile( String word) {
        FileWriter fwriter = null;
        try {
            String path = "data.txt";
            //BufferedWriter out = new BufferedWriter(
            fwriter = new FileWriter(path,true);
            fwriter.write(word+"\r\n");
            fwriter.flush();
            fwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
