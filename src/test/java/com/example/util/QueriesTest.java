package com.example.util;

import com.example.model.Company;
import com.example.model.Employee;
import com.example.model.Project;
import com.example.model.ProjectType;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

public class QueriesTest {

    @Test
    @DisplayName("filterByProjectType")
    public void filterByProjectType() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Project project1 = new Project("Project X","PRJ1","Lorem ipsum dolor", ProjectType.SMALL);
        Project project2 = new Project("Project Y","PRJ1","Lorem ipsum dolor",ProjectType.MEDIUM);
        Project project3 = new Project("Project Z","PRJ1","Lorem ipsum dolor",ProjectType.LARGE);

        session.beginTransaction();

        session.save(project1);
        session.save(project2);
        session.save(project3);

        session.getTransaction().commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();

        Query<Project> query = session.createQuery("select distinct p from Project p where type = :projectType", Project.class);
        query.setParameter("projectType", ProjectType.MEDIUM);
        List<Project> projects = query.list();

        System.out.println(projects);
        session.close();
    }

    @Test
    @DisplayName("queryJoin")
    public void queryJoin() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Company> query = session.createQuery("select distinct c from Company c join fetch c.employees where c.id = 1", Company.class);

        Company company = query.getSingleResult();

        System.out.println("=========");
        System.out.println(company);
        System.out.println(company.getEmployees());

    }



    // CRITERIA API

    @Test
    @DisplayName("Query All Criteria")
    public void findAllEmployees() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // Criteria Builder
        CriteriaBuilder builder = session.getCriteriaBuilder();
        // Crea el criteria
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        // se especifica la tabla raíz / origen sobre la que aplicar el criteria
        Root<Employee> root = criteria.from(Employee.class);
        // Especificar la operación a realizar
        criteria.select(root);

//        Query<Employee> query = session.createQuery(criteria);
//        List<Employee> employees = query.list();

        List<Employee> employees = session.createQuery(criteria).list();

        System.out.println(employees);

        session.close();
    }

    @Test
    @DisplayName("Query One employee by id")
    public void findOneEmployeeById() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
        Root<Company> root = criteria.from(Company.class);

        criteria.select(root);

        criteria.where(builder.equal(root.get("id"), 2L));

        // Opcion 1
        // List<Company> companies = session.createQuery(criteria).list();
        // System.out.println(companies);

        // Opcion 2
        Company company = session.createQuery(criteria).getSingleResult();
        System.out.println(company);
        session.close();
    }


    @Test
    @DisplayName("Find All Where like ")
    public void findAllWhereLike() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root);

        criteria.where(builder.like(root.get("name"), "A%"));

        List<Employee> employees = session.createQuery(criteria).list();
        employees.forEach(employee -> System.out.println(employee.getName()));
        //System.out.println(employees);

        session.close();

    }


    @Test
    @DisplayName("Find All Greater ")
    public void findAllGreater() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root);

        criteria.where(builder.gt(root.get("age"), 20));

        List<Employee> employees = session.createQuery(criteria).list();
        employees.forEach(employee -> System.out.println(employee.getName()));

        session.close();

    }

    @Test
    @DisplayName("Find All Between ")
    public void findAllBetween() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root);

        criteria.where(builder.between(root.get("age"), 20, 30));

        List<Employee> employees = session.createQuery(criteria).list();
        employees.forEach(employee -> System.out.println(employee.getName()));

        session.close();

    }





}
