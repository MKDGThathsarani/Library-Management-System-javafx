package com.library.librarymanagementsystem.dto;

public class MemberDTO {
    private String memberId;
    private String name;
    private String email;
    private String phone;
    private String address;

    // Constructor
    public MemberDTO(String memberId, String name, String email, String phone, String address) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters
    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
}