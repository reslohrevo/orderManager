package com.zog3.ordermanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderManagerApplicationTests {@LocalServerPort
private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private OrdersController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void successfullySubmitOrderRequest() throws Exception {
		Order order = new Order(List.of("orange"));
		String url = "http://localhost:" + port + "/orders";
		URI uri = new URI(url);

		ResponseEntity<OrderCheckout> response = this.restTemplate.postForEntity(url, order, OrderCheckout.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	public void submitCorrectOrderProcessedWithTotal() throws Exception {
		//The request object does not communicate if the simple offer is active. The 'deals' boolean needs to be
		//toggled manually and the price in this test adjusted to match.
		Order order = new Order(List.of("orange", "orange", "orange", "orange", "orange", "orange", "orange", "orange"));
		String url = "http://localhost:" + port + "/orders";
		URI uri = new URI(url);

		ResponseEntity<OrderCheckout> response = this.restTemplate.postForEntity(url, order, OrderCheckout.class);
		assertEquals(new BigDecimal("1.50"), response.getBody().getTotal());
	}

	@Test
	public void serviceRejectsItemsWithNoPrice() throws Exception {
		//The request object does not communicate if the simple offer is active. The 'deals' boolean needs to be
		//toggled manually and the price in this test adjusted to match.
		Order order = new Order(List.of("orange", "orange", "apple", "orange", "apple", "orange", "pickle", "orange"));
		String url = "http://localhost:" + port + "/orders";
		URI uri = new URI(url);

		ResponseEntity<OrderCheckout> response = this.restTemplate.postForEntity(url, order, OrderCheckout.class);
		assertFalse(response.getBody().toString().contains("pickle"), response.getBody().getTotal().toString());
	}

}
