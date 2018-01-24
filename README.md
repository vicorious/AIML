# AIML
Bot en español con implementacion con AIML

## Que es AIML

Es una implementacion del lenguaje XML para la creacion de BOTS basados en el famoso proyecto A.L.I.C.E [link](https://es.wikipedia.org/wiki/Artificial_Linguistic_Internet_Computer_Entity)

## En que consiste este software?

* La idea del VICTORIA-BOT es un desarrollo de codigo abierto con interfaz linguistica para la ayuda del estudio ontologico en general.
* en primera instancia, solo podremos hablar con nuestro bot
* en segunda instancia, el va almacenar las preguntas mas frecuentes
* **en tercera instancia, va aprender de las mismas**
* **al final, la idea es que sea un sistema complejo de Inteligencia artificial con integraciones de algoritmos de Backpropagacion, para el auto aprendizaje o el mas conocido como _machine learning complejo_ **

*NOTA: las que estan en **negrita** estan en desarrollo en este momento*

Vamos a explicar como funciona el software:

## Introduccion al AIML

* Encontramos una introduccion bastante completa y muy fuertemente tipada en el siguiente **[Link](https://www.tutorialspoint.com/aiml)**

* Cuando te sientas en un buen nivel (No es tan complicado, enserio), sigamos a explicar para que nos sirven nuestros principales componentes.

## Clases principales

Existen 3 clases principales

* co.com.chatbot.aimtools.AgregarAIML.java
* co.com.chatbot.main.ChatBot.java
* co.com.chatbot.main.SimpleChatBot.java

### AgregarAIML.java

esta clase nos ayuda para incluir en nuestro CORE, las propociones que cambiemos, es decir, si cambiamos algun archivo **AIML**, debemos ejecutar la clase **AgregarAIML**

```
import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.MagicBooleans;

/**
 * Clase encargada de agregar al CORE los cambios linguisticos
 * @author Alejandro Lindarte Castro
 *
 */
public abstract class AgregarAIML 
{
	 
    private static final boolean TRACE_MODE = false;
    static String botName = "super";
 
    /**
     * Main, encargado de dar inicio a la aplicacion
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

```
### ChatBot.java

La clase ChatBoot te permite generar pruebas unitarias mediante JMeter

Para ver un ejemplo, puedes encontrarlo claramente en este **[Link](https://www.javacodegeeks.com/2012/05/apache-jmeter-load-test-whatever-you.html/comment-page-1/#comment-8288)**

Jmeter es un software encargado de realizar pruebas unitarias de carga y de concurrencia a tu chatbot, Genial!!

```
package co.com.chatbot.main;

import java.io.File;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import co.com.chatbot.aimltools.AgregarAIML;

/**
 * 
 * @author Alejandro Lindarte Castro
 *
 */
public class ChatBot extends AbstractJavaSamplerClient
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
        
    }//main
 
    /**
     * 
     * @return
     */
    private synchronized static String getResourcesPath() 
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
    public synchronized String hablarConBot(String[] args)
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
 
            //while(true) 
            //{
                //System.out.print("Persona : ");
                //textLine = IOUtils.readInputTextLine();                
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
                    
                    //System.out.println("Robot : " + response);                   
                }
           // } Descomentar cuando quieran que sea una prueba unitaria en consola (es necesario tener un ciclo bruto)                            
                
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
		return response;
        
    }//hablarConBot

    /**
     * Encargado de la prueba unitaria en Jmeter
     */
	@Override
	public synchronized SampleResult runTest(JavaSamplerContext arg0) 
	{
		 String pregunta = "hola";
		 
		 SampleResult result = new SampleResult();

         boolean success = true;

         result.sampleStart();
         
         System.out.println("Persona : " + pregunta); 
         
         String resultado = hablarConBot(new String[]{pregunta});
         
         System.out.println("Robot : " + resultado); 
         
         result.setResponseMessage(resultado);
         
         result.sampleEnd();

         result.setSuccessful(success);

         return result;
         
	}//runTest
    
}//NoBorrar


```

### SimpleChatBoot.java

Encargado de general pruebas directamente a tu chatbot (Chatear con el) desde consola de JAVA.

```

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


```
Y el ejemplo de como es la interfaz de habla:

```
Persona : hola
Robot : Hola amigo, en que puedo ayudarte
Persona : 

```
