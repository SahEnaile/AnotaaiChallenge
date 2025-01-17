package config.mongo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoDBConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public MongoDatabaseFactory mongoConfigure() {
        String host = dotenv.get("MONGO_DB_HOST");
        String username = dotenv.get("MONGO_DB_USERNAME");
        String password = dotenv.get("MONGO_DB_PASSWORD");
        String database = dotenv.get("MONGO_DB_DATABASE");

        String connectionString = String.format(
                "mongodb+srv://%s:%s@%s/%s?retryWrites=true&w=majority",
                username,
                password,
                host,
                database
        );

        return new SimpleMongoClientDatabaseFactory(connectionString);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoConfigure());
    }
}
