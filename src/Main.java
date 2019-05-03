import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Http.HttpHelper;


public class Main
{


    public static void main(String[] args)
    {
        
        System.out.println("Hello World!");


        //logger.info("sss");

        //HttpHelper.GetHttpResponse();
        Sockets.MyServer server1 = new Sockets.MyServer();
        server1.StartListener();
    }

}
