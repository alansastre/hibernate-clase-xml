package com.example.util;

import com.example.model.Project;
import com.example.model.ProjectType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


public class EnumTest {

    @Test
    @DisplayName("checkProjectTypeEnum")
    public void checkProjectTypeEnum() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Project project1 = new Project("Project 1","PRJ1","Lorem ipsum dolor",ProjectType.SMALL);
        Project project2 = new Project("Project 1","PRJ1","Lorem ipsum dolor",ProjectType.MEDIUM);
        Project project3 = new Project("Project 1","PRJ1","Lorem ipsum dolor",ProjectType.LARGE);


        session.beginTransaction();

        session.save(project1);
        session.save(project2);
        session.save(project3);

        session.getTransaction().commit();



    }








}
