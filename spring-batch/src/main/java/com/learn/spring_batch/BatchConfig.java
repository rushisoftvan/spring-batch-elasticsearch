package com.learn.spring_batch;

import com.learn.spring_batch.entities.SalesEntity;
import com.learn.spring_batch.processer.SalesItemProcessor;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class BatchConfig {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


//    @Bean
//    public FlatFileItemReader<SalesEntity> reader() {
//        try {
//            return new FlatFileItemReaderBuilder<SalesEntity>()
//                    .name("personItemReader")
//                    .resource(new ClassPathResource("10000 Sales Records.csv"))
//                    .delimited()
//                    .names("Region","Country","Item Type",
//                            "Sales Channel","Order Priority","Order Date","Order ID","Ship Date","Units Sold"
//                            ,"Unit Price","Unit Cost","Total Revenue","Total Cost","Total Profit")
//
//                      .linesToSkip(1)
//                    .strict(false)
//
//                    .build();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//
//        // second solution
//
//
////        FlatFileItemReader<SalesEntity> reader = new FlatFileItemReader<>();
////        reader.setResource(new ClassPathResource("10000 Sales Records.csv"));
////
////        // Define date format for orderDate and shipDate
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
////
////        // Custom FieldSetMapper to handle LocalDate
////        reader.setLineMapper(new DefaultLineMapper<SalesEntity>() {{
////            setLineTokenizer(new DelimitedLineTokenizer() {{
////                setNames("Region","Country","Item Type",
////                            "Sales Channel","Order Priority","Order Date","Order ID","Ship Date","Units Sold"
////                            ,"Unit Price","Unit Cost","Total Revenue","Total Cost","Total Profit");
////            }});
////
////            setFieldSetMapper(new BeanWrapperFieldSetMapper<SalesEntity>() {{
////                setTargetType(SalesEntity.class);
////                setFieldSetMapper(new FieldSetMapper<SalesEntity>() {
////                    @Override
////                    public SalesEntity mapFieldSet(FieldSet fieldSet) {
////                        SalesEntity salesData = new SalesEntity();
////                        salesData.setRegion(fieldSet.readString("Region"));
////                        salesData.setCountry(fieldSet.readString("Country"));
////                        salesData.setItemType(fieldSet.readString("Item Type"));
////                        salesData.setSalesChannel(fieldSet.readString("Sales Channel"));
////                        salesData.setOrderPriority(fieldSet.readString("Order Priority"));
////
////                        // Parse orderDate and shipDate from string to LocalDate
////                        salesData.setOrderDate(LocalDate.parse(fieldSet.readString("Order Date"), formatter));
////                        salesData.setShipDate(LocalDate.parse(fieldSet.readString("shipDate"), formatter));
////
////                        salesData.setUnitsSold((long) fieldSet.readInt("Units Sold"));
////                        salesData.setUnitPrice(fieldSet.readDouble("Unit Price"));
////                        salesData.setUnitCost(fieldSet.readDouble("Unit Cost"));
////                        salesData.setTotalRevenue(fieldSet.readDouble("Total Revenue"));
////                        salesData.setTotalCost(fieldSet.readDouble("totalCost"));
////                        salesData.setTotalProfit(fieldSet.readDouble("totalProfit"));
////
////                        return salesData;
////                    }
////                });
////            }});
////        }});
////
////        return reader;
//    }


@Bean
public FlatFileItemReader<SalesEntity> reader() {
    try {
        // Create a FlatFileItemReader using the builder
        FlatFileItemReader<SalesEntity> reader = new FlatFileItemReaderBuilder<SalesEntity>()
                .name("salesItemReader")
                .resource(new ClassPathResource("10000 Sales Records.csv"))
                .delimited()
                .names("Region", "Country", "Item Type", "Sales Channel", "Order Priority", "Order Date", "Order ID", "Ship Date",
                        "Units Sold", "Unit Price", "Unit Cost", "Total Revenue", "Total Cost", "Total Profit")
                .linesToSkip(1)  // Skipping the header row
                .strict(false)   // Allowing extra columns in CSV
                .fieldSetMapper(fieldSet -> {
                    SalesEntity salesEntity = new SalesEntity();
                    salesEntity.setRegion(fieldSet.readString("Region"));
                    salesEntity.setCountry(fieldSet.readString("Country"));
                    salesEntity.setItemType(fieldSet.readString("Item Type"));
                    salesEntity.setSalesChannel(fieldSet.readString("Sales Channel"));
                    salesEntity.setOrderPriority(fieldSet.readString("Order Priority"));
                    salesEntity.setOrderId(fieldSet.readString("Order ID"));

                    // Parsing date manually (if the format is MM/dd/yyyy)
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
                    salesEntity.setOrderDate(LocalDate.parse(fieldSet.readString("Order Date"), formatter));
                    salesEntity.setShipDate(LocalDate.parse(fieldSet.readString("Ship Date"), formatter));

                    salesEntity.setUnitsSold(fieldSet.readLong("Units Sold"));
                    salesEntity.setUnitPrice(fieldSet.readDouble("Unit Price"));
                    salesEntity.setUnitCost(fieldSet.readDouble("Unit Cost"));
                    salesEntity.setTotalRevenue(fieldSet.readDouble("Total Revenue"));
                    salesEntity.setTotalCost(fieldSet.readDouble("Total Cost"));
                    salesEntity.setTotalProfit(fieldSet.readDouble("Total Profit"));

                    return salesEntity;
                })
                .build();

        return reader;
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
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
    public PlatformTransactionManager transactionManager(){
       return new JpaTransactionManager(entityManagerFactory);
    }


    @Bean
    public Step step1(JobRepository jobRepository,
                      FlatFileItemReader<SalesEntity> reader, SalesItemProcessor processor,
                      JpaItemWriter<SalesEntity> writer) {
        return new StepBuilder("step1", jobRepository)
                .<SalesEntity, SalesEntity>chunk(50, transactionManager())
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }


//    @Bean
//    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

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
                .incrementer(new RunIdIncrementer())

                .start(step1)
                .build();
    }

}
