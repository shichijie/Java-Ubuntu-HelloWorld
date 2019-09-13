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
import Common.AccountInfoStruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import Common.ConfigHelper;

public class HttpHelper
{
    static final Logger logger = LoggerFactory.getLogger("httpHelper");
    //static final String AMS_IP = "66.0.249.1";

    public HttpHelper()
    {

    }

    //久悬户查找
    public static void GetHttpResponse_JXH(AccountInfoStruct accountInstance)
    {
        //String s = "";
        CloseableHttpClient client1 = null;
        try
        {
            logger.info("GetHttpResponse_JXH entered...");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String strCurrentDate = sdf.format(new Date());

            client1 = org.apache.http.impl.client.HttpClients.createDefault();
            String strLoginURL = String.format("http://%s/ams/security.do?method=login&txcode=login&loginInfo.slogincode=%s&loginInfo.spassword=%s&clientDate=%s", ConfigHelper.AMS_IP, accountInstance.strLoginName, accountInstance.strLoginPassword, strCurrentDate);
            logger.info("loginURL = " + strLoginURL);

            HttpGet myGetStep1 = new HttpGet(strLoginURL);
            //HttpGet myGetStep1 = new HttpGet("http://66.0.249.1/ams/security.do?method=login&txcode=login&loginInfo.slogincode=23XQ72&loginInfo.spassword=Aa111111&clientDate=20190821");
            CloseableHttpResponse responseStep1 = client1.execute(myGetStep1);
            HttpEntity entity = responseStep1.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            responseStep1.close();
            //logger.info(result);
            if (result.contains("在线人数") != true)
            {
                logger.info("login failed...");
                logger.info(result);
                accountInstance.strRetCode = "9995";
                accountInstance.strRetInfo = "login failed.";
                return;
            }

            logger.info(("login success!"));


            SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-dd");
            String strTempCurrentDate = sdfTemp.format(new Date());
            String strTempAccount = "1";
            //strTempCurrentDate = "2019-08-21";

            String strQueryURL = String.format("http://%s/ams/recordcommon.do?method=forInputBasicImportantInfo&accEntAccountInfo.saccbaselicno=%s&accEntAccountInfo.saccbankcode=%s&accEntAccountInfo.saccno=%s&accEntAccountInfo.daccbegindate=%s", ConfigHelper.AMS_IP, accountInstance.strEnterpriseCert, accountInstance.strEnterpriseAreaCode, strTempAccount, strTempCurrentDate);
            logger.info("query URL = " + strQueryURL);
            //HttpGet myGetStep3 = new HttpGet("http://66.0.249.1/ams/recordcommon.do?method=forInputBasicImportantInfo&accEntAccountInfo.saccbaselicno=J3128007742501&accEntAccountInfo.saccbankcode=314312801010&accEntAccountInfo.saccno=1&accEntAccountInfo.daccbegindate=2019-08-21");
            HttpGet myGetStep3 = new HttpGet(strQueryURL);

            CloseableHttpResponse responseStep3 = client1.execute(myGetStep3);
            entity = responseStep3.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            responseStep3.close();
            logger.info(result);
            accountInstance.strRetCode = "0000";

            if (result.contains("校验信息") == true)
            {
                int iStartIndex = result.indexOf("校验信息");
                result = result.substring(iStartIndex);
                result = result.replaceAll("<br>", "<BR>");
                int iEndIndex = result.indexOf("<BR>");
                if (iEndIndex > 0)
                {
                    String strRetData = result.substring(0, iEndIndex - 1);
                    logger.info("RET DATA = " + strRetData);
                    accountInstance.strRetInfo = strRetData;
                }
            }
            //logger.info(result);
            //System.out.println(result);
        }
        catch (Exception e)
        {
            logger.error("error in HTTP get JXH", e);
            accountInstance.strRetCode = "9996";
            accountInstance.strRetInfo = "deal HTTP error";
        }
        finally
        {
            if (client1 != null)
            {
                try
                {
                    client1.close();
                }
                catch (Exception e1)
                {
                }
                client1 = null;
            }
        }
    }

    //基本户唯一性
    public static void GetHttpResponse_JBH(AccountInfoStruct accountInstance)
    {
        //String s = "";
        CloseableHttpClient client1 = null;
        try
        {
            logger.info("GetHttpResponse_JXH entered...");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String strCurrentDate = sdf.format(new Date());

            client1 = org.apache.http.impl.client.HttpClients.createDefault();

            String strLoginURL = String.format("http://%s/ams/security.do?method=login&txcode=login&loginInfo.slogincode=%s&loginInfo.spassword=%s&clientDate=%s", ConfigHelper.AMS_IP, accountInstance.strLoginName, accountInstance.strLoginPassword, strCurrentDate);
            logger.info("loginURL = " + strLoginURL);
            HttpGet myGetStep1 = new HttpGet(strLoginURL);
            //HttpGet myGetStep1 = new HttpGet("http://66.0.249.1/ams/security.do?method=login&txcode=login&loginInfo.slogincode=4184T5&loginInfo.spassword=Lr456789&clientDate=20190821");
            CloseableHttpResponse responseStep1 = client1.execute(myGetStep1);
            HttpEntity entity = responseStep1.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            responseStep1.close();
            //logger.info(result);
            if (result.contains("在线人数") != true)
            {
                logger.info("login failed...");
                logger.info(result);
                accountInstance.strRetCode = "9995";
                accountInstance.strRetInfo = "login failed.";
                return;
            }

            logger.info(("login success!"));

            SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy-MM-dd");
            String strTempCurrentDate = sdfTemp.format(new Date());
            String strTempAccount = "1";
            //strTempCurrentDate = "2019-08-21";

            String strQueryURL = String.format("http://%s/ams/apprLocalBasicPilot.do?method=forInputBasicImportantInfo&accEntDepositorInfo.sdepkind=01&accEntAccountInfo.saccno=0&accEntDepositorInfo.sdepname=%s&accEntDepositorInfo.saccfilecode1=%s&accEntDepositorInfo.deffectdate=%s&accEntAccountInfo.saccbankcode=%s&accEntAccountInfo.daccbegindate=%s&areaStr=312800",ConfigHelper.AMS_IP, accountInstance.strEnterpriseName, accountInstance.strRegisterNo,strTempCurrentDate, accountInstance.strEnterpriseAreaCode, strTempCurrentDate, accountInstance.strLoginAreaCode);
            logger.info("query URL = " + strQueryURL);
            HttpGet myGetStep2 = new HttpGet(strQueryURL);
            //HttpGet myGetStep2 = new HttpGet("http://66.0.249.1/ams/apprLocalBasicPilot.do?method=forInputBasicImportantInfo&accEntDepositorInfo.sdepkind=01&accEntAccountInfo.saccno=0&accEntDepositorInfo.sdepname=惠山区长安嘀嘀饭店&accEntDepositorInfo.saccfilecode1=92320206MA1T6UJD75&accEntDepositorInfo.deffectdate=2019-08-21&accEntAccountInfo.saccbankcode=314312801010&accEntAccountInfo.daccbegindate=2019-08-21&areaStr=312800");
            CloseableHttpResponse responseStep2 = client1.execute(myGetStep2);
            entity = responseStep2.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
            responseStep2.close();
            logger.info(result);

            accountInstance.strRetCode = "0000";

            if (result.contains("校验信息") == true)
            {
                int iStartIndex = result.indexOf("校验信息");
                result = result.substring(iStartIndex);
                result = result.replaceAll("<br>", "<BR>");
                int iEndIndex = result.indexOf("<BR>");
                if (iEndIndex > 0)
                {
                    String strRetData = result.substring(0, iEndIndex - 1);
                    logger.info("RET DATA = " + strRetData);
                    accountInstance.strRetInfo = strRetData;
                }
            }
            //logger.info(result);
            //System.out.println(result);
        }
        catch (Exception e)
        {
            logger.error("error in HTTP get JBH", e);
            accountInstance.strRetCode = "9996";
            accountInstance.strRetInfo = "deal HTTP error";
        }
        finally
        {
            if (client1 != null)
            {
                try
                {
                    client1.close();
                }
                catch (Exception e1)
                {
                }
                client1 = null;
            }
        }
    }
}
