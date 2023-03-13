package com.demo.unittesting.repository;

import com.demo.unittesting.model.Fighter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author itsol.trung.nt
 * all
 * Friday. 10 Mar 2023
 */
@Repository
public interface FighterRepository extends JpaRepository<Fighter, Integer> {
    @Query(value = "select case when count(f) > 0 " +
            "then true else false end " +
            "from Fighter f where f.age = ?1")
    Boolean isExistAge(int age);
}
