package com.training.dv.dao;

import com.training.dv.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private static final String SQL_INDEX = "SELECT * FROM Person";
    private static final String SQL_SHOW = "SELECT * FROM person WHERE id = ?";
    private static final String SQL_SAVE = "INSERT INTO person (name, age, email, address) values (?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM Person WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE person SET  name = ?, age = ?, email = ?, address = ? WHERE id = ?";
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query(SQL_INDEX, new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String email){
        return (jdbcTemplate.query("SELECT * FROM person WHERE email = ?", new BeanPropertyRowMapper<>(Person.class),
                email))
                .stream().findAny();
    }
    public Person show(int id) {
        return (jdbcTemplate.query(SQL_SHOW, new BeanPropertyRowMapper<>(Person.class), id))
                .stream().findAny().orElse(new Person(-1, "", 0, ""));
    }

    public void save(Person person) {
        jdbcTemplate.update(SQL_SAVE, person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void delete(int id) {
        jdbcTemplate.update(SQL_DELETE, id);
    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update(SQL_UPDATE, updatedPerson.getName(), updatedPerson.getAge(), updatedPerson.getEmail(),
                updatedPerson.getAddress(), id);
    }

    ////////////////
    ///Test
    ///////////////
    public void testMultipleUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        for (Person person:
             people) {
            save(person);
        }
        long after = System.currentTimeMillis();
        System.out.println("Time multi: " + (after - before));
    }

    public void testBatchUpdate(){
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();

        jdbcTemplate.batchUpdate("INSERT INTO person (name, age, email) VALUES (?,?,?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, people.get(i).getName());
                        ps.setInt(2, people.get(i).getAge());
                        ps.setString(3, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
        long after = System.currentTimeMillis();
        System.out.println("Time batch: " + (after - before));
    }
    private List<Person> create1000People(){
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person("Name" + i, 30, "blabla" + i + "@gm.com" , "some address"));
        }
        return people;
    }
}
