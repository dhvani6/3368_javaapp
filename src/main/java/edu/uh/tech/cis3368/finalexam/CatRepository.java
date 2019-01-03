package edu.uh.tech.cis3368.finalexam;

import org.springframework.data.repository.CrudRepository;

public interface CatRepository extends CrudRepository<Cat,Integer> {
    Cat findByName(String name);
}
