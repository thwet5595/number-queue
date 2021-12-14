/**
 * 
 */
package com.thwet.number_queue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thwet.number_queue.model.Queue;
import com.thwet.number_queue.service.QueueService;

/**
 * @author Thwet Thwet Mar
 *
 *         Dec 14, 2021
 */
@RequestMapping("api")
@RestController
public class ApiController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	private QueueService queueService;

	@GetMapping("/number/created")
	public Map<String, String> getCreatedNumbers() {

		LOGGER.info("Inside getCreatedNumbers");
		int value = 0;
		List<Queue> queues = queueService.findLastValue();
		Queue queue = null;
		if (queues.size() != 0) {
			queue = queues.get(0);
			value = queue.getValue();
		}
		
		LOGGER.info("Queue Value....." + value);
		//queueService.queueDisplay();
		String result = queueService.queueDisplay();
		queueService.queueDequeue();
		queueService.queueEnqueue(value+1);
		LOGGER.info("Queue Result ....." + result);
		
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("result", result);

		return resultMap;
	}
}
