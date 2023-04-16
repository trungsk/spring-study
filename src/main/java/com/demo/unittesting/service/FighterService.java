package com.demo.unittesting.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.unittesting.model.Fighter;
import com.demo.unittesting.repository.FighterRepository;

import lombok.AllArgsConstructor;

/**
 * @author itsol.trung.nt
 * all
 * Friday. 10 Mar 2023
 */
@AllArgsConstructor
@Service
public class FighterService {
	@Autowired
    private FighterRepository fighterRepository;
    
	public List<Fighter> getAllFighter(){
        return fighterRepository.findAll();
    }
    public void addFighter(Fighter fighter){
        fighterRepository.save(fighter);
    }
    public void deleteFighter(Integer id){
        fighterRepository.deleteById(id);
    }

}
