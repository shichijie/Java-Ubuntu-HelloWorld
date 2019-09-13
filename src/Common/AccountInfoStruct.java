package Common;

import Sockets.WorkThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountInfoStruct
{
    static final Logger logger = LoggerFactory.getLogger(WorkThread.class);

    public String strDetailFile = null;
    public String strLoginName = null;
    public String strLoginPassword = null;
    public String strLoginOrgan = null;
    public String strLoginAreaCode = null;

    public String strEnterpriseName = null;
    public String strEnterpriseCert = null;
    public String strEnterprisePassword = null;
    public String strEnterpriseAreaCode = null;

    public String strAccountNo = null;

    public String strRegisterNo = null;


    public String strTransCode = null;
    public Boolean blDealFinished = false;

    public String strRetCode = null;
    public String strRetInfo = null;


    public AccountInfoStruct()
    {

    }

    public static String GetRetMessage(String strTransCode, String strRetCode, String strRetInfo)
    {
        return String.format("%s%s%s", PadLeft(strTransCode, 8), strRetCode, strRetInfo);
    }

    public static String GetRetMessage(AccountInfoStruct accountInstance)
    {
        if (accountInstance != null)
        {
            return GetRetMessage(accountInstance.strTransCode,  accountInstance.strRetCode,  accountInstance.strRetInfo);
        }
        return "";
    }

    public static String PadLeft(String strData, int iLength)
    {
        String strFormat = "%1$-" + iLength + "s";
        return String.format( strFormat, strData);
    }
    public static AccountInfoStruct BuildObject(String strMessage)
    {
        try
        {
            logger.info("in Build Object");
            if (strMessage == null)
            {
                logger.info("input param data is null");
                return null;
            }
            if (strMessage.length() != 267)
            {
                logger.info("input param data length is " + strMessage.length());
                return null;
            }

            AccountInfoStruct instance = new AccountInfoStruct();
            int iStartIndex = 0;
            int iLength = 8;
            instance.strTransCode = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("transCode = " + instance.strTransCode);

            iStartIndex = iStartIndex + iLength;
            iLength = 10;
            instance.strLoginName = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("loginName = " + instance.strLoginName);

            iStartIndex = iStartIndex + iLength;
            iLength = 10;
            instance.strLoginPassword = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("loginPassword = " + instance.strLoginPassword);

            iStartIndex = iStartIndex + iLength;
            iLength = 9;
            instance.strLoginOrgan = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("loginOrgan = " + instance.strLoginOrgan);

            iStartIndex = iStartIndex + iLength;
            iLength = 12;
            instance.strLoginAreaCode = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("loginAreaCode = " + instance.strLoginAreaCode);

            iStartIndex = iStartIndex + iLength;
            iLength = 20;
            instance.strEnterpriseCert = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("enterpriseCert = " + instance.strEnterpriseCert);

            iStartIndex = iStartIndex + iLength;
            iLength = 100;
            instance.strEnterpriseName = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("enterpriseName = " + instance.strEnterpriseName);

            iStartIndex = iStartIndex + iLength;
            iLength = 12;
            instance.strEnterpriseAreaCode = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("enterpriseAreaCode = " + instance.strEnterpriseAreaCode);

            iStartIndex = iStartIndex + iLength;
            iLength = 6;
            instance.strEnterprisePassword = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("enterprisePassword = " + instance.strEnterprisePassword);

            iStartIndex = iStartIndex + iLength;
            iLength = 30;
            instance.strAccountNo = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("accountNo = " + instance.strAccountNo);

            iStartIndex = iStartIndex + iLength;
            iLength = 50;
            instance.strRegisterNo = strMessage.substring(iStartIndex, iStartIndex + iLength).trim();
            logger.info("registerNo = " + instance.strRegisterNo);

            instance.blDealFinished = false;

            logger.info("account struct completed!");
            return instance;
        }
        catch (Exception ex)
        {
            logger.error("error when building account struct ", ex);
            return null;
        }
    }
}
