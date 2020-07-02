package pl.dreem.sync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.dreem.sync.db.DbConnectionFacade;
import pl.dreem.sync.db.replication.ReplicationDbConnectionMock;
import pl.dreem.sync.db.source.SourceDBConnectionMock;

@Configuration
public class MockDBConf {

    @Bean("sourceDBFacade")
    public DbConnectionFacade sourceDB(SourceDBConnectionMock sourceDB) {
        return new DbConnectionFacade(sourceDB);
    }

    @Bean("replicateDBFacade")
    public DbConnectionFacade replicateDB(ReplicationDbConnectionMock replicateDB) {
        return new DbConnectionFacade(replicateDB);
    }
}