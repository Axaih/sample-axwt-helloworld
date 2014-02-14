package com.axaih.webservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
 
import com.google.common.base.Optional;
import com.google.common.util.concurrent.ListeningExecutorService;

public abstract class PageServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ListeningExecutorService listeningExecutorService;

	public PageServlet(ListeningExecutorService listeningExecutorService) {

		this.listeningExecutorService = listeningExecutorService;
		// TODO Auto-generated constructor stub
	}

	protected void setCorsPolicie(HttpServletRequest req,
			HttpServletResponse resp) {

		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "*");
		resp.setHeader("Access-Control-Allow-Headers", "accept, content-type");
		resp.setHeader("Access-Control-Allow-Credentials", " true");
	}

	public abstract String control(HttpServletRequest req,
			HttpServletResponse resp)
			throws HttpResponseException;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PrintWriter writer = resp.getWriter();

		try {

			Optional<String> rendered = Optional
					.fromNullable(control(req, resp
							 ));
			writer.write(rendered.or(""));
		} catch (HttpResponseException e) {
			// TODO Auto-generated catch block
			resp.setStatus(e.getStatusCode());
			writer.write(e.getMessage());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			resp.sendError(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}

		writer.flush();

	}

}
