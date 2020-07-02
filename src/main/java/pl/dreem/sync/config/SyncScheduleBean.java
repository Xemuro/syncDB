package pl.dreem.sync.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class SyncScheduleBean {

    @Bean
    public ScheduledExecutorService scheduleBean(){
        return Executors.newSingleThreadScheduledExecutor();
    }
}