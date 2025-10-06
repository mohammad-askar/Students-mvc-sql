package app;

import controller.AppController;
import db.DataSourceFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.JdbcStudentRepository;
import service.StudentService;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        var url = App.class.getResource("/ui/StudentView.fxml");
        if(url == null){
            throw new IllegalArgumentException("Fxml no found!");
        }
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        StudentViewController studentViewController = loader.getController();

        var repo = new JdbcStudentRepository();
        var service = new StudentService(repo);
        var appController = new AppController(service);
        studentViewController.setAppController(appController);
        stage.setScene(new Scene(root, 820, 480));
        stage.setTitle("Students");
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        DataSourceFactory.close();
    }

    public static void main(String[] args) {
        launch();
    }
}
