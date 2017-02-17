package org.devict;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimpleMovieDbApplication
{

   public static void main(String[] args)
   {
      SLF4JBridgeHandler.install();
      
      SpringApplication.run(SimpleMovieDbApplication.class, args);
   }
}
