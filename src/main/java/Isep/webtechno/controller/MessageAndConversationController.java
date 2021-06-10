package Isep.webtechno.controller;


import Isep.webtechno.model.converter.ConversationConverter;
import Isep.webtechno.model.converter.MessageConverter;
import Isep.webtechno.model.dto.ConversationDto;
import Isep.webtechno.model.dto.MessageDto;
import Isep.webtechno.model.entity.Conversation;
import Isep.webtechno.model.entity.Message;
import Isep.webtechno.model.entity.User;
import Isep.webtechno.model.repo.ConversationRepository;
import Isep.webtechno.model.repo.MessageRepository;
import Isep.webtechno.model.repo.UserRepository;
import Isep.webtechno.utils.GeneralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/message")
public class MessageAndConversationController {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final GeneralService generalService;
    private final ConversationConverter conversationConverter;
    private final MessageConverter messageConverter;

    @Autowired
    public MessageAndConversationController(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository, GeneralService generalService, ConversationConverter conversationConverter, MessageConverter messageConverter) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.generalService = generalService;
        this.conversationConverter = conversationConverter;
        this.messageConverter = messageConverter;
    }

    @GetMapping(path = "/all-conversations")
    private ResponseEntity<List<ConversationDto>> getConversations() {
        User userContext = generalService.getUserFromContext();
        List<Conversation> conversations = userContext.getConversations();
        return new ResponseEntity<>(conversationConverter.toDto(conversations, userContext), HttpStatus.OK);
    }

    @PostMapping(path = "/new-conversation/{userId}")
    private ResponseEntity<String> addConversation(@PathVariable Integer userId) {
        User userContext = generalService.getUserFromContext();
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("No user with id " + userId));

        if (userContext.getId().equals(userId))
            return new ResponseEntity<>("You can't have a conversation with yourself", HttpStatus.BAD_REQUEST);
        if (userContext.getConversations().stream().anyMatch(
                conversation -> conversation.getUsers().contains(user)
        )) {
            return new ResponseEntity<>("A conversation with this user already exists", HttpStatus.BAD_REQUEST);
        }

        Conversation conversation = new Conversation();
        conversation.setUsers(List.of(userContext, user));
        conversation.setUser1lastConsultedAt(new Date());
        conversation.setUser2lastConsultedAt(new Date());
        conversationRepository.save(conversation);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = "/new-message/{conversationId}")
    private ResponseEntity<String> addMessage(@PathVariable Integer conversationId, @RequestParam String text) {
        User userContext = generalService.getUserFromContext();
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new EntityNotFoundException("No conversation with this id"));
        if(!conversation.getUsers().contains(userContext)) return new ResponseEntity<>("This is not your conversation", HttpStatus.FORBIDDEN);

        Message message = new Message();
        message.setConversation(conversation);
        message.setText(text);
        message.setDate(new Date());
        message.setUserSending(userContext);

        messageRepository.save(message);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/get-messages/{conversationId}")
    private ResponseEntity<List<MessageDto>> getMessages(@PathVariable Integer conversationId) {
        User userContext = generalService.getUserFromContext();
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new EntityNotFoundException("No conversation with this id"));
        if(!conversation.getUsers().contains(userContext)) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        List<Message> messages = conversation.getMessages();

        return new ResponseEntity<>(messageConverter.toDto(messages, userContext), HttpStatus.OK);
    }
}
