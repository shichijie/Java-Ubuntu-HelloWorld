package Common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.io.FileReader;
import java.io.BufferedReader;

public class ConfigHelper
{
    public static String AMS_IP = "";
    public static int LISTENER_PORT = 0;
    static final Logger logger = LoggerFactory.getLogger(ConfigHelper.class);

    public static void InitConfig() throws Exception
    {
        try
        {
            logger.info("init config...");

            Properties properties = new Properties();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("config/app.properties"));
            //InputStream in = PropertiesMain.class.getClassLoader().getResourceAsStream("config/app.properties");
            properties.load(bufferedReader);
            bufferedReader.close();
            logger.info("read config ok!");
            AMS_IP = properties.getProperty("ams.ip");
            logger.info("ams ip = " + AMS_IP);
            String strTempData = properties.getProperty("server.listener.port");
            LISTENER_PORT = Integer.parseInt(strTempData);
            logger.info("listener port = " + LISTENER_PORT);
        }
        catch (Exception ex)
        {
            logger.error("init config failed", ex);
            throw ex;
        }
    }
}
