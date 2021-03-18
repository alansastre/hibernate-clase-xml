package com.example.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String cif;

//   @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
//    @OneToMany(mappedBy = "company")
//    private List<Employee> employees = new ArrayList<>();

    // Opción 1 - OneToMany bidireccional, con columna extra en la tabla Employee
    @OneToMany(mappedBy = "company")
    @Where(clause = "married = 1")
    @OrderBy("name")
    private List<Employee> employees = new ArrayList<>();

    // Opción 2 - OneToMany una tabla separada
    @OneToMany
    @JoinTable(
            name = "company_creditcard",
            joinColumns = @JoinColumn(name = "company_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="creditcard_id", referencedColumnName = "id")
    )
    private List<CreditCard> creditCards = new ArrayList<>();



    public Company() {
    }

    public Company(String name, String cif) {
        this.name = name;
        this.cif = cif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cif='" + cif + '\'' +
                '}';
    }
}
