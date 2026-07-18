package com.careerpath.careerpathai.service.impl;

import com.careerpath.careerpathai.entity.Career;
import com.careerpath.careerpathai.exception.CareerAlreadyExistsException;
import com.careerpath.careerpathai.exception.CareerNotFoundException;
import com.careerpath.careerpathai.repository.CareerRepository;
import com.careerpath.careerpathai.service.CareerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerServiceImpl implements CareerService {

    private final CareerRepository careerRepository;

    public CareerServiceImpl(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    @Override
    public List<Career> getAllCareers() {
        return careerRepository.findAll();
    }

    @Override
    public Career getCareerById(Integer id) {
        return careerRepository.findById(id).orElseThrow(() ->
                        new CareerNotFoundException("Career with id " + id + " was not found."
                        )
                );
    }

    @Override
    public Career createCareer(Career career) {

        if (careerRepository.existsByTitle(career.getTitle())) {
            throw new CareerAlreadyExistsException("Career " + career.getTitle() + " already exists."
            );
        }

        return careerRepository.save(career);
    }

    @Override
    public Career updateCareer(Integer id, Career career) {

        Career existingCareer = careerRepository.findById(id)
                .orElseThrow(() -> new CareerNotFoundException(
                                "Career with id " + id + " was not found."
                        )
                );

        if (careerRepository.existsByTitleAndIdNot(career.getTitle(), id)) {
            throw new CareerAlreadyExistsException(
                    "Another career with title " +
                            career.getTitle() + " already exists."
            );
        }

        existingCareer.setTitle(career.getTitle());
        existingCareer.setDescription(career.getDescription());
        existingCareer.setCategory(career.getCategory());
        existingCareer.setAverageSalary(career.getAverageSalary());
        existingCareer.setDemandLevel(career.getDemandLevel());

        return careerRepository.save(existingCareer);
    }

    @Override
    public void deleteCareer(Integer id) {

        Career career = careerRepository.findById(id)
                .orElseThrow(() ->
                        new CareerNotFoundException(
                                "Career with id " + id + " was not found."
                        )
                );

        careerRepository.delete(career);
    }

    @Override
    public List<Career> searchCareersByTitle(String title) {
        return careerRepository.findByTitleContainingIgnoreCase(title);
    }
}