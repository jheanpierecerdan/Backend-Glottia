package com.glottia.backend.service;

import com.glottia.backend.entity.Interest;
import com.glottia.backend.repository.InterestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestService {

    @Autowired
    private InterestRepository interestRepository;

    public List<Interest> findAll() {
        return interestRepository.findAll();
    }

    public Optional<Interest> findById(Integer id) {
        return interestRepository.findById(id);
    }

    public Interest save(Interest interest) {
        return interestRepository.save(interest);
    }

    public void deleteById(Integer id) {
        interestRepository.deleteById(id);
    }
}
