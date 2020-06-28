package com.strike.strijkatelier.test;

import com.strike.strijkatelier.model.*;
import com.strike.strijkatelier.service.*;
import org.junit.*;
import org.junit.rules.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.data.rest.webmvc.*;
import org.springframework.test.context.junit4.*;

import javax.sql.*;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StrijkatelierApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BucketService bucketService;


	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Test
	public void testSaveUpdateDeleteBucket() throws Exception{
		Bucket c = new Bucket(new ArrayList<Item>(),true);

		c.setActive(true);
		c.setPrice(3.51);
		bucketService.save(c);
		assertNotNull(c.getId());

		Bucket findBucket = bucketService.findById(c.getId());
		assertEquals(3.51, findBucket.getPrice());
		assertTrue( findBucket.isActive());

		// update record
		c.setPrice(5.22);
		c.setCash(false);
		bucketService.update(c);

		// test after update
		findBucket = bucketService.findById(c.getId());
		assertEquals(5.22, findBucket.getPrice());
		assertFalse( findBucket.isActive());
		// test delete
		bucketService.deleteById(c.getId());

		// query after delete
		exceptionRule.expect(ResourceNotFoundException.class);
		bucketService.findById(c.getId());
	}
}
