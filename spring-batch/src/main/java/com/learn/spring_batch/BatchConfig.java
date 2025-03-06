package com.learn.spring_batch;

import com.learn.spring_batch.elasticserach.entities.ProductEntity;
import com.learn.spring_batch.elasticserach.repositories.ProductRepo;
import com.learn.spring_batch.processer.ProductItemProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class BatchConfig {

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private ProductItemProcessor productItemProcessor;

//    @Autowired
//    private ProductWriter productWriter;

    @Autowired
    private  DataSource dataSource;

    @Autowired
    ProductRepo productRepo;


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

    /// /        FlatFileItemReader<SalesEntity> reader = new FlatFileItemReader<>();
    /// /        reader.setResource(new ClassPathResource("10000 Sales Records.csv"));
    /// /
    /// /        // Define date format for orderDate and shipDate
    /// /        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
    /// /
    /// /        // Custom FieldSetMapper to handle LocalDate
    /// /        reader.setLineMapper(new DefaultLineMapper<SalesEntity>() {{
    /// /            setLineTokenizer(new DelimitedLineTokenizer() {{
    /// /                setNames("Region","Country","Item Type",
    /// /                            "Sales Channel","Order Priority","Order Date","Order ID","Ship Date","Units Sold"
    /// /                            ,"Unit Price","Unit Cost","Total Revenue","Total Cost","Total Profit");
    /// /            }});
    /// /
    /// /            setFieldSetMapper(new BeanWrapperFieldSetMapper<SalesEntity>() {{
    /// /                setTargetType(SalesEntity.class);
    /// /                setFieldSetMapper(new FieldSetMapper<SalesEntity>() {
    /// /                    @Override
    /// /                    public SalesEntity mapFieldSet(FieldSet fieldSet) {
    /// /                        SalesEntity salesData = new SalesEntity();
    /// /                        salesData.setRegion(fieldSet.readString("Region"));
    /// /                        salesData.setCountry(fieldSet.readString("Country"));
    /// /                        salesData.setItemType(fieldSet.readString("Item Type"));
    /// /                        salesData.setSalesChannel(fieldSet.readString("Sales Channel"));
    /// /                        salesData.setOrderPriority(fieldSet.readString("Order Priority"));
    /// /
    /// /                        // Parse orderDate and shipDate from string to LocalDate
    /// /                        salesData.setOrderDate(LocalDate.parse(fieldSet.readString("Order Date"), formatter));
    /// /                        salesData.setShipDate(LocalDate.parse(fieldSet.readString("shipDate"), formatter));
    /// /
    /// /                        salesData.setUnitsSold((long) fieldSet.readInt("Units Sold"));
    /// /                        salesData.setUnitPrice(fieldSet.readDouble("Unit Price"));
    /// /                        salesData.setUnitCost(fieldSet.readDouble("Unit Cost"));
    /// /                        salesData.setTotalRevenue(fieldSet.readDouble("Total Revenue"));
    /// /                        salesData.setTotalCost(fieldSet.readDouble("totalCost"));
    /// /                        salesData.setTotalProfit(fieldSet.readDouble("totalProfit"));
    /// /
    /// /                        return salesData;
    /// /                    }
    /// /                });
    /// /            }});
    /// /        }});
    /// /
    /// /        return reader;
//    }


//@Bean
//public FlatFileItemReader<SalesEntity> reader() {
//    try {
//        // Create a FlatFileItemReader using the builder
//        FlatFileItemReader<SalesEntity> reader = new FlatFileItemReaderBuilder<SalesEntity>()
//                .name("salesItemReader")
//                .resource(new ClassPathResource("1000000 Sales Records.csv"))
//                .delimited()
//                .names("Region", "Country", "Item Type", "Sales Channel", "Order Priority", "Order Date", "Order ID", "Ship Date",
//                        "Units Sold", "Unit Price", "Unit Cost", "Total Revenue", "Total Cost", "Total Profit")
//                .linesToSkip(1)  // Skipping the header row
//                .strict(false)   // Allowing extra columns in CSV
//                .fieldSetMapper(fieldSet -> {
//                    SalesEntity salesEntity = new SalesEntity();
//                    salesEntity.setRegion(fieldSet.readString("Region"));
//                    salesEntity.setCountry(fieldSet.readString("Country"));
//                    salesEntity.setItemType(fieldSet.readString("Item Type"));
//                    salesEntity.setSalesChannel(fieldSet.readString("Sales Channel"));
//                    salesEntity.setOrderPriority(fieldSet.readString("Order Priority"));
//                    salesEntity.setOrderId(fieldSet.readString("Order ID"));
//
//                    // Parsing date manually (if the format is MM/dd/yyyy)
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//                    salesEntity.setOrderDate(LocalDate.parse(fieldSet.readString("Order Date"), formatter));
//                    salesEntity.setShipDate(LocalDate.parse(fieldSet.readString("Ship Date"), formatter));
//
//                    salesEntity.setUnitsSold(fieldSet.readLong("Units Sold"));
//                    salesEntity.setUnitPrice(fieldSet.readDouble("Unit Price"));
//                    salesEntity.setUnitCost(fieldSet.readDouble("Unit Cost"));
//                    salesEntity.setTotalRevenue(fieldSet.readDouble("Total Revenue"));
//                    salesEntity.setTotalCost(fieldSet.readDouble("Total Cost"));
//                    salesEntity.setTotalProfit(fieldSet.readDouble("Total Profit"));
//
//                    return salesEntity;
//                })
//                .build();
//
//        return reader;
//    } catch (Exception e) {
//        e.printStackTrace();
//        throw new RuntimeException(e);
//    }
//}
//    @Bean
//    public FlatFileItemReader<Sale> reader() {
//        try {
//            // Define date formatter for parsing Order Date and Ship Date
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//
//            // Build the FlatFileItemReader
//            return new FlatFileItemReaderBuilder<Sale>()
//                    .name("salesItemReader")
//                    .resource(new ClassPathResource("1000000 Sales Records.csv"))
//                    .delimited()
////                    .names("Region", "Country", "Item Type", "Sales Channel", "Order Priority", "Order Date",
////                            "Order ID", "Ship Date", "Units Sold", "Unit Price", "Unit Cost",
////                            "Total Revenue", "Total Cost", "Total Profit")
//                    .names("name","description","price")
//                    .linesToSkip(1) // Skip header row
//                    .strict(false)  // Allow malformed lines
//                    .fieldSetMapper(fieldSet -> {
//                        Sale sale = new Sale();
//                        try {
//                            // Map CSV fields to SalesEntity properties matching the writer's SQL
//                            sale.setRegion(fieldSet.readString("Region"));
//                            sale.setCountry(fieldSet.readString("Country"));
//                            sale.setItemType(fieldSet.readString("Item Type"));
//                            sale.setSalesChannel(fieldSet.readString("Sales Channel"));
//                            sale.setOrderPriority(fieldSet.readString("Order Priority"));
//                            sale.setOrderDate(LocalDate.parse(fieldSet.readString("Order Date"), formatter));
//                            sale.setOrderId(fieldSet.readString("Order ID"));
//                            sale.setShipDate(LocalDate.parse(fieldSet.readString("Ship Date"), formatter));
//                            sale.setUnitsSold(fieldSet.readLong("Units Sold"));
//                            sale.setUnitPrice(fieldSet.readDouble("Unit Price"));
//                            sale.setUnitCost(fieldSet.readDouble("Unit Cost"));
//                            sale.setTotalRevenue(fieldSet.readDouble("Total Revenue"));
//                            sale.setTotalCost(fieldSet.readDouble("Total Cost"));
//                            sale.setTotalProfit(fieldSet.readDouble("Total Profit"));
//                        } catch (Exception e) {
//                            log.error("Error mapping CSV row to SalesEntity: {}", fieldSet, e);
//                            return null; // Skip invalid rows (processed by fault tolerance if configured)
//                        }
//                        return sale;
//                    })
//                    .build();
//        } catch (Exception e) {
//            log.error("Failed to initialize FlatFileItemReader", e);
//            throw new RuntimeException("Error initializing reader", e);
//        }
//    }


//    @Bean
//    public SalesItemProcessor processor() {
//        return new SalesItemProcessor();
//    }


//   @Bean
//    public ProductItemProcessor productProcesser(){
//       return new ProductItemProcessor();
//    }

//    @Bean
//    public JpaItemWriter<SalesEntity> writer(EntityManagerFactory entityManagerFactory) {
//        JpaItemWriter<SalesEntity> writer = new JpaItemWriter<>();
//        writer.setEntityManagerFactory(entityManagerFactory);
//
//        return writer;
//    }


//    @Bean
//    public JdbcBatchItemWriter<Sale> writer(DataSource dataSource) {
//        JdbcBatchItemWriter<Sale> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(dataSource);
//        writer.setSql("INSERT INTO sales (region, country, item_type, sales_channel, order_priority, order_date, " +
//                "order_id, shipe_date, unit_solid, unit_price, unit_cost, total_revenue, total_cost, total_profit) " +
//                "VALUES (:region, :country, :itemType, :salesChannel, :orderPriority, :orderDate, :orderId, :shipDate, :unitsSold, :unitPrice, :unitCost, :totalRevenue, :totalCost, :totalProfit)");
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
//        return writer;
//    }


//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager(entityManagerFactory);
//    }


//    @Bean
//    public Step step1(JobRepository jobRepository,
//                      FlatFileItemReader<Sale> reader, SalesItemProcessor processor,
//                      JdbcBatchItemWriter<Sale> writer) {
//        log.info("reader::::::{}", reader);
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        asyncTaskExecutor.setConcurrencyLimit(500); // Max 500 concurrent tasks
//        asyncTaskExecutor.setThreadNamePrefix("job-thread-");
//        return new StepBuilder("step1", jobRepository)
//                .<Sale, Sale>chunk(5000, transactionManager())
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .taskExecutor(taskExecutor())
//
//                .build();
//    }


//    @Bean
//    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1000); // Number of core threads
        executor.setMaxPoolSize(1000);  // Maximum number of threads
        executor.setQueueCapacity(10000); // Queue size before new threads are created
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


    @Bean
    public ItemWriter<ProductEntity> writer() {

        return items -> productRepo.saveAll(items); // Bulk save to Elasticsearch
    }



       //elastic serach
        @Bean
        public FlatFileItemReader<ProductEntity> productItemReader() {
            return new FlatFileItemReaderBuilder<ProductEntity>()
                    .name("saleItemReader")
                    .resource(new ClassPathResource("product.csv"))
                    .linesToSkip(1) // Skip header row
                    .delimited()
                    .names("name","description","price")
                    .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                        setTargetType(ProductEntity.class); // Auto maps CSV columns to Sale fields
                    }})
                    .build();
        }


        // elastic search

      //  @Bean
//    public Step step1(JobRepository jobRepository,
//                      FlatFileItemReader<ProductEntity> reader , ProductItemProcessor processor,
//                      ProductItemProcessor writer) {
//        log.info("reader::::::{}", reader);
//        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
//        asyncTaskExecutor.setConcurrencyLimit(500); // Max 500 concurrent tasks
//        asyncTaskExecutor.setThreadNamePrefix("job-thread-");
//        return new StepBuilder("step1", jobRepository)
//                .<Sale, Sale>chunk(5000, transactionManager())
//                .reader(productItemReader())
//                .processor(processor)
//                .writer(writer)
//                .taskExecutor(taskExecutor())
//
//                .build();
//    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new NoOpTransactionManager();
    }

    @Bean
    public Step step1(JobRepository jobRepository, FlatFileItemReader<ProductEntity> reader ,
                     PlatformTransactionManager platformTransactionManager){


    return new StepBuilder("step2", jobRepository)
            .<ProductEntity, ProductEntity> chunk(500,platformTransactionManager)
            .reader(reader)
    //  .processor(processor)
            .writer(writer())
            .build();

    }

    class NoOpTransactionManager extends AbstractPlatformTransactionManager {
        @Override
        protected Object doGetTransaction() { return new Object(); }
        @Override
        protected void doBegin(Object transaction, TransactionDefinition definition) {}
        @Override
        protected void doCommit(DefaultTransactionStatus status) {}
        @Override
        protected void doRollback(DefaultTransactionStatus status) {}
    }


}
