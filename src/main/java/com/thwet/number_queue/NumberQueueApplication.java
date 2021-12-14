package com.thwet.number_queue;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import com.thwet.number_queue.service.QueueService;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class NumberQueueApplication implements CommandLineRunner, SchedulingConfigurer {

	@Autowired
	private QueueService queueService;

	public static void main(String[] args) {
		SpringApplication.run(NumberQueueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {		
		queueService.initialQueue();
	}

	private String cronConfig() {
		String cronTabExpression = "* */30 * * * *";

		return cronTabExpression;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.addTriggerTask(new Runnable() {
			@Override
			public void run() {
				// Don't delete can be use
				
				/*int value = 0;
				List<Queue> queues = queueService.findLastValue();
				Queue queue = null;
				if (queues.size() != 0) {
					queue = queues.get(0);
					value = queue.getValue();
				}
				
				System.out.println("Queue Value....." + value);
				queueService.queueDisplay();
				queueService.queueDequeue();
				queueService.queueEnqueue(value+1);
				System.out.println("\n");
				queueService.queueDisplay();*/
				
			}
		}, new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				String cron = cronConfig();
				System.out.println("In Trigger...." + cron);
				CronTrigger trigger = new CronTrigger(cron);
				Date nextExec = trigger.nextExecutionTime(triggerContext);
				return nextExec;
			}
		});
	}

}
