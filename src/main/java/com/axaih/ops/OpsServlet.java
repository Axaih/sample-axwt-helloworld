package com.axaih.ops;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.axaih.axwt.soy.ClosureTemplateViewResolver;
import com.axaih.axwt.soy.Util;
import com.axaih.common.boot.BootImpl;
import com.axaih.common.boot.PathImpl;
import com.axaih.configuration.ConfigurationService;
import com.axaih.webservice.CommandServlet;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OpsServlet  extends CommandServlet{
	
 

	ConfigurationService configurationService;

	private BootImpl boot;
	final static Logger logger = LoggerFactory.getLogger(OpsServlet.class);

	Map<String, Map<String, ?>> bootCache;
	FastDateFormat httpDateFormat;

	private static class Cmd {

		public static final String  _default = "default"; 

	};

	class TemplatePageName {

		public final static String DEFAULT = "soy.axaih.ops.Default.boot"; 

	}

	ClosureTemplateViewResolver closureTemplateViewResolver;

	ImmutableMap<String, String> closureTemplateNameMap; 
	@Inject
	public OpsServlet(
			ClosureTemplateViewResolver closureTemplateViewResolver,
			ConfigurationService configurationService) {
		super();
		this.closureTemplateViewResolver = closureTemplateViewResolver;
		Builder<String, String> mapBuilder = ImmutableMap.builder();
		this.configurationService = configurationService;
		
		try {

			mapBuilder.put(Cmd._default, TemplatePageName.DEFAULT); 

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closureTemplateNameMap = mapBuilder.build();
		// TODO Auto-generated constructor stub

		httpDateFormat = FastDateFormat.getInstance(
				"EEE, dd MMM yyyy HH:mm:ss z", TimeZone.getTimeZone("GMT"),
				Locale.US);

		PathImpl path = new PathImpl();

		path.setRoot(configurationService.getRootPath());

		PathImpl l0Path = new PathImpl();

		l0Path.setRoot(configurationService.getRootL0path());

		logger.info("ops controller constructor");
		Map<String, Object> initData = new HashMap<String, Object>();

		boot = new BootImpl();
		boot.setPath(path);
		boot.setL0path(l0Path);
		boot.setInitData(initData);
		boot.setInitState("default");

		bootCache = new HashMap<String, Map<String, ?>>();
		bootCache.put("default", Util.toSoyCompatibleMap(boot));

		// ---
		homeModel = new Builder<String, Object>().put("boot",
				bootCache.get("default")).build();
	}

	final Map<String, Object> homeModel;
 

	@Override
	public void commandExec(String cmdId,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
			throws IOException {

		String ret = null;

		cmdId = (cmdId == "") ? Cmd._default : cmdId;

		/* get model */

		Map<String, Object> model = homeModel;
	 
		String templateName = closureTemplateNameMap.get(cmdId);
		
		

		if (templateName != null) {

			try {
				ret = closureTemplateViewResolver.buildView(templateName)
						.renderMergedTemplateModel(model);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "");
		}
		

		httpServletResponse.getWriter().write(ret); 
	}

}

