package edu.uh.tech.cis3368.finalexam;

import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface MouseRepository extends CrudRepository<Mouse,Integer> {
    ArrayList<Mouse> findAllByCatId(int id);

}
