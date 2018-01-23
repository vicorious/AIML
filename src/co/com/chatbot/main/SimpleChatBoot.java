package co.com.chatbot.main;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;

import co.com.chatbot.aimltools.AgregarAIML;

/**
 * 
 * @author Alejandro Lindarte Castro
 *
 */
public class SimpleChatBoot
{
    private static final boolean TRACE_MODE = false;
    static String botName = "super";
    private static final String NO_RESPUESTA 		 = "I have no answer for that.";
    private static final String NO_RESPUESTA_ESPANOL = "No tengo respuesta para esto";
    private static final String AGREGAR				 = "AGREGAR";
 
    /**
     * Main
     * @param args
     */    
	public synchronized static void main(String[] args) 
    {
		String pregunta = "hola";
		
		String resultado = hablarConBot(new String[]{pregunta});
		
		System.out.println(resultado);
        
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
    
    /**
     * 
     * @param args
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static String hablarConBot(String[] args)
    {
    	String textLine = "";
    	String response = "";
    	
    	if(args != null)
    	{
    		try
    		{
    			if(args[0].equalsIgnoreCase(AGREGAR))
    			{
    				AgregarAIML.main(args);
    				return "Se agrego el archivo correctamente";
    			}else
    			{
    				textLine = args[0];
    			}
    		}catch(Exception ex)
    		{
    			//No vienen argumentos
    		}
    	}
    	
        try 
        {
 
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot("super", resourcesPath);            
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();            
 
            while(true) 
            {
                System.out.print("Persona : ");
                textLine = IOUtils.readInputTextLine();                
                if ((textLine == null) || (textLine.length() < 1))
                {
                    textLine = MagicStrings.null_input;
                }
                if (textLine.equals("q")) 
                {
                    System.exit(0);
                } else if (textLine.equals("wq")) 
                {
                    bot.writeQuit();
                    System.exit(0);
                } else 
                {
                    String request = textLine;
                    if (MagicBooleans.trace_mode)
                    {
                        System.out.println("STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0) + ":TOPIC=" + chatSession.predicates.get("topic"));
                    }
                    response = chatSession.multisentenceRespond(request);
                    while (response.contains("&lt;"))
                    {
                        response = response.replace("&lt;", "<");
                    }
                    while (response.contains("&gt;"))
                    {
                        response = response.replace("&gt;", ">");
                    }
                    
                    if(response.equalsIgnoreCase(NO_RESPUESTA))
                    {
                    	response = NO_RESPUESTA_ESPANOL;
                    }
                    
                    System.out.println("Robot : " + response);                   
                }
            }                             
                
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
		return response;
        
    }//hablarConBot

    
    
}//NoBorrar
