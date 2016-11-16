package com.jaychang.demo.st;

public class User {

  public String id;
  public String name;

  public User(String id, String name) {
    this.id = id;
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("User{");
    sb.append("id='").append(id).append('\'');
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
