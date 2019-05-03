package Sockets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;

public class WorkThread implements Runnable
{
    private final String EncodingName = "UTF-8";

    private Socket mSocket = null;
    private java.io.BufferedInputStream bis = null;
    java.io.BufferedOutputStream bos = null;


    public WorkThread(Socket ws)
    {
        mSocket = ws;
    }

    static final Logger logger = LoggerFactory.getLogger(WorkThread.class);

    @Override
    public void run()
    {
        try
        {
            bis = new java.io.BufferedInputStream(mSocket.getInputStream());
            bos = new java.io.BufferedOutputStream(mSocket.getOutputStream());

            String sData = ReceiveMessage();
            if (sData == null || sData.length() == 0)
            {
                logger.info("receive data error return to client");
                SendMessage("no data received...");
                return;
            }
            //try to do something here...

            SendMessage("do something ok!");
            return;
        }
        catch (Exception ex)
        {
            logger.error("work thread error", ex);
            return;
        }
        finally
        {
            try
            {
                if (mSocket != null)
                {
                    mSocket.close();
                    mSocket = null;
                }
            }
            catch (Exception e1)
            {

            }
        }
    }

    private String ReceiveMessage()
    {
        try
        {
            logger.debug("in receive message... try to read len");
            byte[] lenBuffer = new byte[10];
            if (ReadDataIntoBuffer(lenBuffer, 0, lenBuffer.length) == false)
            {
                logger.info("read length failed...");
                return "";
            }
            logger.info("read len success...");

            String strLen = new String(lenBuffer);
            strLen = strLen.trim();
            logger.debug("len = " + strLen);

            int iDataLength = Integer.parseInt(strLen);

            if (iDataLength == 0)
            {
                logger.info("workthread recv datalength is 0, return...");
                return "";
            }

            byte[] dataBuffer = new byte[iDataLength];
            if (ReadDataIntoBuffer(dataBuffer, 0, dataBuffer.length) == false)
            {
                logger.info("read data failed...");
                return "";
            }
            String strData = new String(dataBuffer, EncodingName);
            logger.info("read data success...");
            logger.debug(strData);
            return strData;

        }
        catch (Exception ex)
        {
            logger.error("ReceiveMessage failed...", ex);
            return "";
        }
    }

    private boolean SendMessage(String message)
    {
        try
        {
            byte[] buffer = message.getBytes(EncodingName);
            String sLength = String.format("%010d", buffer.length);
            byte[] lenBuffer = sLength.getBytes();
            logger.debug("send buffer length = " + sLength);

            if (SendDataFromBuffer(lenBuffer, 0, lenBuffer.length) == false)
            {
                logger.debug("send buffer length failed!");
                return false;
            }
            logger.debug("send buffer length succ");

            logger.debug("send buffer");
            if (SendDataFromBuffer(buffer, 0, buffer.length) == false)
            {
                logger.debug("send buffer data failed");
                return false;
            }
            logger.debug("send buffer data succ!");
            return true;
        }
        catch (Exception ex)
        {
            logger.error("send string message to client fialed ex", ex);
            return false;
        }
    }

    private boolean SendDataFromBuffer(byte[] buffer, int iOffset, int iLength)
    {

        try
        {
            bos.write(buffer, iOffset, iLength);
            bos.flush();
            return true;
        }
        catch (Exception ex)
        {
            logger.error("SendDataFromBuffer", ex);
            return false;
        }
        /*finally
        {
            try
            {
                if (os != null)
                {
                    os.close();
                    os = null;
                }
            }
            catch (Exception e1)
            {

            }
        }*/
    }

    private boolean ReadDataIntoBuffer(byte[] buffer, int iOffset, int iLength)
    {

        try
        {


            //bReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));

            int iTotalReadLen = 0;
            int iErrorCount = 0;
            while (iTotalReadLen < iLength && iErrorCount < 3)
            {
                int iLen = bis.read(buffer, iOffset, iLength - iTotalReadLen);
                iTotalReadLen += iLen;
                iOffset += iLen;

                if (iLen == 0)
                {
                    iErrorCount++;
                }
            }

            if (iTotalReadLen == iLength)
            {
                return true;
            }
            return false;
        }
        catch (Exception ex)
        {
            logger.error("ReadDataIntoBuffer ex", ex);
            return false;
        }
        /*finally
        {
            try
            {
                if (bis != null)
                {
                    bis.close();
                    bis = null;
                }
            }
            catch (Exception e1)
            {

            }
        }*/
    }

}
