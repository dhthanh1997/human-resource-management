package com.ansv.humanresource.dto.redis;

//import com.redis.om.spring.annotations.Indexed;
//import com.redis.om.spring.annotations.Searchable;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Data
//@Document
@Builder
@NoArgsConstructor
@Getter
@Setter
@RedisHash(value = "accessToken")
public class AccessToken {

//    @Id
//    @Indexed
    private String id;

    //    @Indexed
    private String accessToken;

//    @Indexed
//    @Searchable
    @NonNull
    private String username;

//    @Indexed
//    @Searchable
    private String department;

//    @Indexed
//    @Searchable
    private String position;

//    @Indexed
//    private Set<String> tags = new HashSet<String>();

//    @Indexed
    @NonNull
//    @Searchable
    private String uuid;

//    @Indexed
    @TimeToLive(unit = TimeUnit.SECONDS)
    @NonNull
    private Date expiredTime;

//    @Indexed
//    @Searchable
    @NonNull
    private String serviceName;
}
