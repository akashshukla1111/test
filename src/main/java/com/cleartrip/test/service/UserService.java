package com.cleartrip.test.service;


import com.cleartrip.test.entity.UserEntity;
import com.cleartrip.test.pojo.User;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Controller;

import java.util.*;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.nonNull;

@Controller
public class UserService {

    private int id=1;

    private HashMap<String,UserEntity> userMap =new HashMap<>();

    public boolean addUser(User user){

        //first check if user is present in db
        if ( nonNull(user) && Strings.isNotBlank(user.getEmailId())){
             if(isUserNotPresent(user.getEmailId())){

                 UserEntity userEntity = mapUserToUserEntity(user);
                 userEntity.setBid(new ArrayList<>());
                 userMap.put(user.getEmailId().trim().toLowerCase(),userEntity);
                 return TRUE;
             }

             // log here and Throw Exception if reuired
            System.out.println("user Already in the database");
        }
        System.out.println(" email id is required field ");
        return FALSE;
    }

    private boolean isUserNotPresent(String email) {
        if (nonNull(email) && userMap.get(email)==null) {
            return TRUE;
        }
        return FALSE;
    }

    public List<User> getAllUser(){
        Iterator<Map.Entry<String, UserEntity>> iterator = userMap.entrySet().iterator();
        List<User> list=new ArrayList<>();
        while (iterator.hasNext()){
            Map.Entry<String, UserEntity> next = iterator.next();
            String key = next.getKey();
            UserEntity userEntity = userMap.get(key);
            list.add(mapUserEntityToUser(userEntity));
        }
        return list;
    }

    public UserEntity getUserEntity(String uid){
       if (nonNull(uid) && Strings.isNotBlank(uid)){
           UserEntity user = userMap.get(uid.toLowerCase().trim());
           if (nonNull(user)){
               return user;
           }
       }
       //log here
        System.out.println("user id can not null");
       return null;
    }

    private UserEntity mapUserToUserEntity(User user){
        UserEntity userEntity=new UserEntity();
        userEntity.setEmailId(user.getEmailId());
        userEntity.setUname(user.getUname());
        return userEntity;

    }

    private User mapUserEntityToUser(UserEntity entity){
        User user=new User();
        user.setEmailId(entity.getEmailId());
        user.setUname(entity.getUname());
        return user;
    }

    public UserEntity updateUser(String eid, UserEntity entity,String bid){
        List<String> bids = entity.getBid();
        bids.add(bid);
        entity.setBid(bids);
        userMap.put(eid,entity);
        return entity;
    }

    public UserEntity updateUserReturnBook(String uid,UserEntity entity,String bid){
        List<String> bids = entity.getBid();
        bids.remove(bid);
        entity.setBid(bids);
        userMap.put(uid,entity);
        return entity;
    }

    public User getUser(String eid){
        UserEntity userEntity = getUserEntity(eid);
        return mapUserEntityToUser(userEntity);

    }

}
