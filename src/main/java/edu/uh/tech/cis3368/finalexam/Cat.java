package edu.uh.tech.cis3368.finalexam;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Cat {

    private int id;
    private String name;
    private String breed;
    private String favoriteToy;


    private Set<Mouse> mice = new HashSet<>();

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    public Set<Mouse> getMice() {
        return mice;
    }

    public void setMice(Set<Mouse> mice) {
        this.mice = mice;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "FIRST_NAME")
    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    @Column(name = "LAST_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "OFFICE")
    public String getFavoriteToy() {
        return favoriteToy;
    }

    public void setFavoriteToy(String favoriteToy) {
        this.favoriteToy = favoriteToy;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", favoriteToy='" + favoriteToy + '\'' +
                '}';
    }

    public void addMouse(Mouse mouse) {
       mice.add(mouse);
       mouse.setCat(this);




    }

    public void removeMouse(Mouse mouse) {
        mice.remove(mouse);




    }

    public void dropMice() {
        mice.forEach(mouse -> mouse.setCat(null));
        mice.clear();

        }


    }
