package Sockets;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.text.StyledEditorKit;

public class WorkSocketCollector
{
    private static ArrayList<Socket> mSocketList = new ArrayList<>();
    private static Lock myLock = new ReentrantLock();
    static final Logger logger = LoggerFactory.getLogger(WorkSocketCollector.class);
    private static Boolean blRunning = false;



    public static void StartCollector()
    {
        if (blRunning == false)
        {
            return;
        }
        try
        {
            blRunning = true;
            new Thread(
                    new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            try
                            {
                                while (blRunning)
                                {
                                    Thread.sleep(100);
                                    Socket mSocket = GetSocketFromList();
                                    if (mSocket == null)
                                    {
                                        continue;
                                    }
                                    try
                                    {
                                        if (mSocket.getInputStream() != null)
                                        {
                                            mSocket.getInputStream().close();
                                        }
                                        if (mSocket.getOutputStream() != null)
                                        {
                                            mSocket.getOutputStream().close();
                                        }
                                        mSocket.close();
                                    }
                                    catch (Exception e1)
                                    {
                                        PushSocketIntoList(mSocket);
                                    }
                                }
                            }
                            catch (Exception ex)
                            {
                                logger.error("error when init server socket...", ex);
                            }
                            finally
                            {
                            }
                        }
                    }
            ).start();
        }
        catch (Exception ex)
        {

        }
        finally
        {

        }
    }

    public static void StopCollector()
    {
        try
        {
            blRunning = false;
            Thread.sleep(100);
        }
        catch (Exception e1)
        {

        }
        finally
        {

        }
    }

    public static void PushSocketIntoList(Socket wSocket)
    {
        try
        {
            myLock.lock();
            {
                mSocketList.add(wSocket);
            }

        }
        finally
        {
            myLock.unlock();
        }


    }

    public static Socket GetSocketFromList()
    {
        try
        {

            myLock.lock();
            {
                if (mSocketList.size() == 0)
                {
                    return null;
                }
                Socket mSocket = mSocketList.get(0);
                mSocketList.remove(mSocket);
                return mSocket;
            }
        }
        catch (Exception ex)
        {
            return null;
        }
        finally
        {
            myLock.unlock();
        }
    }

    public static void RemoveSocketFromList(Socket wSocket)
    {
        try
        {
            myLock.lock();
            {
                mSocketList.remove(wSocket);
            }

        }
        finally
        {
            myLock.unlock();
        }
    }
}
