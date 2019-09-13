import Common.ConfigHelper;
import Common.MyServer;
import Common.AccountInfoStruct;
import Http.HttpHelper;
import org.apache.commons.codec.binary.StringUtils;

import java.io.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main
{

    static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args)
    {
        try
        {
            //String strRetMessage = AccountInfoStruct.GetRetMessage("aaa", "9991", "err");
            //logger.info(strRetMessage);

            logger.info("try to start main v1.1...");
            ConfigHelper.InitConfig();
            logger.info("init config successfully");
            MyServer server1 = new MyServer();
            server1.StartListener();
            logger.info("start listener successfully");
        }
        catch (Exception ex)
        {
            logger.error("main ex", ex);
        }
    }

}
