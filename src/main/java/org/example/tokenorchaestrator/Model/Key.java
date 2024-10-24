package org.example.tokenorchaestrator.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Key {

    String keyId;
    boolean isBlocked;
    LocalDateTime createdAt;
    LocalDateTime expiredAt;

}
