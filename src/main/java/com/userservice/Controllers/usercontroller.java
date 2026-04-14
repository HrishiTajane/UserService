package com.userservice.Controllers;

import java.util.List;

import org.apache.jasper.compiler.NewlineReductionServletWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userservice.Entities.Users;
import com.userservice.Services.UserService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class usercontroller {
	
	@Autowired
	private UserService userService;
	
	
//	create 

	@PostMapping("/create")
	public ResponseEntity<Users> ceateUser(@RequestBody Users user){
		Users user1=userService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	
	
//	Single User get
	int retryCount;
	@GetMapping("/{id}")
//	@CircuitBreaker(name= "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//	@Retry(name= "ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter", fallbackMethod= "ratingHotelFallback")
	public ResponseEntity<Users> getSingleUser(@PathVariable String id){
		System.out.println("Retry Count="+retryCount);
		retryCount++;
		Users user1=userService.getUser(id); 		
		return ResponseEntity.ok(user1);
	}

//	creating fallback method for circuit breaker
	public ResponseEntity<Users> ratingHotelFallback(String userId, Exception ex){
		System.out.println("fallback is executed because servise is down"+ ex);
		Users user=new Users();
		user.setName("dummy");
		user.setEmail("dummyEmail@gmail.com");
		user.setAbout("This user is created dummy because some services is down");
		return new ResponseEntity<>(user,HttpStatus.OK); 
		
	}
	
	
//	Alluser Get
    @GetMapping("/allUsers")
    public ResponseEntity<List<Users>> getAllUsers(){
    	List<Users> users=userService.getAllUsers();
    	return ResponseEntity.ok(users);
    }
    
}
