package com.example.util;

import com.example.model.Direction;
import com.example.model.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class OneToOneTest {

    @Test
    @DisplayName("One to One 1")
    public void oneToOne1() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Employee employee = new Employee("Anthony", 32, true, 40000D, Instant.now());
        Employee employee2 = new Employee("Anthony2", 32, true, 40000D, Instant.now());

        Direction direction = new Direction("Calle falsa", "Madrid", "33021", "Spain");
        Direction direction2 = new Direction("Calle verdadera", "Madrid", "33021", "Spain");


        session.beginTransaction();

        employee.setDirection(direction);
        employee2.setDirection(direction2);

        session.save(direction);
        session.save(direction2);

        session.save(employee);
        session.save(employee2);

        session.getTransaction().commit();

        System.out.println(direction);
        System.out.println(employee);
        System.out.println(direction2);
        System.out.println(employee2);
    }


    @Test
    @DisplayName("createDirection")
    public void createDirection() {


        // 0 - Crear datos
        Session session = HibernateUtil.getSessionFactory().openSession();

        Employee employee = new Employee("Raul", 32, true, 40000D, Instant.now());
        Direction direction = new Direction("Calle Madrid", "Madrid", "33021", "Spain");

        session.beginTransaction();

        direction.setEmployee(employee);
        employee.setDirection(direction);

        session.save(employee);
        session.save(direction);

        session.getTransaction().commit();

        session.close();

        System.out.println("************************************");
        session = HibernateUtil.getSessionFactory().openSession();

        // 1 - Find employee y verificar que trae la direccion
        Employee employeeDB = session.find(Employee.class, employee.getId());
        // System.out.println(employeeDB.getDirection());


        // session.evict(employeeDB.getDirection()); // Forzar que haga de nuevo el select en database
        // 2 - Find direction y verificar que trae el empleado
        // session.evict(employeeDB);
        Direction directionDB = session.find(Direction.class, direction.getId()); // Si no hacemos el evict no lo consula, lo saca de caché
        System.out.println(directionDB.getEmployee());

        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        Employee employeeDB2 = session.find(Employee.class, employee.getId());
        System.out.println(directionDB.getEmployee());
        session.close();

    }
}
