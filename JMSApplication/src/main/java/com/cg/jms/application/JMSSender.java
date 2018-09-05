package com.cg.jms.application;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSSender {
	public static void main(String[] args) {
		ConnectionFactory connectionFactory;
		Connection connection = null;

		try {
			InitialContext initialContext = new InitialContext();

			Queue queue = (Queue) initialContext.lookup("jms/P2PQueue");
			connectionFactory = (ConnectionFactory) initialContext.lookup("jms/__defaultConnectionFactory");

			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageProducer messageProducer = session.createProducer(queue);
			TextMessage textMessage = session.createTextMessage(args[0]);
			messageProducer.send(textMessage);
			System.out.println("Message Produced");
			
			
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
