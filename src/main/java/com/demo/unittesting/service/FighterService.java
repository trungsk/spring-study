package com.demo.unittesting.service;

import com.demo.unittesting.model.Fighter;
import com.demo.unittesting.repository.FighterRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author itsol.trung.nt
 * all
 * Friday. 10 Mar 2023
 */
@AllArgsConstructor
@Service
public class FighterService {
    private final FighterRepository fighterRepository;

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
