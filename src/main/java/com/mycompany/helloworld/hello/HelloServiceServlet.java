package com.mycompany.helloworld.hello;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;

import org.apache.http.HttpStatus;

import pbdesc.com.mycompany.helloworld.HomePbDesc.HelloMsg;

import com.axaih.pblite.PbLiteSerializer;
import com.axaih.webservice.CommandServlet;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class HelloServiceServlet extends CommandServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, String> statusMap = new HashMap<String, String>();

	@Inject
	public HelloServiceServlet() {

		// TODO Auto-generated constructor stub

	}

	private static class Cmd {
		public static final String  say = "say";

	}

	/**
	 * <p>
	 * autoComplete.
	 * </p>
	 * 
	 * @param request
	 *            a {@link javax.servlet.http.HttpServletRequest} object.
	 * @param response
	 *            a {@link javax.servlet.http.HttpServletResponse} object.
	 * @param token
	 *            a {@link java.lang.String} object.
	 * @param max_matches
	 *            a {@link java.lang.String} object.
	 * @param use_similar
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	final Gson gson = new Gson();

	public void commandExec(String cmdId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
 

		this.setCorsPolicie(httpServletRequest, httpServletResponse);

		switch (cmdId) {

		case Cmd.say: {

			say(httpServletRequest, httpServletResponse);

		}
			break;
		default: {
			httpServletResponse.setStatus(HttpStatus.SC_BAD_REQUEST);
		}
			break;
		}
	}

	private void say(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {

		Preconditions.checkState(httpServletRequest.getMethod().equals(
				HttpMethod.POST));
		
		HelloMsg helloMsg = (HelloMsg) PbLiteSerializer.deserialize(
				HelloMsg.newBuilder(), httpServletRequest.getReader());
		helloMsg.toBuilder().setText("Hi user, keep calm and stop clicks");

		httpServletResponse.getWriter().write(
				PbLiteSerializer.serialize(helloMsg));

	}

}
