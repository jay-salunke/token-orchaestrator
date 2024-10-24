package org.example.tokenorchaestrator.Service;
import org.example.tokenorchaestrator.Model.Key;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

@Service
public class KeyGenImpl {

    public static HashMap<String,Key> map = new HashMap<>();
    private static Queue<Key> unblockKeys = new LinkedList<>();


    public Key generateToken() {

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime currentTime = LocalDateTime.now();
            String uuid = UUID.randomUUID().toString();
            Key key = new Key(uuid,false,currentTime,currentTime.plusMinutes(5));
            unblockKeys.offer(key);
            map.put(uuid,key);
            return key;
    }

    public Key getKey(String keyId) {

        if(map.containsKey(keyId)) {
            Key key = map.get(keyId);
            return key;
        }

        return null;
    }

    public boolean deleteKey(String key) {

        if(map.containsKey(key)) {
           map.remove(key);
           return true;
        }

        return false;
    }

    public Key keepAliveKey(String keyId) {
        if(map.containsKey(keyId)) {

            Key key = map.get(keyId);
            if(key.getExpiredAt().compareTo(LocalDateTime.now())< 0) {
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime expiryDate = currentTime.plusMinutes(5);
                key.setCreatedAt(currentTime);
                key.setExpiredAt(expiryDate);
                key.setBlocked(true);
                map.put(keyId, key);

                return key;
            }
        }

        return null;
    }

    public Key unblockKey(String keyId) {
        if(map.containsKey(keyId)) {

            Key key = map.get(keyId);
            key.setBlocked(false);
            unblockKeys.offer(key);
                return key;
            }

        return null;

    }

    public Key retrieveUnBlockKey() {
        if(!unblockKeys.isEmpty()) {

           Key key = unblockKeys.peek();
           key.setBlocked(true);
           map.put(key.getKeyId(),key);
           return unblockKeys.poll();
        }

        return null;
    }

    @Scheduled(fixedRate = 60000)
    public void autoKeyDelete() {
        for(Map.Entry<String,Key> entrySet:map.entrySet()) {

            String keyId = entrySet.getKey();
            Key key = entrySet.getValue();

            if(key.getExpiredAt().compareTo(LocalDateTime.now()) < 0)
                map.remove(keyId);


        }
    }

}











