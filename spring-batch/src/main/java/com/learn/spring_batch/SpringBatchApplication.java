package com.learn.spring_batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SpringBatchApplication implements CommandLineRunner {

	private final JobLauncher jobLauncher;

	private final Job job;

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
			System.out.println("Job executed with status: " + jobExecution.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
