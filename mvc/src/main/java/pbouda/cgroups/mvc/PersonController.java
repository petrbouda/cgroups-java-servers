package pbouda.cgroups.mvc;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pbouda.cgroups.common.Helpers;
import pbouda.cgroups.mvc.repositories.PersonRepository;

import java.util.List;

@RestController
@RequestMapping(path = "/persons", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/single")
    public Person single() {
        return repository.getOne(Helpers.generateId());
    }

    @GetMapping("/multi/{count}")
    public List<Person> multi(@PathVariable("count") int count) {
        return repository.findAllById(Helpers.generateIds(count));
    }
}
