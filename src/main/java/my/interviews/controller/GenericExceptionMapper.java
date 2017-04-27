package my.interviews.controller;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class GenericExceptionMapper extends Throwable implements ExceptionMapper<Throwable> {
  private static final long serialVersionUID = 4312321;

  @Override
  public Response toResponse(Throwable exception) {
    return Response.status(500).entity(exception.getMessage()).type("text/plain").build();
  }
}