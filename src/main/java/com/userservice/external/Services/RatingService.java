package com.userservice.external.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.userservice.Entities.Rating;

@FeignClient(name = "RatingService")
public interface RatingService {

	
	@PostMapping("/rating/create")
	void createRating(Rating values);
}
