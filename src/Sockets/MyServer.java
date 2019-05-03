package Sockets;

import com.sun.xml.internal.ws.transport.http.server.EndpointImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Endpoint;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer
{

    private ServerSocket socket = null;
    private boolean blListening = false;

    static final Logger logger = LoggerFactory.getLogger(MyServer.class);

    public MyServer()
    {

    }

    public void StopListener()
    {
        blListening = false;
    }

    public void StartListener()
    {
        try
        {
            logger.debug("start listening...");

            socket = new ServerSocket(9995);
            blListening = true;
            new Thread(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while (blListening)
                                {
                                    logger.debug("waiting for connecting...");
                                    Socket workSocket = socket.accept();
                                    logger.debug("connected...");
                                    try
                                    {
                                        WorkThread wThread = new WorkThread(workSocket);
                                        wThread.run();
                                    }
                                    catch (Exception e2)
                                    {
                                        logger.error("error when init workThread", e2);
                                    }
                                }
                            }
                            catch (Exception ex)
                            {
                                logger.error("error when init server socket...", ex);
                            }
                            finally
                            {
                                try
                                {
                                    socket.close();
                                }
                                catch (Exception ex1)
                                {
                                    logger.error("close server soocket failed...", ex1);
                                }
                            }
                        }
                    }
            ).start();
        }
        catch (Exception e)
        {
            logger.error("StartListener failed...", e);
            //System.out.println("ex... " + e.getMessage());
        }
    }
}
