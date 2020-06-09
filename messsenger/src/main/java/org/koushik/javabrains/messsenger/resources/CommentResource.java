package org.koushik.javabrains.messsenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.koushik.javabrains.messsenger.model.Comment;
import org.koushik.javabrains.messsenger.model.Message;
import org.koushik.javabrains.messsenger.resources.beans.MessageFilterBeans;
import org.koushik.javabrains.messsenger.service.CommentService;


//it is sub-resource from message resource
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService service = new CommentService();

	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return service.getAllComments(messageId);
	}

	@Path("/{commentId}")
	@GET
	public Comment getMessage(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId)
//it is sub-resource of message resource and in message resource messageId variable is available hence here we are 
//getting messageId variable as well.	
	{
		return service.getComment(messageId, commentId);
	}

	@POST
	public Comment addCommet(@PathParam("messageId") long messageId, Comment comment) {
		return service.addComment(messageId, comment);
	}

	@Path("/{commentId}")
	@PUT
	public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId,
			Comment comment) {
		comment.setId(commentId);
		return service.updateComment(messageId, comment);
	}

	@Path("/{commentId}")
	@DELETE
	public Comment deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		return service.removeComment(messageId, commentId);
	}
}
