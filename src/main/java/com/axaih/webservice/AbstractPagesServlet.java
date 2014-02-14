package com.axaih.webservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;

import com.axaih.axwt.soy.ClosureTemplateViewResolver;

public abstract class AbstractPagesServlet extends CommandServlet {

	protected ClosureTemplateViewResolver closureTemplateViewResolver;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AbstractPagesServlet( 
			ClosureTemplateViewResolver closureTemplateViewResolver) {
		super( );
		this.closureTemplateViewResolver = closureTemplateViewResolver;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Integer requestHandler(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String methodId)
			throws HttpResponseException {

		try {
			final PrintWriter writer = httpServletResponse.getWriter();
			try {

				commandExec(methodId, httpServletRequest, httpServletResponse);

			} catch (HttpResponseException e) {
				// TODO Auto-generated catch block
				String ret = null;
				try {
					ret = closureTemplateViewResolver.buildView(
							"soy.axaih.ops.Default.boot")
							.renderMergedTemplateModel();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				httpServletResponse.setStatus(e.getStatusCode());
				httpServletResponse.flushBuffer();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new HttpResponseException(
					HttpStatus.SC_INTERNAL_SERVER_ERROR, "");

		}

		return Integer.valueOf(0);
	}

}
