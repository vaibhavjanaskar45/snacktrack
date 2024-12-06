package com.snacktrack.snacktrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.snacktrack.snacktrack.model.Feedback;
import com.snacktrack.snacktrack.repository.FeedbackRepository;

@Controller
public class FeedbackController {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @PostMapping("/submit-feedback")
    public String submitFeedback(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message) {

        Feedback feedback = new Feedback();
        feedback.setName(name);
        feedback.setEmail(email);
        feedback.setMessage(message);

        feedbackRepository.save(feedback);

        return "redirect:/contactUs";
    }
}
