package com.training.dv.util;

import com.training.dv.dao.PersonDAO;
import com.training.dv.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        if (personDAO.show(person.getEmail()).isPresent()){
            errors.rejectValue("email","", "This email is NOT free!");
        }

    }
}
