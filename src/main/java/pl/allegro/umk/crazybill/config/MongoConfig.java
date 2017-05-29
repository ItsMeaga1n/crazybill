package pl.allegro.umk.crazybill.config;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import pl.allegro.umk.crazybill.repository.BillsRepository;

@Configuration
@Profile({"local", "prod"})
@EnableMongoRepositories(basePackageClasses = {BillsRepository.class})
public class MongoConfig extends AbstractMongoConfiguration {
	private static final String MONGO_DATABASE = "bills";

	@Override
	protected String getDatabaseName() {
		return MONGO_DATABASE;
	}

	@Override
	public Mongo mongo() {
		return new MongoClient("localhost");
	}

	@Override
	protected String getMappingBasePackage() {
		return "pl.allegro.umk.crazybill";
	}
}

