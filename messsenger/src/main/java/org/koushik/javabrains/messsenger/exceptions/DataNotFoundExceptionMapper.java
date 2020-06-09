package org.koushik.javabrains.messsenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.koushik.javabrains.messsenger.model.ErrorMessage;

@Provider // it registers this class with jax-rs so that jax rs know that this class is
			// here and jax rscan use it
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage errorMessage = new ErrorMessage("data not found", "link for documentation", 404);
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();//here we are sending result in form of
//response object and we are binding here Status code and errorMessage class.
	}

}
