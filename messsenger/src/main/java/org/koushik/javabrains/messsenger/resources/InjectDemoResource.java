package org.koushik.javabrains.messsenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")///if we are using path annotation at class level then we need to use / symbol with value but when
//we are using path annotation at method level then we do not need to add /symbol with value.
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("annotations")//here we are not using / symbol in path annotation bcz it is at class level. but if we are
//using any variable with curlee braces then we need to add / symbol at method level also in path annotation.
	public String getParamsUsingAnnotations(@MatrixParam("matrixparam") String matrixparam,
			@HeaderParam("headerValue") String header,@CookieParam("cookie")String cookie)
//Matrixparam annotation is just like queryparam annotation difference is only in case of queryparam we differentiate 
//value with ? symbol like :- http://localhost:8080/messages?start=5&end=7 and in case of matrix param we differentiate
//value with ; symbol like :- http://localhost:8080/messages;start=5&end=7.
//We are using headerparam annotation for getting value from header of url  and same for cookieparam annotation as well
//if we want to get value of cookie then we will use this annotation.
	
	{
		return "test " + matrixparam + " " + header +" "+cookie;
	}
	
	@GET
	@Path("context")
	public String getParamsUsingContext(@Context UriInfo uriInfo,@Context HttpHeaders httpHeaders) {
		
		String path=uriInfo.getAbsolutePath().toString();
		String cookie=httpHeaders.getCookies().toString();
		return "Path : "+path+" cokkie "+  cookie;
	}

}
