package org.koushik.javabrains.messsenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.koushik.javabrains.messsenger.database.DatabaseClass;
import org.koushik.javabrains.messsenger.exceptions.DataNotFoundException;
import org.koushik.javabrains.messsenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		super();
		messages.put(1L, new Message(1, "Hello world", "koushik"));
		messages.put(2L, new Message(2, "Hello jersey", "koushik"));
	}

	public List<Message> getAllMessages() {
		System.out.println("into getAllMessages method");
		return new ArrayList<Message>(messages.values());
	}

	public Message getMessage(Long id) {
		Message message = messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with " + id + " not found");
//as we have already defined a class with provider annotation so jersy will check and found that class is available for
//error handling so it will be handled by that class 
			
		}
		return messages.get(id);
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(Long id) {
		return messages.remove(id);
	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<Message>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if (start + size > list.size()) {
			return new ArrayList<Message>();
		}
		return list.subList(start, start + size);
	}
}
