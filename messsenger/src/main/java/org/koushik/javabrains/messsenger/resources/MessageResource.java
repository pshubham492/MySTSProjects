package org.koushik.javabrains.messsenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.koushik.javabrains.messsenger.model.Message;
import org.koushik.javabrains.messsenger.resources.beans.MessageFilterBeans;
import org.koushik.javabrains.messsenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON) // We are applying it at class level so all methods available in class will
										// consume
//and produce json.if any method need to consume or prouce any different type then we can override it by applying this
//annotations at method level.from client side we use headers  Accept for produces annotation and content-type for 
//consumes annotation
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService service = new MessageService();

	@GET
	@Produces(value = MediaType.APPLICATION_JSON)
	public List<Message> getJsonMessages(@BeanParam MessageFilterBeans filterBeans) // here we are using beanparam
	// annotation because here so many arguments are available for this methods so
	// we bind up all annotations in a single class
	// and then we are using object of that class here.so we no need to write so
	// many query param here in method argument.
	{
		System.out.println("json method callde");
		if (filterBeans.getYear() > 0) {
			return service.getAllMessagesForYear(filterBeans.getYear());
		}
		if (filterBeans.getStart() >= 0 && filterBeans.getSize() > 0) {
			return service.getAllMessagesPaginated(filterBeans.getStart(), filterBeans.getSize());
		}
		return service.getAllMessages();
	}

	@GET
	@Produces(value = MediaType.TEXT_XML) // here we are overriding class level produces annotation
	public List<Message> getXMLMessages(@BeanParam MessageFilterBeans filterBeans) {
		System.out.println("xml method callde");
		if (filterBeans.getYear() > 0) {
			return service.getAllMessagesForYear(filterBeans.getYear());
		}
		if (filterBeans.getStart() >= 0 && filterBeans.getSize() > 0) {
			return service.getAllMessagesPaginated(filterBeans.getStart(), filterBeans.getSize());
		}
		return service.getAllMessages();
	}

	@Path("/{messageId}")//if we need to match any method with variable of url then we will use variable name in curlee braceslike {}
	//basically {} matches with variable
	@GET
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo)//here we are catching 
//catching variable as @PathParam annotation.If we want any info regarding url by which we are calling this method then
// we can use @Context annotation like we are using here
	{
		Message message = service.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComment(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComment(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder().path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource").path(CommentResource.class)
				.resolveTemplate("messageId", message.getId()).build().toString();
		return url;
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build()
				.toString();
		return url;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId()))
				.build().toString();
		return url;
	}

	@POST
	public Response addMessage(@Context UriInfo uriInfo, Message message) {
		Message newMessage = service.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();// here we are using builder method bcz we need
// to add id with path and then again need to convert in URI object hence we are using builder conceept here
		return Response.created(uri).entity(newMessage).build();
	}

	@Path("/{messageId}")
	@PUT
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return service.updateMessage(message);
	}

	@Path("/{messageId}")
	@DELETE
	public Message deleteMessage(@PathParam("messageId") long messageId) {
		return service.removeMessage(messageId);
	}

	@Path("/{messageId}/comments") // this is subresource we are making a call for instance of subresource
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}