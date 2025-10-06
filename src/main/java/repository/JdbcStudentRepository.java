package repository;

import db.DataSourceFactory;
import model.StudentModel;

import javax.sql.DataSource;
import javax.xml.stream.events.StartDocument;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcStudentRepository implements StudentRepository{
    private final DataSource ds = DataSourceFactory.get();
    private final static String TABLE_NAME = "dbo.students";
    @Override
    public List<StudentModel> findAll() {
        String sql = "SELECT * FROM "+TABLE_NAME+" ORDER BY id";
        List<StudentModel> out = new ArrayList<>();
        try(var con  = ds.getConnection();
            var ps = con.prepareStatement(sql);
            var rs = ps.executeQuery()
            ){
            while(rs.next()){
                out.add(new StudentModel(rs.getInt("id"), rs.getString("fullName")));
            }
        }catch (SQLException e){
            throw new RuntimeException("finadAll faild "+e);
        }
        return out;
    }

    @Override
    public Optional<StudentModel> findById(int id) {// id = 3;
        String sql = "SELECT *  FROM "+TABLE_NAME+" WHERE id=?";//SELECT *  FROM dbo.students WHERE id=3
        try(
                var con = ds.getConnection();
                var ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);//[]
            try (var rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(new StudentModel(rs.getInt("id"), rs.getString("fullName")));
            }
        }catch (SQLException e){
                throw new RuntimeException("findById faild ", e);
            }
        return Optional.empty();

        }


    @Override
    public List<StudentModel> findByName(String name) {
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE fullName LIKE ?";
        List<StudentModel> out = new ArrayList<>();
        try(
                var con = ds.getConnection();
                var ps = con.prepareStatement(sql)){
            ps.setString(1, name+"%");//[]
            try (var rs = ps.executeQuery()) {
                while(rs.next())
                    out.add(new StudentModel(rs.getInt("id"), rs.getString("fullName")));
            }

        }catch (SQLException e){
            throw new RuntimeException("findByName filed "+e);
        }

        return out;
    }

    @Override
    public StudentModel save(StudentModel student) {
        String sql = "INSERT INTO "+TABLE_NAME+" VALUES (?)";
        try(var con  = ds.getConnection();
            var ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, student.getName());//[]
            ps.executeUpdate();
            try(var keys = ps.getGeneratedKeys()){
                if(keys.next()) return  new StudentModel(keys.getInt(1), student.getName());
            }

        }catch (SQLException e){
            throw new RuntimeException("findByName failed "+e);
        }
        return student;
    }

    @Override
    public boolean deleteById(int id) {
        String sql = "DELETE FROM "+TABLE_NAME+" WHERE id = ?";
        try(var con = ds.getConnection();
            var ps =  con.prepareStatement(sql)){
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        }catch (SQLException e){
            throw new RuntimeException("deleteById failed "+e);
        }
    }
}
