# 通过MyBatis分析ImportBeanDefinitionRegistrar与@Import注解以及bean定义的注册过程分析
之前在的介绍中已经说明了大多数bean定义都由[BeanDefinitionRegistryPostProcessor](https://github.com/ShenFeng312/study/blob/master/spring/BeanDefinitionRegistryPostProcessor%E6%8E%A5%E5%8F%A3.md)注册。如果我们要自己干预注册可以自己添加一个BeanDefinitionRegistryPostProcessor的实现。

除了这种方式spring还有一种更简单的方式来注册。``@Import``和``ImportBeanDefinitionRegistrar``
以Mybatis为例 我们如果要把mapper 注入到容器中则需要在springboot的启动类上添加一个``@MapperScan("com.sf.ioctest.mapper")``的注解。
spring 在执行`BeanDefinitionRegistryPostProcessor`的`postProcessBeanDefinitionRegistry`方法时。其默认实现的`ConfigurationClassPostProcessor`
会把带`@Import`注解的bean定义特殊处理。如果Import的是一个`ImportBeanDefinitionRegistrar`的实现类则会执行他的`registerBeanDefinitions`方法来注册bean

代码如下：
首先看`MapperScan`的代码
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
//这里合并了@Import注解并且@Import的class是ImportBeanDefinitionRegistrar接口的实现
@Import(MapperScannerRegistrar.class)
@Repeatable(MapperScans.class)
public @interface MapperScan {
```
首先我们要知道我们从容器启动时就把标记有MapperScan的类注册给了容器（一般情况下都是标记在启动类上的）
或者标记在其他注入到了容器内的类上（带@Component的注解标记的类切能扫描到）
spring会把这些注入到容器中国的类当做配置类处理读取其中的注解的信息。
在[基于注解配置的源码分析3.容器刷新](https://github.com/ShenFeng312/study/blob/master/spring/%E5%9F%BA%E4%BA%8E%E6%B3%A8%E8%A7%A3%E9%85%8D%E7%BD%AE%E7%9A%84%E6%BA%90%E7%A0%81%E5%88%86%E6%9E%903.%E5%AE%B9%E5%99%A8%E5%88%B7%E6%96%B0.md)
中我们看到了代码中申明了一个`ConfigurationClassParser`来出路这些配置类
```java
ConfigurationClassParser parser = new ConfigurationClassParser(
  			this.metadataReaderFactory, this.problemReporter, this.environment,
  			this.resourceLoader, this.componentScanBeanNameGenerator, registry);

  	Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
  	Set<ConfigurationClass> alreadyParsed = new HashSet<>(configCandidates.size());
  	do {
  	//通过这个解析器解析获取所有第一次spring能解析的BeanDefinition并注册到beanFactory的BeanDefintionMap中
  	
  		parser.parse(candidates);
  		parser.validate();```
      ...
而`parser.parse(candidates)`的关键代码就是这个方法`doProcessConfigurationClass`
```java
protected final SourceClass doProcessConfigurationClass(ConfigurationClass configClass, SourceClass sourceClass)
			throws IOException {
    //先递归处理自雷
		if (configClass.getMetadata().isAnnotated(Component.class.getName())) {
			// Recursively process any member (nested) classes first
			processMemberClasses(configClass, sourceClass);
		}
    //处理@PropertySource
		// Process any @PropertySource annotations
		for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(
				sourceClass.getMetadata(), PropertySources.class,
				org.springframework.context.annotation.PropertySource.class)) {
			if (this.environment instanceof ConfigurableEnvironment) {
				processPropertySource(propertySource);
			}
			else {
				logger.info("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() +
						"]. Reason: Environment must implement ConfigurableEnvironment");
			}
		}
    //包扫描的处理如果注解带有ComponentScan则会扫描包并且递归处理
		// Process any @ComponentScan annotations
		Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
				sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
		if (!componentScans.isEmpty() &&
				!this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
			for (AnnotationAttributes componentScan : componentScans) {
				// The config class is annotated with @ComponentScan -> perform the scan immediately
				Set<BeanDefinitionHolder> scannedBeanDefinitions =
						this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
				// Check the set of scanned definitions for any further config classes and parse recursively if needed
				for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
					BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
					if (bdCand == null) {
						bdCand = holder.getBeanDefinition();
					}
					if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
					  //这里递归处理
            parse(bdCand.getBeanClassName(), holder.getBeanName());
					}
				}
			}
		}
    //处理带Import的
		// Process any @Import annotations
		processImports(configClass, sourceClass, getImports(sourceClass), true);

		// Process any @ImportResource annotations
		AnnotationAttributes importResource =
				AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
		if (importResource != null) {
			String[] resources = importResource.getStringArray("locations");
			Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
			for (String resource : resources) {
				String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
				configClass.addImportedResource(resolvedResource, readerClass);
			}
		}

		// Process individual @Bean methods
		Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
		for (MethodMetadata methodMetadata : beanMethods) {
			configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
		}

		// Process default methods on interfaces
		processInterfaces(configClass, sourceClass);

		// Process superclass, if any
		if (sourceClass.getMetadata().hasSuperClass()) {
			String superclass = sourceClass.getMetadata().getSuperClassName();
			if (superclass != null && !superclass.startsWith("java") &&
					!this.knownSuperclasses.containsKey(superclass)) {
				this.knownSuperclasses.put(superclass, configClass);
				// Superclass found, return its annotation metadata and recurse
				return sourceClass.getSuperClass();
			}
		}

		// No superclass -> processing is complete
		return null;
	}
  ```
在下面的代码中我们可以看到如果Import的是一个ImportBeanDefinitionRegistrar类会调用
`configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());`添加一个注册器
```java
	private void processImports(ConfigurationClass configClass, SourceClass currentSourceClass,
			Collection<SourceClass> importCandidates, boolean checkForCircularImports) {

		if (importCandidates.isEmpty()) {
			return;
		}

		if (checkForCircularImports && isChainedImportOnStack(configClass)) {
			this.problemReporter.error(new CircularImportProblem(configClass, this.importStack));
		}
		else {
			this.importStack.push(configClass);
			try {
				for (SourceClass candidate : importCandidates) {
					if (candidate.isAssignable(ImportSelector.class)) {
						// Candidate class is an ImportSelector -> delegate to it to determine imports
						Class<?> candidateClass = candidate.loadClass();
						ImportSelector selector = ParserStrategyUtils.instantiateClass(candidateClass, ImportSelector.class,
								this.environment, this.resourceLoader, this.registry);
						if (selector instanceof DeferredImportSelector) {
							this.deferredImportSelectorHandler.handle(configClass, (DeferredImportSelector) selector);
						}
						else {
							String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
							Collection<SourceClass> importSourceClasses = asSourceClasses(importClassNames);
							processImports(configClass, currentSourceClass, importSourceClasses, false);
						}
					}
					else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
						// Candidate class is an ImportBeanDefinitionRegistrar ->
						// delegate to it to register additional bean definitions
						Class<?> candidateClass = candidate.loadClass();
						ImportBeanDefinitionRegistrar registrar =
								ParserStrategyUtils.instantiateClass(candidateClass, ImportBeanDefinitionRegistrar.class,
										this.environment, this.resourceLoader, this.registry);
						configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());
					}
					else {
						// Candidate class not an ImportSelector or ImportBeanDefinitionRegistrar ->
						// process it as an @Configuration class
						this.importStack.registerImport(
								currentSourceClass.getMetadata(), candidate.getMetadata().getClassName());
						processConfigurationClass(candidate.asConfigClass(configClass));
					}
				}
			}
			catch (BeanDefinitionStoreException ex) {
				throw ex;
			}
			catch (Throwable ex) {
				throw new BeanDefinitionStoreException(
						"Failed to process import candidates for configuration class [" +
						configClass.getMetadata().getClassName() + "]", ex);
			}
			finally {
				this.importStack.pop();
			}
		}
	}
  ```
  
  当转化完成之后自然是要解析这个配置;

```java
	// Read the model and create bean definitions based on its content
			if (this.reader == null) {
				this.reader = new ConfigurationClassBeanDefinitionReader(
						registry, this.sourceExtractor, this.resourceLoader, this.environment,
						this.importBeanNameGenerator, parser.getImportRegistry());
			}
      //通过这个reader去加载bean定义
			this.reader.loadBeanDefinitions(configClasses);
			alreadyParsed.addAll(configClasses);
```


他在`loadBeanDefinitionsForConfigurationClass`方法中实现
```java
	private void loadBeanDefinitionsForConfigurationClass(
			ConfigurationClass configClass, TrackedConditionEvaluator trackedConditionEvaluator) {
    //判断是否需要跳过。只有条件注入才会进入
		if (trackedConditionEvaluator.shouldSkip(configClass)) {
			String beanName = configClass.getBeanName();
			if (StringUtils.hasLength(beanName) && this.registry.containsBeanDefinition(beanName)) {
				this.registry.removeBeanDefinition(beanName);
			}
			this.importRegistry.removeImportingClass(configClass.getMetadata().getClassName());
			return;
		}
    //被Import的类先自己注册 
		if (configClass.isImported()) {
			registerBeanDefinitionForImportedConfigurationClass(configClass);
		}
    //如果配置类中含有@bean
		for (BeanMethod beanMethod : configClass.getBeanMethods()) {
			loadBeanDefinitionsForBeanMethod(beanMethod);
		}
    //如果是Import 的Resource文件在这里加载
		loadBeanDefinitionsFromImportedResources(configClass.getImportedResources());
    //这里去执行`ImportBeanDefinitionRegistrar`中的方法 从而干预bean的生成
		loadBeanDefinitionsFromRegistrars(configClass.getImportBeanDefinitionRegistrars());
	}
```
      
