package org.aba;

import org.aba.controller.AnalyserController;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");

        try
        {
            String os = System.getProperty("os.name");
            String path = "";

            if (os.contains("Windows"))
            {
                path = "C:\\temp\\";
            }
            else
            {
                path = "/Users/aba/Downloads/";
            }

            AnalyserController controller = new AnalyserController(path);
            controller.doWork();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}