package com.axaih.axwt.soy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import org.springframework.core.io.Resource;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.template.soy.SoyFileSet;
import com.google.template.soy.shared.SoyCssRenamingMap;
import com.google.template.soy.shared.SoyGeneralOptions.CssHandlingScheme;
import com.google.template.soy.tofu.SoyTofu;

/**
 * <p>
 * ClosureTemplateViewResolver class.
 * </p>
 * 
 * @author andreani
 * @version $Id: $Id
 */
@Singleton
public class ClosureTemplateViewResolver {

	/** Constant <code>logger</code> */
	private static final Logger logger = Logger.getLogger(ClosureTemplateViewResolver.class.getName());
	boolean cacheEnabled = false;

	private ClosureTemplateConfig closureTemplateConfig;

	private SoyTofu compiledTemplates;

	private Map<String, SoyCssRenamingMap> soyCssRenamingMapMap;

	private EmbeddedResourcesMap embeddedResourcesMap;
	private Gson gson = new Gson();

	/**
	 * <p>
	 * Constructor for ClosureTemplateViewResolver.
	 * </p>
	 */
	@Inject
	public ClosureTemplateViewResolver(
			ClosureTemplateConfig closureTemplateConfig) {
		this.closureTemplateConfig = closureTemplateConfig;

		init();
	}

	public ClosureTemplateView buildView(String viewName) throws Exception {
		ClosureTemplateView view = new ClosureTemplateView();

		view.setTemplateName(viewName);

		if (isCacheEnabled()) {
			view.setCompiledTemplates(compiledTemplates);
			view.setSoyCssRenamingMapMap(soyCssRenamingMapMap);

			view.setEmbeddedResourcesMap(embeddedResourcesMap);

		} else {
			view.setCompiledTemplates(compileTemplates());
			view.setSoyCssRenamingMapMap(loadCssRenamingMap());

			view.setEmbeddedResourcesMap(loadEmbeddedResourcesMap());
		}

		return view;
	}

	/**
	 * <p>
	 * compileTemplates.
	 * </p>
	 * 
	 * @return a {@link com.google.template.soy.tofu.SoyTofu} object.
	 */
	private SoyTofu compileTemplates() {

		Resource templatesLocation = closureTemplateConfig
				.getTemplatesLocation();
		List<File> templateFiles;
		try {
			File baseDirectory = templatesLocation.getFile();
			if (baseDirectory.isDirectory()) {
				templateFiles = findSoyFiles(baseDirectory,
						closureTemplateConfig.isRecursive());
			} else {
				throw new IllegalArgumentException(
						"Soy template base directory '" + templatesLocation
								+ "' is not a directory");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Soy template base directory '"
					+ templatesLocation + "' does not exist", e);
		}

		SoyFileSet.Builder fileSetBuilder = new SoyFileSet.Builder();

		for (File templateFile : templateFiles) {
			fileSetBuilder.add(templateFile);
		}
		;
		fileSetBuilder.setCssHandlingScheme(CssHandlingScheme.BACKEND_SPECIFIC);

		SoyFileSet soyFileSet = fileSetBuilder.build();

		return soyFileSet.compileToTofu();
	}

	/**
	 * <p>
	 * findEmbeddedResourcesFiles.
	 * </p>
	 * 
	 * @param baseDirectory
	 *            a {@link java.io.File} object.
	 * @param extension
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	protected List<File> findEmbeddedResourcesFiles(File baseDirectory,
			String extension) {
		File[] files = baseDirectory.listFiles();
		List<File> ret = new ArrayList<File>();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					if (file.getName().endsWith(extension)) {
						ret.add(file);
					}
				}
			}
		}
		return ret;
	}

	/**
	 * <p>
	 * findSoyFiles.
	 * </p>
	 * 
	 * @param baseDirectory
	 *            a {@link java.io.File} object.
	 * @param recursive
	 *            a boolean.
	 * @return a {@link java.util.List} object.
	 */
	protected List<File> findSoyFiles(File baseDirectory, boolean recursive) {
		List<File> soyFiles = new ArrayList<File>();
		findSoyFiles(soyFiles, baseDirectory, recursive);
		return soyFiles;
	}

	/**
	 * <p>
	 * findSoyFiles.
	 * </p>
	 * 
	 * @param soyFiles
	 *            a {@link java.util.List} object.
	 * @param baseDirectory
	 *            a {@link java.io.File} object.
	 * @param recursive
	 *            a boolean.
	 */
	protected void findSoyFiles(List<File> soyFiles, File baseDirectory,
			boolean recursive) {
		File[] files = baseDirectory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					if (file.getName().endsWith(".soy")) {
						soyFiles.add(file);
					}
				} else if (file.isDirectory() && recursive) {
					findSoyFiles(soyFiles, file, recursive);
				}
			}
		} else {
			throw new IllegalArgumentException(
					"Unable to retrieve contents of: " + baseDirectory);
		}
	}

	protected Class<?> getViewClass() {
		return ClosureTemplateView.class;
	}

	public void init() {

		soyCssRenamingMapMap = loadCssRenamingMap();
		compiledTemplates = compileTemplates();
		embeddedResourcesMap = loadEmbeddedResourcesMap();
	}

	public boolean isCacheEnabled() {
		return cacheEnabled;
	}

	/**
	 * <p>
	 * loadCssRenamingMap.
	 * </p>
	 * 
	 * @return a {@link com.google.template.soy.shared.SoyCssRenamingMap}
	 *         object.
	 */
	private Map<String, SoyCssRenamingMap> loadCssRenamingMap() {
		SoyCssRenamingMap soyCssRenamingMap = null;
		Map<String, SoyCssRenamingMap> ret = new HashMap<String, SoyCssRenamingMap>();

		Resource renamingMapFileLocation = closureTemplateConfig
				.getRenamingMapFileLocation();

		try {
			File folder = renamingMapFileLocation.getFile();
			ArrayList<File> fileList = null;
			if (folder.isDirectory()) {

				fileList = new ArrayList<File>(
						Arrays.asList(folder.listFiles()));
				for (File file : fileList) {
					logger.info("try load renaming map file: " + file.getName());

					if (file.getName().endsWith(".json")) {
						if (file.canRead()) {

							FileReader fileReader = new FileReader(file);
							char[] chars = new char[(int) file.length()];
							fileReader.read(chars);
							String content = new String(chars);
							fileReader.close();
							Type type = new TypeToken<Map<String, String>>() {
							}.getType();
							Map<String, String> map = gson.fromJson(content,
									type);

							soyCssRenamingMap = new SoyCssRenamingMapImpl(map);
							String key = Files.getNameWithoutExtension(file
									.getName());
							ret.put(key, soyCssRenamingMap);

							if (logger.isLoggable(Level.FINE)) {
								logger.log(Level.FINE, 
										"renaming map json content: "
												+ gson.toJson(map, type));
							}
							logger.info("renaming map file " + key
									+ " loaded. ");
						}
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ret;

	}

	/**
	 * <p>
	 * loadEmbeddedResourcesMap.
	 * </p>
	 * 
	 * @return a {@link com.axaih.axwt.soy.EmbeddedResourcesMap} object.
	 */
	private EmbeddedResourcesMap loadEmbeddedResourcesMap() {
		EmbeddedResourcesMap ret = null;
		Resource resource = closureTemplateConfig
				.getEmbeddedResourcesLocation();
		List<File> jsFiles = null;
		List<File> cssFiles = null;
		try {
			File baseDirectory = resource.getFile();
			if (baseDirectory.isDirectory()) {
				jsFiles = findEmbeddedResourcesFiles(baseDirectory, ".js");
				cssFiles = findEmbeddedResourcesFiles(baseDirectory, ".css");

				Map<String, String> jsContentMap = new HashMap<String, String>();
				for (File file : jsFiles) {
					if (file.canRead()) {

						String content = Files.toString(file,
								java.nio.charset.Charset.forName("UTF-8"));
						jsContentMap.put(
								Files.getNameWithoutExtension(file.getName()),
								content);

					}
				}

				Map<String, String> cssContentMap = new HashMap<String, String>();
				for (File file : cssFiles) {
					if (file.canRead()) {

						String content = Files.toString(file,
								java.nio.charset.Charset.forName("UTF-8"));
						cssContentMap.put(
								Files.getNameWithoutExtension(file.getName()),
								content);

					}
				}

				ret = new EmbeddedResourcesMapImpl(jsContentMap, cssContentMap);
			} else {
				throw new IllegalArgumentException(
						"Embedded Resources base directory '" + resource
								+ "' is not a directory");
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"Embedded Resources base directory  '" + resource
							+ "' does not exist", e);
		}

		return ret;
	}

	public void setCacheEnabled(boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}

	/**
	 * <p>
	 * Setter for the field <code>closureTemplateConfig</code>.
	 * </p>
	 * 
	 * @param closureTemplateConfig
	 *            a {@link com.axaih.axwt.soy.ClosureTemplateConfig} object.
	 */
	public void setClosureTemplateConfig(
			ClosureTemplateConfig closureTemplateConfig) {
		this.closureTemplateConfig = closureTemplateConfig;
	}
}
