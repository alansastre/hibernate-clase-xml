package com.example.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectType type;

    @ElementCollection
    @CollectionTable(
            name = "project_tags",
            joinColumns = @JoinColumn(name = "id_project")
    )
    private List<String> tags = new ArrayList<>();

    @ElementCollection
    private List<ProjectType> projectTypes = new ArrayList<>();

    // Bidireccional
    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<Employee> employees = new ArrayList<>();

    public Project() {
    }

    public Project(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }
    public Project(String name, String code, String description, ProjectType type) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.type = type;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<ProjectType> getProjectTypes() {
        return projectTypes;
    }

    public void setProjectTypes(List<ProjectType> projectTypes) {
        this.projectTypes = projectTypes;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
