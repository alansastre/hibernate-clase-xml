package com.example.util;

import com.example.model.Employee;
import com.example.model.Project;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class ManyToManyTest {


    @Test
    @DisplayName("createProjects")
    public void createProjects() {




        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();


        // session.createQuery("from Employee e where e.projects")

        Project project1 = new Project("Proyecto 1", "PRJ1", "Proyecto Fin de curso Frontend");
        Project project2 = new Project("Proyecto 2", "PRJ2", "Proyecto Fin de curso Backend");
        Project project3 = new Project("Proyecto 3", "PRJ3", "Proyecto Fin de curso Full stack");


        Employee employee1 = new Employee("Employee 1", 33, true, 30000D, Instant.now());
        Employee employee2 = new Employee("Employee 2", 33, true, 30000D, Instant.now());


        employee1.getProjects().add(project1);
        employee1.getProjects().add(project2);
        employee1.getProjects().add(project3);

        employee2.getProjects().add(project2);

        session.save(project1);
        session.save(project2);

        session.save(project3);

        session.save(employee1);
        session.save(employee2);

        session.getTransaction().commit();



    }
}
