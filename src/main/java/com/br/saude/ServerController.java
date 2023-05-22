package com.br.saude;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ServerController {

    public static final String SERVER = "{!SERVER_NAME!}";

    public String replaceServerName(String oldName, HttpServletRequest req) {
        if (oldName != null)
            return oldName.replace(ServerController.SERVER, this.getServerName(req));
        return null;
    }

    @Autowired
    private ServerProperties serverProperties;

    @GetMapping("/server-local")
    @ResponseBody
    public String getServer() {
        String serverName = serverProperties.getAddress().getHostName();
        int serverPort = serverProperties.getPort();
        return "http://" + serverName + ":" + serverPort;
    }

    @GetMapping("/server-name")
    @ResponseBody
    public String getServerName(HttpServletRequest request) {
        String name = request.getServerName().toString();
        // return request.getServerName();
        if (name.contains("localhost")) {
            int serverPort = request.getServerPort();
            return "http://localhost:" + serverPort;
        }

        return "https://" + name;
    }
}