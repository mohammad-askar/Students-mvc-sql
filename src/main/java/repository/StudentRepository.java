package repository;

import model.StudentModel;

import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<StudentModel> findAll();
    Optional<StudentModel> findById(int id);
    List<StudentModel> findByName(String name);
    StudentModel save(StudentModel student);
    boolean deleteById(int id);
}
