package com.github.shotah.cauliflower;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CauliflowerApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void test(){
		double foo = 0.0;
//		System.out.println(foo / foo);
		double v = foo / foo;
		if(true == Double.isNaN(v)){
			System.out.println("-");
		}else{
			System.out.println("true");
		}
	}

}
