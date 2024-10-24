package org.example.tokenorchaestrator.Controllers;

import org.example.tokenorchaestrator.Service.KeyGenImpl;
import org.example.tokenorchaestrator.payload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.example.tokenorchaestrator.Model.Key;

@RestController
@RequestMapping("/api/v1/")
public class KeyGeneratorController {

    @Autowired
     KeyGenImpl keyGen;

     @PostMapping("generate-token")
     public ResponseEntity<Key> generateTokenKey() {
         return new ResponseEntity<>(keyGen.generateToken(), HttpStatus.CREATED);
     }

     @GetMapping("keys/{keyId}")
    public ResponseEntity<?> getKey(@PathVariable String keyId){
         Key key = keyGen.getKey(keyId);
         if(key!= null) {
             return new ResponseEntity<>(key,HttpStatus.OK);
         }

         return new ResponseEntity<>(new ApiResponse(keyId+" is not available please generate it",HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
     }

     @DeleteMapping("keys/{keyId}")
     public ResponseEntity<ApiResponse> deleteKey(@PathVariable String keyId) {
         boolean isKeyDeleted = keyGen.deleteKey(keyId);

         if(isKeyDeleted)
             return new ResponseEntity<>(new ApiResponse(keyId+" is deleted successfully",HttpStatus.OK),HttpStatus.OK);

         return new ResponseEntity<>(new ApiResponse(keyId+" not found",HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
     }

     @PutMapping("keepalive/{keyId}")
    public ResponseEntity<?> keepAliveKey(@PathVariable String keyId) {
         Key key = keyGen.keepAliveKey(keyId);
         if(key != null) {
             return new ResponseEntity<>(key,HttpStatus.OK);
         }
         return new ResponseEntity<>(new ApiResponse(keyId+" not found",HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
     }

     @PutMapping("keys/{keyId}")
    public ResponseEntity<?> unblockKey(@PathVariable String keyId) {
         Key key = keyGen.unblockKey(keyId);

         if(key != null) {
             return new ResponseEntity<>(key,HttpStatus.OK);
         }
         return new ResponseEntity<>(new ApiResponse(keyId+" not found",HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
     }

     @GetMapping("keys")
    public ResponseEntity<?> retrieveUnBlockKeys() {
         Key key = keyGen.retrieveUnBlockKey();
         if(key != null) {
             return new ResponseEntity<>(key,HttpStatus.OK);
         }

         return new ResponseEntity<>(new ApiResponse("There are no following keys available for use",HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);

     }

}
