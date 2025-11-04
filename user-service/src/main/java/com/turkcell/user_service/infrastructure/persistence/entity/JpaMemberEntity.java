package com.turkcell.user_service.infrastructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "members")
public class JpaMemberEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String username;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "membership_level")
    @Enumerated(EnumType.STRING)
    private String membershipLevel;
    @Column(name = "member_status")
    @Enumerated(EnumType.STRING)
    private String memberStatus;

    public JpaMemberEntity(UUID id, String firstName, String lastName, String email, String password, String username,
            String phone, String membershipLevel, String memberStatus) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
        this.membershipLevel = membershipLevel;
        this.memberStatus = memberStatus;
    }

    public JpaMemberEntity() {

    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String firstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String email() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String phone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String membershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public String memberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }
}
