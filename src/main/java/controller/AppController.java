package controller;

import model.StudentModel;
import service.StudentService;

import java.util.List;
import java.util.Optional;

public class AppController {
    private final StudentService service;

    public AppController(final StudentService service){
        this.service = service;
    }

    public List<StudentModel> showAll(){
        return service.findAll();
    }

    public Optional<StudentModel> showById(final int id){
        return service.findById(id);
    }

    public List<StudentModel> showByName(final String name){
        return  service.findByName(name);
    }


    public StudentModel add(final String name){
        return service.add(name);
    }

    public boolean remove(final int id){
        return service.remove(id);
    }
}
