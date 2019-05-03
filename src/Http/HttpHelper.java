package Http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpHelper
{
    static final Logger logger = LoggerFactory.getLogger("");

    public HttpHelper()
    {

    }

    public static void GetHttpResponse()
    {
        //String s = "";
        try
        {
            CloseableHttpClient client1 = org.apache.http.impl.client.HttpClients.createDefault();
            HttpGet myGet = new HttpGet("http://www.ifeng.com");
            CloseableHttpResponse response = client1.execute(myGet);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            logger.info(result);
            //System.out.println(result);
        }
        catch (Exception e)
        {
        }

    }
}
