package org.kivilev.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import io.mongock.driver.mongodb.reactive.driver.MongoReactiveDriver;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockInitializingBeanRunner;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.kivilev.mongock.changelog.DictionariesInitializerChangeUnit;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.Clock;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
@EnableMongoRepositories(basePackages = "org.kivilev.dao.repository")
@EnableConfigurationProperties(MongoDbConfigurationProperties.class)
public class ApplicationConfig {

    @Bean
    public Clock mainClock() {
        return Clock.systemUTC();
    }

    @Bean
    public MongockInitializingBeanRunner getBuilder(MongoClient reactiveMongoClient,
                                                    ApplicationContext context,
                                                    MongoDbConfigurationProperties properties) {
        return MongockSpringboot.builder()
                .setDriver(MongoReactiveDriver.withDefaultLock(reactiveMongoClient, properties.getDatabase()))
                .addMigrationScanPackage(DictionariesInitializerChangeUnit.class.getPackageName())
                .setSpringContext(context)
                .setTransactionEnabled(false)
                .buildInitializingBeanRunner();
    }

    @Bean
    MongoClient mongoClient(MongoDbConfigurationProperties properties) {
        CodecRegistry codecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        return MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(properties.getUri()))
                .codecRegistry(codecRegistry)
                .build());
    }
}
