package com.userservice.Services.Impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.userservice.Entities.Hotel;
import com.userservice.Entities.Rating;
import com.userservice.Entities.Users;
import com.userservice.Exceptions.ResourceNotFoundException;
import com.userservice.Repositories.userRepository;
import com.userservice.Services.UserService;
import com.userservice.external.Services.HotelService;
import com.userservice.external.Services.RatingService;

@Service
public class UserServiceimpl implements UserService{

	@Autowired
	private userRepository userrepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	
	@Override
	public Users saveUser(Users user) {
		String randomUserId=UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userrepo.save(user);
	}

	@Override
	public List<Users> getAllUsers() {
		return userrepo.findAll();
	}

	@Override
	public Users getUser(String userId) {
//		get user from database with the help of user repository
		Users user= userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id not found on server !!" + userId));

//	    fetch rating of the user from Rating Service		
		ResponseEntity<List<Rating>> response = restTemplate.exchange(
			    "http://RatingService/rating/users/"+user.getUserId(),
			    HttpMethod.GET,
			    null,
			    new ParameterizedTypeReference<List<Rating>>() {}
			);
			List<Rating> ratings = response.getBody();			
			
			List<Rating> ratingList=ratings.stream().map(ratings1 ->{	
//				Fetch Hotel from Hotel Service
//				ResponseEntity<Hotel> forEntity=restTemplate.exchange(
//						"http://HotelService/hotels/"+ratings1.getHotelId(), 
//                         HttpMethod.GET,
//                         null,
//                         new  ParameterizedTypeReference<Hotel>() {}
//						);
//			   Hotel hotel = forEntity.getBody();
				
				Hotel hotel=hotelService.getHotel(ratings1.getHotelId());
				
				
				ratings1.setHotel(hotel);

				return ratings1;
			}).collect(Collectors.toList());
			
		user.setRating(ratingList);			
		return user;
	}

}
