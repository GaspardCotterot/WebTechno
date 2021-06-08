package Isep.webtechno.controller;


import Isep.webtechno.model.entity.Booking;
import Isep.webtechno.model.entity.Message;
import Isep.webtechno.model.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path="/message")
public class MessageAndConversationController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    private ResponseEntity getAll() {
        List<Message> allMessages = new ArrayList<>();
        messageRepository.findAll().forEach(allMessages::add);
        return new ResponseEntity<>(allMessages, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(path = "/get_by_id/{message_id}")
    public Message getMessageById(@PathVariable int message_id){

        if (messageRepository.findById(message_id).isPresent()) {
            return messageRepository.findById(message_id).get();
        }
        return null;
    }

//    @PostMapping(path="/add")
//    public String addNewMessage (@RequestBody Booking booking, @RequestBody Date date, @RequestParam Boolean isFromOwner, @RequestParam String text) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//
//        Message message = new Message();
//
//        message.setBooking(booking);
//        message.setDate(date);
//        message.setIsFromOwner(isFromOwner);
//        message.setText(text);
//        messageRepository.save(message);
//        return "Saved";
//    }
}
