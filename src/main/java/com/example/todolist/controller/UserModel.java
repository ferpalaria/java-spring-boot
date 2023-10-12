package com.example.todolist.controller;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name="tb_users")
public class UserModel {

   @Id
   @GeneratedValue(generator = "uuid")
   private UUID id;

   @Column(unique = true)
   private String username;
   private String name;
   private String password;

   @CreationTimestamp
   private LocalDateTime createdAt;
}
