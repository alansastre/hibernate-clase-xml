package com.example.util;

import com.example.model.Company;
import com.example.model.Employee;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

public class ManyToOne {


    @Test
    @DisplayName("Create Company Unidirectional")
    public void createCompanyUnidirectional() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Company company = new Company("Imagina", "B00000000");
        Employee employee = new Employee("Empleado1", 34, false, 300000.0, Instant.now());

        session.beginTransaction();

        // Persistir
        employee.setCompany(company);
        // SIN CASCADE: Sin guardar company primero ocurre: TransientObjectException
        // session.save(company);

        // con CASCADE: no es necesario guardar antes la company ya que se guarda al hacer save de employee por la cascada
        session.persist(employee); // crear
        // session.merge(); // actualizar

        session.delete(employee);

        session.getTransaction().commit();
    }

    /**
     * 2 empleados vinculados a una company
     *
     * Borro un empleado teniendo CASCADE.ALL por tanto se borra la company
     *
     * El otro empleado asociado no se borra y tiene una referencia a la company que se quiere borrar, por tanto
     * se produce una expención.
     *
     * IMPORTANTE: cascadas y borrados se han de hacer con cuidado, uso de listeners
     */
    @Test
    @DisplayName("Borrar con cascade all cuando hay más de un empleado: error ")
    public void createCompanyUnidirectional2() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Company company = new Company("Imagina", "B00000000");
        Employee employee = new Employee("Empleado1", 34, false, 300000.0, Instant.now());
        Employee employeeDB1 = session.find(Employee.class, 1L);
        session.beginTransaction();

        // Persistir
        employeeDB1.setCompany(company);
        employee.setCompany(company);
        // SIN CASCADE: Sin guardar company primero ocurre: TransientObjectException
        // session.save(company);

        // con CASCADE: no es necesario guardar antes la company ya que se guarda al hacer save de employee por la cascada
        session.persist(employee); // crear
        // session.persist(employeeDB1);
        // session.merge(); // actualizar

        session.delete(employee);

        session.getTransaction().commit();


    }

    @Test
    @DisplayName("retrieveCompany")
    public void retrieveCompany() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // create data
        Company company = new Company("Imagina", "B00000000");
        Employee employee1 = new Employee("Empleado1", 34, false, 300000.0, Instant.now());
        Employee employee2 = new Employee("Empleado2", 34, false, 300000.0, Instant.now());
        Employee employee3 = new Employee("Empleado3", 34, false, 300000.0, Instant.now());

        session.beginTransaction();

//        company.getEmployees().add(employee1);
//        company.getEmployees().add(employee2);
//        company.getEmployees().add(employee3);
        session.save(company);

        // session.flush();

        employee1.setCompany(company);
        employee2.setCompany(company);
        employee3.setCompany(company);

        // save data

        session.save(employee1);
        session.save(employee2);
        session.save(employee3);


        session.getTransaction().commit();
        session.close();

        // Recuperar company y comprobar que trae los empleados

        session = HibernateUtil.getSessionFactory().openSession();
        Company companyDB = session.find(Company.class, company.getId());
        // System.out.println(companyDB.getEmployees());

        session.close();


        session = HibernateUtil.getSessionFactory().openSession();

        // LazyInitializationException porque accedemos a una lista lazy fuera de la sesión donde se recuperó la company
        System.out.println(companyDB.getEmployees());

        session.close();



        HibernateUtil.shutdown();
    }


}
