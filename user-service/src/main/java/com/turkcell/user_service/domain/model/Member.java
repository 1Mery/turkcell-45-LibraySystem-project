package com.turkcell.user_service.domain.model;


import java.util.Objects;

public class Member {

    private final MemberId id;

    private String firstName;
    private String lastName;
    private Email email;
    private Username username;
    private String password;
    private Phone phone;

    private MembershipLevel level;
    private MemberStatus status;

    //controlled c'tor
    private Member(MemberId id, String firstName, String lastName, Email email, Username username, String password, Phone phone, MembershipLevel level, MemberStatus status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.level = level != null ? level : MembershipLevel.getDefault();
        this.status = status != null ? status : MemberStatus.getDefault();
    }

    //create method
    public static Member create(String firstName, String lastName, Email email,
                                Username username, String password, Phone phone){
        validateFirstName(firstName);
        validateLastName(lastName);
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(username, "Username cannot be null");
        validatePassword(password);
        Objects.requireNonNull(phone, "Phone cannot be null");
        return new Member(
                MemberId.generate(),
                firstName,
                lastName,
                email,
                username,
                password,
                phone,
                MembershipLevel.getDefault(),
                MemberStatus.getDefault()
        );
    }

    //rehydrate method
    public static Member rehydrate(
            MemberId id, String firstName, String lastName,
            Email email, Username username, String password, Phone phone, MembershipLevel level, MemberStatus status){
        return new Member(id, firstName,lastName,email,username,password,phone,level,status);
    }

    //worker methods
    public void changeFirstName(String firstName){
        validateFirstName(firstName);
        this.firstName = firstName;
    }

    public void changeLastName(String lastName){
        validateLastName(lastName);
        this.lastName = lastName;
    }

    public void updateEmail(Email newEmail){
        Objects.requireNonNull(newEmail, "Email cannot be null");
        if(this.email.equals(newEmail)){
            throw new IllegalArgumentException("This email already exists");
        }
        this.email = newEmail;

    }

    public void changeUsername(Username newUsername){
        Objects.requireNonNull(newUsername, "Username cannot be null");
        this.username = newUsername;
    }

    public void changePassword(String password){
        validatePassword(password);
        this.password = password;
    }

    public void updatePhone(Phone newPhone){
        Objects.requireNonNull(newPhone, "Phone number cannot be null");
        this.phone = newPhone;
    }

    public void activate() {
        this.status = MemberStatus.ACTIVE;
    }

    public void ban(){
        this.status = MemberStatus.BANNED;
    }

    public void upgradeLevel(){
        this.level = MembershipLevel.GOLD;
    }

    public void downLevel(){
        this.level = MembershipLevel.STANDARD;
    }

    //validations
    private static void validateFirstName(String firstName){
        if (firstName == null || firstName.isBlank()){
            throw new IllegalArgumentException("First name cannot be null");
        }

        if (firstName.length() >= 50){
            throw new IllegalArgumentException("First name length must be less than 50 characters");
        }
    }

    private static void validateLastName(String lastName){
        if (lastName == null || lastName.isBlank()){
            throw new IllegalArgumentException("Lastname cannot be null");
        }
        if (lastName.length() > 50){
            throw new IllegalArgumentException("Last name length must be less than 50 characters");
        }
    }

    private static void validatePassword(String password){
        if (password == null || password.isBlank()){
            throw new IllegalArgumentException("Password cannot be null");
        }
        if (password.length() < 8 ){
            throw new IllegalArgumentException("Password length must be min 8 characters");
        }
        //TODO : check
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).+$")){
            throw new IllegalArgumentException("Invalid password");
        }
    }

    //getters

    public MemberId id() {
        return id;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public Email email() {
        return email;
    }

    public Username username() {
        return username;
    }

    public String password() {
        return password;
    }

    public Phone phone() {
        return phone;
    }

    public MembershipLevel level() {
        return level;
    }

    public MemberStatus status() {
        return status;
    }
}
