package service;

import model.StudentModel;
import repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentRepository repo;

    public StudentService(final StudentRepository repo){
        this.repo = repo;
    }

    public List<StudentModel> findAll(){
        return repo.findAll();
    }

    public Optional<StudentModel> findById(final int id){
        return repo.findById(id);
    }

    public List<StudentModel> findByName(final String name){
        if(name == null || name.isBlank() || name.length() > 50) return List.of();//""
        List<StudentModel> list = repo.findByName(name.trim());// "mohammad " -> "mohammad";

        return list;
    }

    public StudentModel add(final String name){
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name is Empty ");
        if(name.length() > 50) throw new IllegalArgumentException("max(50)");
        return repo.save(new StudentModel(name));
    }

    public boolean remove(final int id){
        return repo.deleteById(id);
    }
}
