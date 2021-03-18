package com.example.util;

import com.example.model.Project;
import com.example.model.ProjectType;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ElementCollectionTest {


    @Test
    @DisplayName("Check Element Collection String")
    public void checkElementCollectionString() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Project project1 = new Project("Project 1","PRJ1","Lorem ipsum dolor", ProjectType.SMALL);

        project1.getTags().add("Backend");
        project1.getTags().add("Database");
        project1.getTags().add("MySQL");

        session.beginTransaction();

        session.save(project1);

        session.getTransaction().commit();

    }

    @Test
    @DisplayName("Check Element Collection Enum")
    public void checkElementCollectionEnum() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Project project1 = new Project("Project 1","PRJ1","Lorem ipsum dolor", ProjectType.SMALL);

        project1.getProjectTypes().add(ProjectType.LARGE);
        project1.getProjectTypes().add(ProjectType.SMALL);
        project1.getProjectTypes().add(ProjectType.MEDIUM);

        session.beginTransaction();

        session.save(project1);

        session.getTransaction().commit();

    }









}
