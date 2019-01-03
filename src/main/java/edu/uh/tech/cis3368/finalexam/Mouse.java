package edu.uh.tech.cis3368.finalexam;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Mouse implements Serializable {
    private int id;

    private String name;
    private Cat cat;

    @ManyToOne(cascade = CascadeType.PERSIST)
    public Cat getCat() {
        return cat;
    }

    public void setCat(Cat cat) {
        this.cat = cat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Mouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cat=" + cat +
                '}';
    }
}
