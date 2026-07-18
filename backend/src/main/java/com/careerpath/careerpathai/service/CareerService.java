package com.careerpath.careerpathai.service;

import com.careerpath.careerpathai.entity.Career;

import java.util.List;

public interface CareerService {

    List<Career> getAllCareers();

    Career getCareerById(Integer id);

    Career createCareer(Career career);

    Career updateCareer(Integer id, Career career);

    void deleteCareer(Integer id);

    List<Career> searchCareersByTitle(String title);
}