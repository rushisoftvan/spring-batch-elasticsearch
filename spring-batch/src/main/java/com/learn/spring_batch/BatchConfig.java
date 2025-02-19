package com.learn.spring_batch;

import com.learn.spring_batch.entities.SalesEntity;
import com.learn.spring_batch.processer.SalesItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration

public class BatchConfig {



    @Bean
    public FlatFileItemReader<SalesEntity> reader() {
        return new FlatFileItemReaderBuilder<SalesEntity>()
                .name("personItemReader")
                .resource(new ClassPathResource("10000 Sales Records.csv"))
                .delimited()
                .names("Region", "Country","Item Type"," Order Priority","Order Date","Order ID","Ship Date","Units " +
                        "Sold","Unit Price","Unit Cost","Total Revenue","Total Revenue","Total Cost","Total Profit")
                .targetType(SalesEntity.class)
                .build();
    }


    @Bean
    public SalesItemProcessor processor() {
        return new SalesItemProcessor();
    }

    @Bean
    public JpaItemWriter<SalesEntity> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<SalesEntity> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }



    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<SalesEntity> reader, SalesItemProcessor processor,
                      JpaItemWriter<SalesEntity> writer) {
        return new StepBuilder("step1", jobRepository)
                .<SalesEntity, SalesEntity>chunk(50, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }


    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Number of core threads
        executor.setMaxPoolSize(50);  // Maximum number of threads
        executor.setQueueCapacity(100); // Queue size before new threads are created
        executor.setThreadNamePrefix("job-thread-");
        executor.initialize();
        return executor;
    }

//   @Bean
//    public JobLauncher jobLauncher() throws Exception {
//        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
//        jobLauncher.setJobRepository(jobRepository); // Set the JobRepository, necessary for job execution.
//        jobLauncher.afterPropertiesSet();
//        jobLauncher.setTaskExecutor(taskExecutor());// Initializes the launcher.
//        return jobLauncher;
//    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .build();
    }

}
