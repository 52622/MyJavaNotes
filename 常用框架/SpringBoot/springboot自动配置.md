参考

https://www.cnblogs.com/leihuazhe/p/7743479.html

应用启动类

注解：

@SpringBootApplication

是一个组合注解

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
```

观察

@EnableAutoConfiguration

```java
@SuppressWarnings("deprecation")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(EnableAutoConfigurationImportSelector.class)
```

加入了EnableAutoConfigurationImportSelector这个bean

```java
@Deprecated
public class EnableAutoConfigurationImportSelector
		extends AutoConfigurationImportSelector {

	@Override
	protected boolean isEnabled(AnnotationMetadata metadata) {
		if (getClass().equals(EnableAutoConfigurationImportSelector.class)) {
			return getEnvironment().getProperty(
					EnableAutoConfiguration.ENABLED_OVERRIDE_PROPERTY, Boolean.class,
					true);
		}
		return true;
	}

}
```

查看父类

AutoConfigurationImportSelector

```java
	@Override
	public String[] selectImports(AnnotationMetadata annotationMetadata) {
		if (!isEnabled(annotationMetadata)) {
			return NO_IMPORTS;
		}
		try {
			AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
					.loadMetadata(this.beanClassLoader);
			AnnotationAttributes attributes = getAttributes(annotationMetadata);
			List<String> configurations = getCandidateConfigurations(annotationMetadata,
					attributes);
			configurations = removeDuplicates(configurations);
			configurations = sort(configurations, autoConfigurationMetadata);
			Set<String> exclusions = getExclusions(annotationMetadata, attributes);
			checkExcludedClasses(configurations, exclusions);
			configurations.removeAll(exclusions);
			configurations = filter(configurations, autoConfigurationMetadata);
			fireAutoConfigurationImportEvents(configurations, exclusions);
			return configurations.toArray(new String[configurations.size()]);
		}
		catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
	}
```

这一行代码

```java
List<String> configurations = getCandidateConfigurations(annotationMetadata,
					attributes);
```

```java
	protected List<String> getCandidateConfigurations(AnnotationMetadata metadata,
			AnnotationAttributes attributes) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(
				getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
		Assert.notEmpty(configurations,
				"No auto configuration classes found in META-INF/spring.factories. If you "
						+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
```

说明了要进行自动配置的包的配置文件路径

META-INF/spring.factories

这个文件里面都写了什么

```xml
# Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration,\
org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
```

都是一些要自动配置的包的路径

```java
org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
```

以这个为例

```java
@Configuration
@ConditionalOnClass({ Mongo.class, MongoTemplate.class })
@EnableConfigurationProperties(MongoProperties.class)
@AutoConfigureAfter(MongoAutoConfiguration.class)
```

@ConditionalOnClass注解做了什么

是一个组合注解

```java
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnClassCondition.class)
```

看看OnClassCondition这个类

重写了match方法

```java
	@Override
	public boolean[] match(String[] autoConfigurationClasses,
			AutoConfigurationMetadata autoConfigurationMetadata) {
		ConditionEvaluationReport report = getConditionEvaluationReport();
		ConditionOutcome[] outcomes = getOutcomes(autoConfigurationClasses,
				autoConfigurationMetadata);
		boolean[] match = new boolean[outcomes.length];
		for (int i = 0; i < outcomes.length; i++) {
			match[i] = (outcomes[i] == null || outcomes[i].isMatch());
			if (!match[i] && outcomes[i] != null) {
				logOutcome(autoConfigurationClasses[i], outcomes[i]);
				if (report != null) {
					report.recordConditionEvaluation(autoConfigurationClasses[i], this,
							outcomes[i]);
				}
			}
		}
		return match;
	}
```

看一下父类SpringBootCondition

实现了Condition接口

```java
public abstract class SpringBootCondition implements Condition {

	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public final boolean matches(ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		String classOrMethodName = getClassOrMethodName(metadata);
		try {
			ConditionOutcome outcome = getMatchOutcome(context, metadata);
			logOutcome(classOrMethodName, outcome);
			recordEvaluation(context, classOrMethodName, outcome);
			return outcome.isMatch();
		}
		catch (NoClassDefFoundError ex) {
			throw new IllegalStateException(
					"Could not evaluate condition on " + classOrMethodName + " due to "
							+ ex.getMessage() + " not "
							+ "found. Make sure your own configuration does not rely on "
							+ "that class. This can also happen if you are "
							+ "@ComponentScanning a springframework package (e.g. if you "
							+ "put a @ComponentScan in the default package by mistake)",
					ex);
		}
		catch (RuntimeException ex) {
			throw new IllegalStateException(
					"Error processing condition on " + getName(metadata), ex);
		}
	}
    ...
}
```

以上就实现了当类路径下有以下class的时候，自动配置就会被激活

@ConditionalOnClass({ Mongo.class, MongoTemplate.class })

而且配置项在

```
@EnableConfigurationProperties(MongoProperties.class)
```

```java
/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.autoconfigure.mongo;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

/**
 * Configuration properties for Mongo.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Josh Long
 * @author Andy Wilkinson
 * @author Eddú Meléndez
 * @author Stephane Nicoll
 * @author Nasko Vasilev
 */
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoProperties {

	/**
	 * Default port used when the configured port is {@code null}.
	 */
	public static final int DEFAULT_PORT = 27017;

	public static final String DEFAULT_URI = "mongodb://localhost/test";

	/**
	 * Mongo server host. Cannot be set with uri.
	 */
	private String host;

	/**
	 * Mongo server port. Cannot be set with uri.
	 */
	private Integer port = null;

	/**
	 * Mongo database URI. Cannot be set with host, port and credentials.
	 */
	private String uri;

	/**
	 * Database name.
	 */
	private String database;

	/**
	 * Authentication database name.
	 */
	private String authenticationDatabase;

	/**
	 * GridFS database name.
	 */
	private String gridFsDatabase;

	/**
	 * Login user of the mongo server. Cannot be set with uri.
	 */
	private String username;

	/**
	 * Login password of the mongo server. Cannot be set with uri.
	 */
	private char[] password;

	/**
	 * Fully qualified name of the FieldNamingStrategy to use.
	 */
	private Class<?> fieldNamingStrategy;

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return this.database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getAuthenticationDatabase() {
		return this.authenticationDatabase;
	}

	public void setAuthenticationDatabase(String authenticationDatabase) {
		this.authenticationDatabase = authenticationDatabase;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return this.password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public Class<?> getFieldNamingStrategy() {
		return this.fieldNamingStrategy;
	}

	public void setFieldNamingStrategy(Class<?> fieldNamingStrategy) {
		this.fieldNamingStrategy = fieldNamingStrategy;
	}

	public void clearPassword() {
		if (this.password == null) {
			return;
		}
		for (int i = 0; i < this.password.length; i++) {
			this.password[i] = 0;
		}
	}

	public String getUri() {
		return this.uri;
	}

	public String determineUri() {
		return (this.uri != null ? this.uri : DEFAULT_URI);
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getGridFsDatabase() {
		return this.gridFsDatabase;
	}

	public void setGridFsDatabase(String gridFsDatabase) {
		this.gridFsDatabase = gridFsDatabase;
	}

	public String getMongoClientDatabase() {
		if (this.database != null) {
			return this.database;
		}
		return new MongoClientURI(determineUri()).getDatabase();
	}

	/**
	 * Creates a {@link MongoClient} using the given {@code options} and
	 * {@code environment}. If the environment contains a {@code local.mongo.port}
	 * property, it is used to configure a client to an embedded MongoDB instance.
	 * @param options the options
	 * @param environment the environment
	 * @return the Mongo client
	 * @throws UnknownHostException if the configured host is unknown
	 */
	public MongoClient createMongoClient(MongoClientOptions options,
			Environment environment) throws UnknownHostException {
		try {
			Integer embeddedPort = getEmbeddedPort(environment);
			if (embeddedPort != null) {
				return createEmbeddedMongoClient(options, embeddedPort);
			}
			return createNetworkMongoClient(options);
		}
		finally {
			clearPassword();
		}
	}

	private Integer getEmbeddedPort(Environment environment) {
		if (environment != null) {
			String localPort = environment.getProperty("local.mongo.port");
			if (localPort != null) {
				return Integer.valueOf(localPort);
			}
		}
		return null;
	}

	private MongoClient createEmbeddedMongoClient(MongoClientOptions options, int port) {
		if (options == null) {
			options = MongoClientOptions.builder().build();
		}
		String host = this.host == null ? "localhost" : this.host;
		return new MongoClient(Collections.singletonList(new ServerAddress(host, port)),
				Collections.<MongoCredential>emptyList(), options);
	}

	private MongoClient createNetworkMongoClient(MongoClientOptions options) {
		if (hasCustomAddress() || hasCustomCredentials()) {
			if (this.uri != null) {
				throw new IllegalStateException("Invalid mongo configuration, "
						+ "either uri or host/port/credentials must be specified");
			}
			if (options == null) {
				options = MongoClientOptions.builder().build();
			}
			List<MongoCredential> credentials = new ArrayList<MongoCredential>();
			if (hasCustomCredentials()) {
				String database = this.authenticationDatabase == null
						? getMongoClientDatabase() : this.authenticationDatabase;
				credentials.add(MongoCredential.createCredential(this.username, database,
						this.password));
			}
			String host = this.host == null ? "localhost" : this.host;
			int port = this.port != null ? this.port : DEFAULT_PORT;
			return new MongoClient(
					Collections.singletonList(new ServerAddress(host, port)), credentials,
					options);
		}
		// The options and credentials are in the URI
		return new MongoClient(new MongoClientURI(determineUri(), builder(options)));
	}

	private boolean hasCustomAddress() {
		return this.host != null || this.port != null;
	}

	private boolean hasCustomCredentials() {
		return this.username != null && this.password != null;
	}

	private Builder builder(MongoClientOptions options) {
		if (options != null) {
			return MongoClientOptions.builder(options);
		}
		return MongoClientOptions.builder();
	}

}

```

