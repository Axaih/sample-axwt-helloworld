package com.axaih.webservice;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.google.api.client.util.Charsets;
import com.google.common.base.Optional;
import com.google.common.base.Splitter;
import com.google.common.net.HttpHeaders;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

public abstract class CommandServlet extends HttpServlet {
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	protected static final Logger logger = Logger
			.getLogger(CommandServlet.class.getName());

	private final String separator;

	public CommandServlet() { 
		this.separator = "/";

	}

	public CommandServlet(
			String separator) { 
		this.separator = separator;

	}

	public abstract void commandExec(String cmdId, HttpServletRequest req,
			HttpServletResponse resp) throws HttpResponseException, IOException;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException { // TODO Auto-generated method stub
		logger.log(java.util.logging.Level.FINE, "req.getPathInfo() init ");

		if (req != null) {
			logger.log(java.util.logging.Level.FINE, "req.getPathInfo() ="
					+ Optional.fromNullable(req.getPathInfo()).or(" "));
		} else {
			logger.log(java.util.logging.Level.FINE, "req null");

		}
		resp.setHeader(HttpHeaders.SERVER.toString(), "axws");
		resp.setCharacterEncoding(Charsets.UTF_8.toString());
		logger.log(java.util.logging.Level.FINE, "req.getPathInfo() end ");
		Iterable<String> pathParamIterable = Splitter.on(separator)
				.trimResults().omitEmptyStrings()
				.split(Optional.fromNullable(req.getPathInfo()).or(""));

		java.util.Iterator<String> iterator = pathParamIterable.iterator();

		final String methodId = (iterator.hasNext()) ? iterator.next() : "";

		logger.log(java.util.logging.Level.FINE, " methodId: " + methodId);

		final HttpServletRequest httpServletRequest = req;
		final HttpServletResponse httpServletResponse = resp;
		requestHandler(httpServletRequest, httpServletResponse, methodId);

		// handle(listeningExecutorService, callable);

		logger.log(java.util.logging.Level.FINE, "command doGet ");

	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
		this.setCorsPolicie(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doGet(req, resp);
	}

	public void handle(ListeningExecutorService listeningExecutorService,
			Callable<Integer> callable) {
		logger.log(java.util.logging.Level.FINE, "ServletAsyncHandler executed");

		ListenableFuture<Integer> listenableFuture = listeningExecutorService
				.submit(callable);

		Futures.addCallback(listenableFuture, new FutureCallback<Integer>() {

			public void onFailure(Throwable t) {
				// TODO Auto-generated method stub
				t.printStackTrace();

			}

			public void onSuccess(@Nullable Integer result) {
				// TODO Auto-generated method stub

			}
		}, listeningExecutorService);
	}

	protected Integer requestHandler(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String methodId)
			throws HttpResponseException {

		try {

			try {

				commandExec(methodId, httpServletRequest, httpServletResponse);

			} catch (HttpResponseException e) {
				// TODO Auto-generated catch block

				httpServletResponse.setStatus(e.getStatusCode());

			}
			 
		} catch (IOException e) {
			throw new HttpResponseException(
					HttpStatus.SC_INTERNAL_SERVER_ERROR, "");
		}

		return Integer.valueOf(0);
	}

	protected void setCorsPolicie(HttpServletRequest req,
			HttpServletResponse resp) {

		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "*");
		resp.setHeader("Access-Control-Allow-Headers", "accept, content-type");
		resp.setHeader("Access-Control-Allow-Credentials", " true");
	}

}
