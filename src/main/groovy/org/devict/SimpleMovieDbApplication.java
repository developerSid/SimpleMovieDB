package org.devict;

import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class SimpleMovieDbApplication
{

   public static void main(String[] args)
   {
      SLF4JBridgeHandler.install();
      
      SpringApplication.run(SimpleMovieDbApplication.class, args);
   }
}
