package com.trackhour.app.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    @Column(name = "ID")
    private UUID id;

    @Column(name = "name")
    private GrantedAutorityEnum name;

    public Role(GrantedAutorityEnum name) {
        this.name = name;
    }
    @Deprecated
    public Role(){
    }
    public UUID getId() {
        return id;
    }
    public GrantedAutorityEnum getName() {
        return name;
    }
    public void setName(GrantedAutorityEnum name) {
        this.name = name;
    }
    @Override
    public String getAuthority() {
        return this.name.name();
    }
}
