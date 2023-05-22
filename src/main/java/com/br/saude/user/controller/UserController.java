package com.br.saude.user.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.saude.infrastructure.support.ObjectUtils;
import com.br.saude.infrastructure.support.RequestHeaderHolder;
import com.br.saude.infrastructure.support.StringUtil;
import com.br.saude.user.model.LogginDt;
import com.br.saude.user.model.User;
import com.br.saude.user.service.IServiceUser;
import com.google.gson.Gson;

/*=========================ATENÇÃO=========================
 * As classes a seguir estão organizadas em ordem alfabética assim como seus respectivos métodos. Caso for fazer alguma alteração, continue com a organização!
 *  
*/
@RestController
@RequestMapping(path = "/api/v1/app/user")
public class UserController {

    @RequestMapping(path = "/hello")
    public String helloWorld(@RequestParam String name,
            @RequestHeader(value = "UserId", required = false) String userId) {
        return "Hello " + name + "!";
    }

    // ---------------------------------------------------
    // ------------- USUARIO -----------------------------
    // ---------------------------------------------------
    @Autowired
    private IServiceUser serviceUser;

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public User atualizar(@RequestBody Map<String, Object> updates,
            @RequestHeader(value = "UserId", required = false) String userId) {
        RequestHeaderHolder.addToHeaders("UserId", userId);

        return ObjectUtils.updateOrSaveObject(updates, serviceUser);
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean deletar(@PathVariable String id, @RequestHeader(value = "UserId", required = false) String userId) {
        RequestHeaderHolder.addToHeaders("UserId", userId);

        User user = new User();
        user.setId(id);
        serviceUser.delete(user);
        return true;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody LogginDt login, @RequestHeader(value = "UserId", required = false) String userId) {
        User user = serviceUser.findByLoginAndPassword(login.login, StringUtil.toPasswordMD5(login.password));

        if (user == null)
            user = serviceUser.findByEmailAndPassword(login.login, StringUtil.toPasswordMD5(login.password));

        return user;
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    public User salvar(@RequestBody User user, @RequestHeader(value = "UserId", required = false) String userId) {
        // User userr = (User) gson.fromJson(user, User.class);

        RequestHeaderHolder.addToHeaders("UserId", userId);

        return serviceUser.save(user);
    }

    @RequestMapping(path = "/all")
    public List<User> get(@RequestHeader(value = "UserId", required = false) String userId) {

        List<User> all = serviceUser.getAll();

        return all;
    }

    @RequestMapping(path = "/find/{id}")
    public User getById(@PathVariable String id,
            @RequestHeader(value = "UserId", required = false) String userId) {

        User user = (User) this.serviceUser.findById(id);

        return user;
    }
}