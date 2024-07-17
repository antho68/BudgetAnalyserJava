package org.aba;

import org.aba.controller.AnalyserController;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!");

        try
        {
            //String path = "/Users/aba/Downloads/";
            String path = "C:\\temp\\";

            AnalyserController controller = new AnalyserController(path);
            controller.doWork();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}