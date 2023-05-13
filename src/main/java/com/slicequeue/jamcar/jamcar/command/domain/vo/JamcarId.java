package com.slicequeue.jamcar.jamcar.command.domain.vo;

import org.hibernate.annotations.Comment;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JamcarId implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("잼카 식별값")
    private Long id;

    public JamcarId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JamcarId jamcarId = (JamcarId) o;
        return Objects.equals(id, jamcarId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "JamcarId{" +
                "id=" + id +
                '}';
    }
}
