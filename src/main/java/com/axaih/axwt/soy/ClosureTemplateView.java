package com.axaih.axwt.soy;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.shared.SoyCssRenamingMap;
import com.google.template.soy.tofu.SoyTofu;
import com.google.template.soy.tofu.SoyTofu.Renderer;

/**
 * <p>
 * ClosureTemplateView class.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
public class ClosureTemplateView {

	/** Constant <code>logger</code> */
	final static Logger logger = LoggerFactory
			.getLogger(ClosureTemplateView.class);

	private SoyTofu compiledTemplates;

	private Map<String, SoyCssRenamingMap> soyCssRenamingMapMap;

	private EmbeddedResourcesMap embeddedResourcesMap;

	private String templateName;

	public String renderMergedTemplateModel(Map<String, Object> model) {

		Renderer renderer = compiledTemplates.newRenderer(templateName);

		renderer.setData(model);

		renderer.setCssRenamingMap(soyCssRenamingMapMap.get(templateName));

		SoyMapData soyMapData = embeddedResourcesMap.get(templateName);

		if (soyMapData != null) {

			renderer.setIjData(soyMapData);
		}

		return renderer.render();

	}

	public String renderMergedTemplateModel() {

		Renderer renderer = compiledTemplates.newRenderer(templateName);

		renderer.setCssRenamingMap(soyCssRenamingMapMap.get(templateName));

		SoyMapData soyMapData = embeddedResourcesMap.get(templateName);

		if (soyMapData != null) {

			renderer.setIjData(soyMapData);
		}

		return renderer.render();

	}

	/**
	 * <p>
	 * Setter for the field <code>compiledTemplates</code>.
	 * </p>
	 * 
	 * @param compiledTemplates
	 *            a {@link com.google.template.soy.tofu.SoyTofu} object.
	 */
	public void setCompiledTemplates(SoyTofu compiledTemplates) {
		this.compiledTemplates = compiledTemplates;
	}

	/**
	 * <p>
	 * Setter for the field <code>embeddedResourcesMap</code>.
	 * </p>
	 * 
	 * @param embeddedResourcesMap
	 *            a {@link com.axaih.axwt.soy.EmbeddedResourcesMap} object.
	 */
	public void setEmbeddedResourcesMap(
			EmbeddedResourcesMap embeddedResourcesMap) {
		this.embeddedResourcesMap = embeddedResourcesMap;

	}

	/**
	 * <p>
	 * Setter for the field <code>templateName</code>.
	 * </p>
	 * 
	 * @param templateName
	 *            a {@link java.lang.String} object.
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * <p>
	 * Setter for the field <code>soyCssRenamingMap</code>.
	 * </p>
	 * 
	 * @param soyCssRenamingMap
	 *            a {@link com.google.template.soy.shared.SoyCssRenamingMap}
	 *            object.
	 */
	public void setSoyCssRenamingMapMap(
			Map<String, SoyCssRenamingMap> soyCssRenamingMapMap) {
		this.soyCssRenamingMapMap = soyCssRenamingMapMap;
	}

}
