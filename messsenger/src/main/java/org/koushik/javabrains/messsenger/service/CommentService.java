package org.koushik.javabrains.messsenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.koushik.javabrains.messsenger.database.DatabaseClass;
import org.koushik.javabrains.messsenger.model.Comment;
import org.koushik.javabrains.messsenger.model.ErrorMessage;
import org.koushik.javabrains.messsenger.model.Message;

public class CommentService {
	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public List<Comment> getAllComments(long messageId) {
		System.out.println("into getAllcomments method");
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}

	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("data not found", "link for documentation", 404);
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Message message = messages.get(messageId);
		if (message == null) {
			throw new WebApplicationException(response);
		}

		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if (comment == null) {
			throw new WebApplicationException(response);// this is a way of throwing exception in jersy, this is
			// jesry`s inbuild feature we can use this class for throwing an exception.
		}
		return comment;
	}

	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		if (comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}

	public Comment removeComment(long messageId, long commentId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
	/*
	 * public List<Message> getAllMessagesForYear(int year) { List<Message>
	 * messagesForYear = new ArrayList<Message>(); Calendar cal =
	 * Calendar.getInstance(); for (Message message : messages.values()) {
	 * cal.setTime(message.getCreated()); if (cal.get(Calendar.YEAR) == year) {
	 * messagesForYear.add(message); } } return messagesForYear; }
	 * 
	 * public List<Message> getAllMessagesPaginated(int start, int size) {
	 * ArrayList<Message> list = new ArrayList<Message>(messages.values()); if
	 * (start + size > list.size()) { return new ArrayList<Message>(); } return
	 * list.subList(start, start + size); }
	 */
}
