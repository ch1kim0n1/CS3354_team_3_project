package com.studybuddy.api.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Lets browser refreshes on React routes resolve to the SPA rather than a server 404. */
@Controller
public class SpaController {
  @GetMapping({"/", "/login", "/register", "/onboarding", "/matches", "/groups", "/admin"})
  public String index() { return "forward:/index.html"; }
}
