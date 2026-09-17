package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Emissor {

  private static final String QUEUE_NAME = "Fila_SD";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("100.26.187.37");
    factory.setUsername("admin");
    factory.setPassword("password");
    factory.setVirtualHost("/");

    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
      // (queue-name, durable, exclusive, auto-delete, params)

      channel.exchangeDeclare("SD_DIRECT", "direct");
      channel.queueBind("Fila_SD_1", "SD_DIRECT", "A"); //Nome da fila vinculada, Nome do bindig? , Nome da chave
      channel.queueBind("Fila_SD_2", "SD_DIRECT", "B");
      channel.queueBind("Fila_SD_3", "SD_DIRECT", "A");

      String message = "Olá Receptores!!!";

      // A fila e a mensagem são persistentes.
      channel.basicPublish("SD", "B", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
      System.out.println(" [x] Mensagem enviada: '" + message + "'");
    }
  }
}
