package co.com.chatbot.aimltools;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.MagicBooleans;

/**
 * 
 * @author Alejandro Lindarte Castro
 *
 */
public abstract class AgregarAIML 
{
	 
    private static final boolean TRACE_MODE = false;
    static String botName = "super";
 
    /**
     * 
     * @param args
     */
    public static void main(String[] args) 
    {
        try 
        {
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);            
             
            bot.writeAIMLFiles();
 
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
        
    }//main
 
    /**
     * 
     * @return
     */
    private static String getResourcesPath() 
    {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path +  File.separator + "resources";
        return resourcesPath;
        
    }//getResourcesPath
    
}//NoBorrar


