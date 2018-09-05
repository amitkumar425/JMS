package com.cg.jms.application;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSReceiver {
	
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;

		try {
			InitialContext initialContext = new InitialContext();

			Queue queue = (Queue) initialContext.lookup("jms/P2PQueue");
			connectionFactory = (ConnectionFactory) initialContext.lookup("jms/__defaultConnectionFactory");

			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
			
			MessageConsumer messageConsumer = session.createConsumer(queue);
			TextMessage textMessage = (TextMessage) messageConsumer.receive();
			String body = textMessage.getText();
			System.out.println(body);
			
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
		}
		
	}
	
}
