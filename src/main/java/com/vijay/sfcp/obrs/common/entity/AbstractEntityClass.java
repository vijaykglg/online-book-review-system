package com.vijay.sfcp.obrs.common.entity;
/*
Project : online-book-review-system
IDE     : IntelliJ IDEA
User    : Vijay Gupta
Date    : 30 May 2020
*/
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
//@SequenceGenerator(name="seq",initialValue = 21,allocationSize = 100)
public class AbstractEntityClass implements EntityObject {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "common_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "21"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "id", updatable = false, nullable = false)
    Integer id;

    /*@Version
    private Integer version;*/
    private Date dateCreated;
    private Date lastUpdated;

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

   /* public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }*/

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        lastUpdated = new Date();
        if (dateCreated == null) {
            dateCreated = new Date();
        }
    }
}
