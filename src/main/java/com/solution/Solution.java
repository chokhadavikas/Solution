package com.solution;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = null;
        List<User> users  = null;
        try {
        file = new File("src/main/resources/data.json");
        users =  objectMapper.readValue(file,new TypeReference<List<User>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(users);
        List<String> ids = validateUserList(users);

    }

    private static List<String> validateUserList(List<User> users) {

        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);

        List<String> userList = users.stream().filter(user -> {
         if (StringUtils.isBlank(user.getName()) ||
         StringUtils.isBlank(user.getAddress())
         || (StringUtils.isBlank(user.getZip()) || !pattern.matcher(user.getZip()).matches())) {
             return false;
         }else
            return true;
        }).map(user -> user.getId()).collect(Collectors.toList());

        return userList;
    }


}
class User implements Serializable {

    private String name;
    private String address;
    private String zip;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", zip='" + zip + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name) &&
                address.equals(user.address) &&
                zip.equals(user.zip) &&
                id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, zip, id);
    }
}