package org.koushik.javabrains.messsenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.koushik.javabrains.messsenger.model.ErrorMessage;

//@Provider // it registers this class with jax-rs so that jax rs know that this class is
// here and it can use it
public class GenericExceptionMapper implements ExceptionMapper<Throwable> // we have created this class and 
//using throwable here so if any exceptions will occur then our this class will handle that and we will get output 
//accprdin to this
{
	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage errorMessage = new ErrorMessage("data not found", "link for documentation", 404);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
	}

}
