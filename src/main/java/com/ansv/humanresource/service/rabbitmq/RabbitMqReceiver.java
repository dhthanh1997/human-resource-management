package com.ansv.humanresource.service.rabbitmq;

import com.ansv.humanresource.dto.mapper.BaseMapper;
import com.ansv.humanresource.dto.response.UserDTO;
import com.ansv.humanresource.model.UserEntity;
import com.ansv.humanresource.repository.UserEntityRepository;
import com.ansv.humanresource.service.UserService;
import com.ansv.humanresource.util.DataUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMqReceiver implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final BaseMapper<UserEntity, UserDTO> mapper = new BaseMapper<>(UserEntity.class, UserDTO.class);

    public RabbitMqReceiver() {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    }

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private UserService userService;

    @Value("${spring.rabbitmq.exchange:#{null}}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey:#{null}}")
    private String routingkey;



    public UserDTO userDTO = new UserDTO();

    public String username = "";

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(UserDTO user){
        logger.info("User Details Received is.. " + user.getUsername());
        userDTO = user;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue-human}")
    public void receivedMessageFromGateway(UserDTO item) {
        UserDTO user = userService.findByUsername(item);
        try {
            if (!DataUtils.isNullOrEmpty(user)) {
                if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                    throw new UsernameNotFoundException("User not found with username: ");
                }
            } else {
                //            creating if user isn't exist in db
                log.warn("User not found with username ----> create in db", item.getUsername());
//                user = new UserDTO();
                item.setStatus("ACTIVE");
                user = userService.save(item);
                rabbitTemplate.convertAndSend(exchange, routingkey, user);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }

}
