package com.example.util;

import com.example.model.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.Query;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HibernateUtilTest {


    @Test
    @DisplayName("Recuperar todos los empleados")
    public void queryAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Employee> employees = session.createQuery("from Employee", Employee.class ).list();

        assertEquals(1, employees.size());
        employees.forEach(e -> assertNotNull(e.getName()));

        session.close();
    }

    /**
     * Hibernate no realizar un segundo select
     */
    @Test
    @DisplayName("Recuperar dos veces con caché")
    public void recuperarUnoConCache() {

        // Option 1
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.find(Employee.class, 1L);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Mike", employee.getName());

        // Option 2
        employee = session.find(Employee.class, 1L);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Mike", employee.getName());


        session.close();
    }

    
    @Test
    @DisplayName("Recuperar un empleado por id")
    public void recuperarUnoSinCache() {

        // Option 1
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = session.find(Employee.class, 1L);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Mike", employee.getName());
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();
        // Option 2
        employee = session.find(Employee.class, 1L);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Mike", employee.getName());


        session.close();
    }

    @Test
    @DisplayName("RecuperarUnoPorParametro")
    public void recuperarUnoPorParametro() {

        Session session = HibernateUtil.getSessionFactory().openSession();


        String hql = "from Employee where id = :idEmpleado"; // named parameters
        Query query = session.createQuery(hql);
        query.setParameter("idEmpleado", 1L);
        Employee employee = (Employee) query.getSingleResult();
        System.out.println(employee);
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Mike", employee.getName());

        session.close();
        // HibernateUtil.shutdown();
    }

    /**
     *  CRUD
     *  RETRIEVE
     *  Asociaciones
     */

    @Test
    @DisplayName("Filter by position parameter")
    public void filterByPositionParameter() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = "from Employee where id = ?1 and age < ?2"; // position parameters
        Query query = session.createQuery(hql);
        query.setParameter(1, 1L);
        query.setParameter(2, 30);
        Employee employee = (Employee) query.getSingleResult();
        assertNotNull(employee);
        assertEquals(1L, employee.getId());
        assertEquals("Mike", employee.getName());
        System.out.println(employee);
        session.close();
    }

    @Test
    @DisplayName("Create one employee")
    public void createOneEmployee() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee1 = new Employee("Juan",25,true,30000.0D, Instant.now());

        session.beginTransaction();
        System.out.println(employee1);
        // guardamos
        session.save(employee1);
        // session.detach(employee1);
        employee1.setName("Juan editado");
        System.out.println(employee1);
        session.getTransaction().commit();

        System.out.println(employee1);
        session.close();
    }


    @Test
    @DisplayName("Update One")
    public void updateOne() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Employee employeeDB = session.find(Employee.class, 1L);

         session.beginTransaction();



         session.save(employeeDB);

         session.getTransaction().commit();
        session.close();
        employeeDB.setName(employeeDB.getName() + " Editado");

        // session.save(employeeDB);

        System.out.println(employeeDB);

    }

    @Test
    @DisplayName("Delete one")
    public void deleteOne() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Employee employeeDB = session.find(Employee.class, 1L);

        session.beginTransaction();

        session.delete(employeeDB);
        System.out.println(employeeDB);


        session.getTransaction().commit();

        System.out.println(employeeDB);

    }




}