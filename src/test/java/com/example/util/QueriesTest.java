package com.example.util;

import com.example.model.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;



//    ANGULAR O POSTMAN --> CONTROLLER --> SERVICE --> REPOSITORY  --> MYSQL/POSTGRESQL/MARIADB/H2
//                                                --> DAO
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

    @Test
    @DisplayName("Multi select")
    public void multiSelect() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Employee> root = criteria.from(Employee.class);

        criteria.multiselect(root.get("name"), root.get("age"));

        List<Object[]> employees = session.createQuery(criteria).list();

        for (Object[] row: employees) {
            System.out.println("Name: " + row[0]);
            System.out.println("Age: " + row[1]);
        }

        session.close();
    }


    @Test
    @DisplayName("multipleWhere")
    public void multipleWhere() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root);

        Predicate agegreater20 = builder.gt(root.get("age"), 20); // greater than
        Predicate ageless30 = builder.lt(root.get("age"), 30); // less than

        criteria.where(builder.and(agegreater20, ageless30));

        List<Employee> employees = session.createQuery(criteria).list();
        employees.forEach(employee -> System.out.println(employee.getName()));

        session.close();


    }

    @Test
    @DisplayName("Paging Results")
    public void pagingResults() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> criteria = builder.createQuery(Employee.class);
        Root<Employee> root = criteria.from(Employee.class);
        criteria.select(root);

        // criteria.where(builder.gt(root.get("age"), 20));

        Query<Employee> query = session.createQuery(criteria);
        query.setMaxResults(2); // size
        query.setFirstResult(2); // pagination

        List<Employee> employees = query.list();
        System.out.println(employees);

        session.close();

    }

    @Test
    @DisplayName("greaterThanLocalDate")
    public void greaterThanLocalDate() {


        Session session = HibernateUtil.getSessionFactory().openSession();

        session.beginTransaction();
        CreditCard card1 = new CreditCard("card 1","242134143","449",LocalDate.of(2021,2,1));
        CreditCard card2 = new CreditCard("card 2","242134143","449",LocalDate.of(2021,3,18));
        CreditCard card3 = new CreditCard("card 3","242134143","449",LocalDate.of(2021,3,31));

        session.save(card1);
        session.save(card2);
        session.save(card3);

        session.getTransaction().commit();
        session.close();

        session = HibernateUtil.getSessionFactory().openSession();

        LocalDate today = LocalDate.now();
        Query<CreditCard> query = session.createQuery("from CreditCard where expirationDate BETWEEN :start AND :end", CreditCard.class);
        query.setParameter("start",today.minusDays(2));
        query.setParameter("end",today);

        List<CreditCard> cards = query.list();
        System.out.println(cards);
        session.close();


    }


//    sum, count, max, min, avg


    @Test
    @DisplayName("Check count")
    public void count() {

        Session session = HibernateUtil.getSessionFactory().openSession();


        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Double> criteria = builder.createQuery(Double.class);
        Root<Employee> root = criteria.from(Employee.class);

        criteria.select(builder.sum(root.get("salary")));

        //criteria.where(builder.equal(root.get("id"), 2L));

        // Opcion 1

         Double totalCosts = session.createQuery(criteria).getSingleResult();
        System.out.println(totalCosts);
        // System.out.println(companies);
        session.close();
    }

    // id in

    @Test
    @DisplayName("idInList")
    public void idInList() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query<Employee> query = session.createQuery("from Employee where id in :ids", Employee.class);
        query.setParameter("ids", Arrays.asList(1L, 2L, 3L));

        List<Employee> employees = query.list();
        System.out.println(employees);

        session.close();
    }

    // update

    @Test
    @DisplayName("updateCriteria")
    public void updateCriteria() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // recuperar empleados
        List<Employee> employees = session.createQuery("from Employee", Employee.class).list();
        employees.forEach(System.out::println);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Employee> criteria = builder.createCriteriaUpdate(Employee.class); // CriteriaUpdate
        Root<Employee> root = criteria.from(Employee.class);

        // cambiar datos
        criteria.set("age", 199);
        criteria.set("salary", 100000D);

        // filtrar
        // criteria.where()

        Query<Employee> query = session.createQuery(criteria);

        session.beginTransaction();
        query.executeUpdate(); // executeUpdate para producir cambios
        session.getTransaction().commit();

        // comprobar si después del executeUpdate se actualizan los objetos automáticamente: NO SE ACTUALIZAN
        employees.forEach(System.out::println);
        session.close();
    }


    // delete

    @Test
    @DisplayName("deleteCriteria")
    public void deleteCriteria() {

        Session session = HibernateUtil.getSessionFactory().openSession();



    }




    // fetch join



}
