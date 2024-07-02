package com.andersen.lesson9.Models;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@org.hibernate.annotations.NamedQueries({ @org.hibernate.annotations.NamedQuery(name = "User_GetUserById", query = "from User where id = :id"),
        @org.hibernate.annotations.NamedQuery(name = "User_UpdateUserById", query = "Update User set id = :newId where id = :id")})//,
//        @org.hibernate.annotations.NamedNativeQuery(name = "DeptEmployee_UpdateEmployeeDesignation", query = "call UPDATE_EMPLOYEE_DESIGNATION(:employeeNumber, :newDesignation)", resultClass = DeptEmployee.class) })
@Entity
@Table(name = "public.user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName="user_seq")
    private Integer id;
    private String name;
    private Timestamp creationDate;

    // A user can have many tickets
    @OneToMany(mappedBy = "user")
    private List<Ticket> ticketList;

    public void setId(Integer id){
        this.id = id;
    }
    public Integer getId(){
        return this.id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public void setCreationDate(Timestamp creationDate){
        this.creationDate = creationDate;
    }
    public Timestamp getCreationDate(){
        return this.creationDate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.getId());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(this.id);
    }
}
