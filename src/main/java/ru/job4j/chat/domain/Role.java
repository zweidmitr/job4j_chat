package ru.job4j.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<Person> persons = new HashSet<>();

    @Override
    public String toString() {
        return String.format("Role: id=%s, name=%s", id, name);
    }

    @JsonIgnore
    public Set<Person> getPersons() {
        return persons;
    }

    @JsonProperty
    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }
}
