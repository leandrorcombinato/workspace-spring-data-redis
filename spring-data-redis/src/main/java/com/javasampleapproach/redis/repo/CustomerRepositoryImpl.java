package com.javasampleapproach.redis.repo;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.javasampleapproach.redis.model.Customer;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	private static final String KEY = "Customer";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	@Resource(name="redisTemplate")
	private HashOperations<String, Long, Customer> hashOperations;

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Customer customer) {
		hashOperations.put(KEY, customer.getId(), customer);
	}

	@Override
	public Customer find(Long id) {
		return hashOperations.get(KEY, id);
	}

	@Override
	public Map<Long, Customer> findAll() {
		return hashOperations.entries(KEY);
	}

	@Override
	public void update(Customer customer) {
		hashOperations.put(KEY, customer.getId(), customer);
	}

	@Override
	public void delete(Long id) {
		hashOperations.delete(KEY, id);
	}

}
