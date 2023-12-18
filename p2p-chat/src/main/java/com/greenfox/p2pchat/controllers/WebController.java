package com.greenfox.p2pchat.controllers;

import com.greenfox.p2pchat.dtos.ClientDto;
import com.greenfox.p2pchat.dtos.MessageDto;
import com.greenfox.p2pchat.dtos.ReceiveDto;
import com.greenfox.p2pchat.models.User;
import com.greenfox.p2pchat.services.WebService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Random;


@Controller
public class WebController {
    private WebService webService;
    private User currentUser;

    public WebController(WebService webService) {
        this.webService = webService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model) {
        if (currentUser == null) {
            return "redirect:/register";
        }

        model.addAttribute("userName", currentUser.getUserName());
        model.addAttribute("messages", webService.findMessages());
        model.addAttribute("users", webService.findUsers());
        return "index";
    }

    @GetMapping("/register")
    public String getRegisterPage(@ModelAttribute String errorMsg) {
        if (currentUser != null) {
            return "redirect:/";
        }
        return "register";
    }

    @PostMapping("/register")
    public String submitRegisterForm(String userName, RedirectAttributes ra) throws UnknownHostException {
        if (userName.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Please provide your name");
            return "redirect:/register";
        }

        currentUser = webService.saveUser(userName);
        return "redirect:/";
    }

    @PutMapping("/register")
    public String submitUpdateForm(String userName) {
        webService.updateUserName(userName, currentUser);
        return "redirect:/";
    }

    @PostMapping("/messages")
    public String saveMessage(String messageText, String userIp) {
        webService.saveMessage(messageText, currentUser);

        RestTemplate rt = new RestTemplate();

        ClientDto client = new ClientDto(currentUser.getUserName());
        MessageDto message = new MessageDto(new Random().nextInt(8_999_999) + 1000000, currentUser.getUserName(), messageText, System.currentTimeMillis());
        ReceiveDto receive = new ReceiveDto(message, client);

        String url = "http://" + userIp + ":8080/api/message/receive";
        rt.postForObject(url, new HttpEntity<>(receive), ReceiveDto.class);

        return "redirect:/";
    }
}
