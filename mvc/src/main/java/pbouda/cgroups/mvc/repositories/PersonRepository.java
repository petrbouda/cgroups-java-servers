package pbouda.cgroups.mvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pbouda.cgroups.mvc.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}