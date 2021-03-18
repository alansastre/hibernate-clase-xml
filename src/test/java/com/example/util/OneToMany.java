package com.example.util;

import com.example.model.Company;
import com.example.model.CreditCard;
import com.example.model.Department;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class OneToMany {


    @Test
    @DisplayName("One to many company - credit cards with new table")
    public void checkCompanyCreditCards() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CreditCard creditCard1 = new CreditCard("card 1", "3344589711223344", "443", LocalDate.now());
        CreditCard creditCard2 = new CreditCard("card 2", "3344589711223344", "443", LocalDate.now());
        CreditCard creditCard3 = new CreditCard("card 3", "3344589711223344", "443", LocalDate.now());

        Company company = new Company("Empresa OneToMany","B55544433");

        session.beginTransaction(); // abrimos transaccion


        // realizar asociacion de entidades
        company.getCreditCards().add(creditCard1);
        company.getCreditCards().add(creditCard2);
        company.getCreditCards().add(creditCard3);

        // guardar las entidades
        session.save(creditCard1);
        session.save(creditCard2);
        session.save(creditCard3);
        session.save(company);

        // hacer efectivos los cambios en la base de datos
        session.getTransaction().commit();

    }


    @Test
    @DisplayName("Check company with departments one column")
    public void checkCompanyWithDepartmentsOneColumn() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        // creamos entidades

        // 2 libros
        Department department1 = new Department();
        department1.setName("dep1");
        Department department2 = new Department();
        department2.setName("dep2");

        // 1 editorial
        Company company = new Company("Empresa OneToMany","B55544433");

        // relacionar entidades
        company.getDepartments().add(department1);
        company.getDepartments().add(department2);

        session.beginTransaction(); // abrimos transaccion

        // persistir datos
        session.save(department1);
        session.save(department2);
        session.save(company);

        // hacer efectivos los cambios en la base de datos
        session.getTransaction().commit();
    }

}
